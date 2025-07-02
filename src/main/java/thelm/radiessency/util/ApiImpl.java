package thelm.radiessency.util;

import java.util.Set;
import java.util.function.Predicate;

import javax.vecmath.TexCoord3f;
import javax.vecmath.Tuple3f;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.IItemHandler;
import thelm.radiessency.api.IMiscHelper;
import thelm.radiessency.api.RadiessencyApi;
import thelm.radiessency.api.enderpulse.IEnderpulseStorage;
import thelm.radiessency.api.item.IBindingGemItem;
import thelm.radiessency.api.matrix.IMatrixHandler;
import thelm.radiessency.api.radiessence.IRadiessenceStorage;
import thelm.radiessency.api.radiessence.IRadiessenceTransferHandler;
import thelm.radiessency.api.radiessence.NoOpRadiessenceTransferHandler;
import thelm.radiessency.api.transfer.DirectionalGlobalPos;
import thelm.radiessency.api.transfer.GlobalVec;
import thelm.radiessency.api.transfer.IRayNetworkHandler;
import thelm.radiessency.capability.RadiessencyCapabilities;
import thelm.radiessency.client.renderer.BeamHandler;
import thelm.radiessency.network.packet.AddBeamPacket;
import thelm.radiessency.radiessence.InventoryRadiessenceTransferHandler;
import thelm.radiessency.radiessence.RayNetworkRadiessenceTransferHandler;
import thelm.radiessency.radiessence.TileRadiessenceTransferHandler;

public class ApiImpl extends RadiessencyApi {

	public static final ApiImpl INSTANCE = new ApiImpl();

	private ApiImpl() {
		instance = this;
	}

	public void init() {}

	@Override
	public IMiscHelper miscHelper() {
		return MiscHelper.INSTANCE;
	}

	@Override
	public Capability<IRadiessenceStorage> radiessenceCapability() {
		return RadiessencyCapabilities.RADIESSENCE;
	}

	@Override
	public Capability<IEnderpulseStorage> enderpulseCapability() {
		return RadiessencyCapabilities.ENDERPULSE;
	}

	@Override
	public Capability<IMatrixHandler> matrixCapability() {
		return RadiessencyCapabilities.MATRIX;
	}

	@Override
	public Capability<IRayNetworkHandler> rayNetworkCapability() {
		return RadiessencyCapabilities.RAY_NETWORK;
	}

	@Override
	public DirectionalGlobalPos getDirectionalGlobalPos(ItemStack stack) {
		if(stack.getItem() instanceof IBindingGemItem) {
			return ((IBindingGemItem)stack.getItem()).getDirectionalGlobalPos(stack);
		}
		return null;
	}

	@Override
	public TileEntity getBoundTile(World world, ItemStack stack, Predicate<DirectionalGlobalPos> predicate) {
		if(world == null || world.isRemote) {
			return null;
		}
		DirectionalGlobalPos globalPos = getDirectionalGlobalPos(stack);
		if(globalPos == null || !predicate.test(globalPos)) {
			return null;
		}
		int dimension = globalPos.dimension();
		boolean crossDimension = world.provider.getDimension() != dimension;
		if(crossDimension) {
			world = DimensionManager.getWorld(dimension);
			if(world == null) {
				return null;
			}
		}
		BlockPos storagePos = globalPos.blockPos();
		if(!world.isBlockLoaded(storagePos)) {
			return null;
		}
		return world.getTileEntity(storagePos);
	}

	@Override
	public IRadiessenceTransferHandler getRadiessenceTransferHandler(World world, IItemHandler inventory, int slot, Predicate<DirectionalGlobalPos> predicate, Set<BlockPos> rayTowerExcluded, GlobalVec beamTarget) {
		if(world == null || world.isRemote) {
			return NoOpRadiessenceTransferHandler.INSTANCE;
		}
		ItemStack stack = inventory.getStackInSlot(slot);
		if(stack.hasCapability(radiessenceCapability(), null)) {
			return new InventoryRadiessenceTransferHandler(inventory, slot);
		}
		DirectionalGlobalPos globalPos = getDirectionalGlobalPos(stack);
		if(globalPos == null || !predicate.test(globalPos)) {
			return NoOpRadiessenceTransferHandler.INSTANCE;
		}
		int dimension = globalPos.dimension();
		boolean crossDimension = world.provider.getDimension() != dimension;
		if(crossDimension) {
			world = DimensionManager.getWorld(dimension);
			if(world == null) {
				return NoOpRadiessenceTransferHandler.INSTANCE;
			}
		}
		BlockPos storagePos = globalPos.blockPos();
		if(!world.isBlockLoaded(storagePos)) {
			return NoOpRadiessenceTransferHandler.INSTANCE;
		}
		EnumFacing direction = globalPos.direction();
		TileEntity tile = world.getTileEntity(storagePos);
		if(tile == null) {
			return NoOpRadiessenceTransferHandler.INSTANCE;
		}
		else if(tile.hasCapability(radiessenceCapability(), direction)) {
			return new TileRadiessenceTransferHandler(tile, direction, beamTarget);
		}
		else if(!crossDimension && tile.hasCapability(rayNetworkCapability(), direction)) {
			return new RayNetworkRadiessenceTransferHandler(tile, rayTowerExcluded, beamTarget);
		}
		return NoOpRadiessenceTransferHandler.INSTANCE;
	}

	public static final Tuple3f COLOR_BALANCED = new TexCoord3f(0.54F, 0.15F, 0.73F);
	public static final Tuple3f COLOR_STASIS = new TexCoord3f(0.15F, 0.15F, 0.73F);
	public static final Tuple3f COLOR_CHAOS = new TexCoord3f(0.73F, 0.15F, 0.15F);

	@Override
	public int radiessenceColor(float balance) {
		Tuple3f color = new TexCoord3f(COLOR_BALANCED);
		float overlayWeight = MathHelper.clamp(Math.abs(1-balance), 0, 1);
		Tuple3f overlay = balance < 1 ? COLOR_STASIS : COLOR_CHAOS;
		color.interpolate(overlay, overlayWeight);
		color.clamp(0, 1);
		return 0xFF000000 | (int)(color.x*255) << 16 | (int)(color.y*255) << 8 | (int)(color.z*255);
	}

	@Override
	public int enderpulseColor() {
		return 0xFF25B925;
	}

	@Override
	public void addBeam(int dimension, Vec3d source, Vec3d delta, float width, int color, int lifetime) {
		MiscHelper.INSTANCE.conditionalRunnable(FMLCommonHandler.instance().getSide()::isClient,
				()->()->{
					World world = Minecraft.getMinecraft().world;
					if(world != null && world.provider.getDimension() == dimension) {
						float r = (color >>> 16 & 0xFF)/255F;
						float g = (color >>> 8 & 0xFF)/255F;
						float b = (color & 0xFF)/255F;
						float a = (color >>> 24 & 0xFF)/255F;
						BeamHandler.INSTANCE.addBeam(source, delta, width, r, g, b, a, lifetime);	
					}
				}, ()->()->{
					AddBeamPacket.sendBeam(source, dimension, 32, delta, width, color, lifetime);
				}).run();
	}

	@Override
	public void addBeam(GlobalVec source, GlobalVec dest, float width, int color, int lifetime) {
		if(source.dimension() == dest.dimension()) {
			Vec3d origin = source.vec();
			Vec3d delta = dest.vec().subtract(origin);
			addBeam(source.dimension(), origin, delta, width, color, lifetime);
		}
	}
}
