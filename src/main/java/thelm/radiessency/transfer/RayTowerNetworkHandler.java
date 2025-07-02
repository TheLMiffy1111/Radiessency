package thelm.radiessency.transfer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.primitives.Ints;

import it.unimi.dsi.fastutil.objects.ObjectRBTreeSet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import thelm.radiessency.api.transfer.DirectionalGlobalPos;
import thelm.radiessency.api.transfer.GlobalVec;
import thelm.radiessency.api.transfer.IRayNetwork;
import thelm.radiessency.api.transfer.IRayNetworkHandler;
import thelm.radiessency.capability.RadiessencyCapabilities;
import thelm.radiessency.tile.RayTowerTile;

public class RayTowerNetworkHandler implements IRayNetworkHandler, INBTSerializable<NBTTagCompound> {

	private static final Logger LOGGER = LogManager.getLogger();
	public static final Vec3d DEFAULT_BEAM_OFFSET = new Vec3d(0.5, 1.75, 0.5);

	protected static long refreshInterval = 40;

	protected final RayTowerTile tile;
	protected final int range;
	protected final int maxConnections;
	protected final Vec3d beamOffset;
	protected Set<BlockPos> connections = new ObjectRBTreeSet<>();
	protected Set<DirectionalGlobalPos> inputs = new ObjectRBTreeSet<>();
	protected IRayNetwork network;

	protected Set<IRayNetworkHandler> prevLoadedConnections = Collections.emptySet();
	protected boolean needsNetworkRebuild;
	protected long lastNetworkBuild;

	public RayTowerNetworkHandler(RayTowerTile tile, int range, int maxConnections) {
		this(tile, range, maxConnections, DEFAULT_BEAM_OFFSET);
	}

	public RayTowerNetworkHandler(RayTowerTile tile, int range, int maxConnections, Vec3d beamOffset) {
		this.tile = tile;
		this.range = range;
		this.maxConnections = maxConnections;
		this.beamOffset = beamOffset;
	}

	@Override
	public int getDimension() {
		return tile.getWorld().provider.getDimension();
	}

	@Override
	public BlockPos getPos() {
		return tile.getPos();
	}

	@Override
	public GlobalVec getCenterPoint() {
		return new GlobalVec(getDimension(), getPos(), beamOffset);
	}

	@Override
	public int getRange() {
		return range;
	}

	@Override
	public int getMaxConnections() {
		return maxConnections;
	}

	@Override
	public Set<BlockPos> getConnections() {
		return Collections.unmodifiableSet(connections);
	}

	@Override
	public boolean addConnection(BlockPos connection) {
		if(connections.contains(connection)) {
			return false;
		}
		if(connections.size() >= getMaxConnections()) {
			return false;
		}
		Vec3i distVec = getPos().subtract(connection);
		int distance = Ints.max(Math.abs(distVec.getX()), Math.abs(distVec.getY()), Math.abs(distVec.getZ()));
		if(distance > getRange()) {
			return false;
		}
		if(!tile.getWorld().isBlockLoaded(connection)) {
			return false;
		}
		TileEntity connected = tile.getWorld().getTileEntity(connection);
		if(connected == null || !connected.hasCapability(RadiessencyCapabilities.RAY_NETWORK, null)) {
			return false;
		}
		IRayNetworkHandler tower = connected.getCapability(RadiessencyCapabilities.RAY_NETWORK, null);
		if(tower.getConnections().size() >= tower.getMaxConnections() && !tower.getConnections().contains(getPos())) {
			return false;
		}
		if(distance > tower.getRange()) {
			return false;
		}
		connections.add(connection);
		needsNetworkRebuild = true;
		if(!tower.getConnections().contains(getPos())) {
			tower.addConnection(getPos());
		}
		tile.syncTile(false);
		return true;
	}

	@Override
	public boolean removeConnection(BlockPos connection) {
		if(!connections.contains(connection)) {
			return false;
		}
		connections.remove(connection);
		needsNetworkRebuild = true;
		TileEntity connected = tile.getWorld().getTileEntity(connection);
		if(connected != null && connected.hasCapability(RadiessencyCapabilities.RAY_NETWORK, null)) {
			IRayNetworkHandler tower = connected.getCapability(RadiessencyCapabilities.RAY_NETWORK, null);
			if(tower.getConnections().contains(getPos())) {
				tower.removeConnection(getPos());
			}
		}
		tile.syncTile(false);
		return true;
	}

	@Override
	public Set<IRayNetworkHandler> getLoadedConnections() {
		World world = tile.getWorld();
		if(world.isRemote && world.getTotalWorldTime() - lastNetworkBuild <= refreshInterval) {
			return Collections.unmodifiableSet(prevLoadedConnections);
		}
		Set<IRayNetworkHandler> loadedConnections = new LinkedHashSet<>();
		List<BlockPos> invalidConnections = new ArrayList<>();
		for(BlockPos connection : connections) {
			if(world.isBlockLoaded(connection)) {
				TileEntity connected = world.getTileEntity(connection);
				if(connected != null && connected.hasCapability(RadiessencyCapabilities.RAY_NETWORK, null)) {
					IRayNetworkHandler tower = connected.getCapability(RadiessencyCapabilities.RAY_NETWORK, null);
					if(tower.getConnections().contains(getPos())) {
						loadedConnections.add(tower);
						continue;
					}
				}
				if(!world.isRemote) {
					invalidConnections.add(connection);
				}
			}
		}
		if(!world.isRemote && !invalidConnections.isEmpty()) {
			connections.removeAll(invalidConnections);
			tile.syncTile(false);
			needsNetworkRebuild = true;
		}
		if(!loadedConnections.equals(prevLoadedConnections)) {
			prevLoadedConnections = loadedConnections;
			if(!world.isRemote) {
				needsNetworkRebuild = true;
			}
			else {
				lastNetworkBuild = world.getTotalWorldTime();
			}
		}
		return Collections.unmodifiableSet(loadedConnections);
	}

	@Override
	public Set<DirectionalGlobalPos> getInputs() {
		return Collections.unmodifiableSet(inputs);
	}

	@Override
	public boolean addInput(DirectionalGlobalPos input) {
		if(input.dimension() != getDimension()) {
			return false;
		}
		Vec3i distVec = getPos().subtract(input.blockPos());
		int distance = Ints.max(Math.abs(distVec.getX()), Math.abs(distVec.getY()), Math.abs(distVec.getZ()));
		if(distance > getRange()) {
			return false;
		}
		inputs.add(input);
		if(network != null) {
			network.inputsChanged();
		}
		return true;
	}

	@Override
	public boolean removeInput(DirectionalGlobalPos input) {
		if(!inputs.contains(input)) {
			return false;
		}
		inputs.remove(input);
		if(network != null) {
			network.inputsChanged();
		}
		return true;
	}

	@Override
	public IRayNetwork getNetwork() {
		if(tile.getWorld().isRemote) {
			return null;
		}
		if(tile.getWorld().getTotalWorldTime() - lastNetworkBuild > refreshInterval) {
			getLoadedConnections();
		}
		if(needsNetworkRebuild || network == null) {
			LOGGER.debug("Rebuilding ray tower network starting at {}", getPos());
			RayNetwork newNetwork = new RayNetwork();
			newNetwork.init(this);
			network = newNetwork;
		}
		return network;
	}

	@Override
	public void setNetwork(IRayNetwork network) {
		if(network.getTowers().containsKey(getPos())) {
			this.network = network;
			needsNetworkRebuild = false;
			lastNetworkBuild = tile.getWorld().getTotalWorldTime();
		}
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList connectionsNbt = new NBTTagList();
		for(BlockPos connection : connections) {
			connectionsNbt.appendTag(NBTUtil.createPosTag(connection));
		}
		nbt.setTag("Connections", connectionsNbt);
		NBTTagList inputsNbt = new NBTTagList();
		for(DirectionalGlobalPos input : inputs) {
			inputsNbt.appendTag(input.serialize());
		}
		nbt.setTag("Inputs", inputsNbt);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		connections.clear();
		inputs.clear();
		NBTTagList connectionsNbt = nbt.getTagList("Connections", 10);
		for(int i = 0; i < connectionsNbt.tagCount(); ++i) {
			connections.add(NBTUtil.getPosFromTag(connectionsNbt.getCompoundTagAt(i)));
		}
		NBTTagList inputsNbt = nbt.getTagList("Inputs", 10);
		for(int i = 0; i < inputsNbt.tagCount(); ++i) {
			inputs.add(new DirectionalGlobalPos(inputsNbt.getCompoundTagAt(i)));
		}
		needsNetworkRebuild = true;
	}
}
