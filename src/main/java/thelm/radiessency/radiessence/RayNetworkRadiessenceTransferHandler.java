package thelm.radiessency.radiessence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thelm.radiessency.api.radiessence.BalancedAmount;
import thelm.radiessency.api.radiessence.IRadiessenceStorage;
import thelm.radiessency.api.radiessence.IRadiessenceTransfer;
import thelm.radiessency.api.radiessence.IRadiessenceTransferHandler;
import thelm.radiessency.api.radiessence.NoOpRadiessenceTransfer;
import thelm.radiessency.api.transfer.DirectionalGlobalPos;
import thelm.radiessency.api.transfer.GlobalVec;
import thelm.radiessency.api.transfer.IRayNetwork;
import thelm.radiessency.api.transfer.IRayNetworkHandler;
import thelm.radiessency.capability.RadiessencyCapabilities;
import thelm.radiessency.util.MiscHelper;

public class RayNetworkRadiessenceTransferHandler implements IRadiessenceTransferHandler {

	protected final TileEntity towerTile;
	protected final Set<BlockPos> rayTowerExcluded;
	protected final GlobalVec beamTarget;

	protected IRayNetwork network;
	protected Map<DirectionalGlobalPos, IRayNetworkHandler> inputs;
	protected boolean invalid;

	protected int lastStorageCount = 10;

	public RayNetworkRadiessenceTransferHandler(TileEntity towerTile, Set<BlockPos> rayTowerExcluded, GlobalVec beamTarget) {
		this.towerTile = towerTile;
		this.rayTowerExcluded = rayTowerExcluded != null ? rayTowerExcluded : Collections.emptySet();
		this.beamTarget = beamTarget;
	}

	@Override
	public boolean isValid() {
		if(invalid) {
			return false;
		}
		if(towerTile.isInvalid() || !towerTile.hasCapability(RadiessencyCapabilities.RAY_NETWORK, null)) {
			invalid = true;
			return false;
		}
		if(network != null) {
			IRayNetworkHandler tower = towerTile.getCapability(RadiessencyCapabilities.RAY_NETWORK, null);
			if(!network.equals(tower.getNetwork()) || !network.getInputs().equals(inputs)) {
				invalid = true;
				return false;
			}
		}
		return true;
	}

	@Override
	public IRadiessenceTransfer prepare(int maxExtract) {
		if(!isValid() || maxExtract <= 0) {
			return NoOpRadiessenceTransfer.INSTANCE;
		}
		IRayNetworkHandler tower = towerTile.getCapability(RadiessencyCapabilities.RAY_NETWORK, null);
		if(network == null) {
			network = tower.getNetwork();
			inputs = network.getInputs();
		}
		World world = towerTile.getWorld();
		List<IRadiessenceStorage> storages = new ArrayList<>(lastStorageCount);
		List<IRayNetworkHandler> towers = new ArrayList<>(lastStorageCount);
		for(Map.Entry<DirectionalGlobalPos, IRayNetworkHandler> input : inputs.entrySet()) {
			DirectionalGlobalPos globalPos = input.getKey();
			if(!rayTowerExcluded.contains(globalPos.blockPos())) {
				TileEntity tile = world.getTileEntity(globalPos.blockPos());
				if(tile != null && tile.hasCapability(RadiessencyCapabilities.RADIESSENCE, globalPos.direction())) {
					IRadiessenceStorage storage = tile.getCapability(RadiessencyCapabilities.RADIESSENCE, globalPos.direction());
					if(storage.canExtract() && storage.getAmount() > 0) {
						storages.add(storage);
						towers.add(input.getValue());
					}
				}
			}
		}
		if(storages.isEmpty()) {
			return NoOpRadiessenceTransfer.INSTANCE;
		}
		lastStorageCount = storages.size();
		int[] capacities = storages.stream().mapToInt(IRadiessenceStorage::getAmount).toArray();
		int[] distributed = MiscHelper.INSTANCE.distributeTanks(capacities, maxExtract);
		List<BalancedAmount> extracted = new ArrayList<>(storages.size());
		for(int i = 0; i < storages.size(); ++i) {
			IRadiessenceStorage storage = storages.get(i);
			BalancedAmount extract = storage.extract(distributed[i], true);
			extracted.add(extract);
		}
		return new RayNetworkRadiessenceTransfer(this, storages, extracted, tower.getCenterPoint(), beamTarget);
	}
}
