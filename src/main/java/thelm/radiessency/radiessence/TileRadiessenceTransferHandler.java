package thelm.radiessency.radiessence;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import thelm.radiessency.api.radiessence.BalancedAmount;
import thelm.radiessency.api.radiessence.IRadiessenceStorage;
import thelm.radiessency.api.radiessence.IRadiessenceTransfer;
import thelm.radiessency.api.radiessence.IRadiessenceTransferHandler;
import thelm.radiessency.api.radiessence.NoOpRadiessenceTransfer;
import thelm.radiessency.api.transfer.GlobalVec;
import thelm.radiessency.api.transfer.IHasCenterPoint;
import thelm.radiessency.capability.RadiessencyCapabilities;

public class TileRadiessenceTransferHandler implements IRadiessenceTransferHandler {

	protected final TileEntity storageTile;
	protected final EnumFacing direction;
	protected final GlobalVec beamTarget;

	protected boolean invalid;

	public TileRadiessenceTransferHandler(TileEntity storageTile, EnumFacing direction, GlobalVec beamTarget) {
		this.storageTile = storageTile;
		this.direction = direction;
		this.beamTarget = beamTarget;
	}

	@Override
	public boolean isValid() {
		if(invalid) {
			return false;
		}
		if(storageTile.isInvalid() || !storageTile.hasCapability(RadiessencyCapabilities.RADIESSENCE, direction)) {
			invalid = true;
			return false;
		}
		return true;
	}

	@Override
	public IRadiessenceTransfer prepare(int maxExtract) {
		if(!isValid() || maxExtract <= 0) {
			return NoOpRadiessenceTransfer.INSTANCE;
		}
		IRadiessenceStorage storage = storageTile.getCapability(RadiessencyCapabilities.RADIESSENCE, direction);
		if(!storage.canExtract() || storage.getAmount() <= 0) {
			return NoOpRadiessenceTransfer.INSTANCE;
		}
		BalancedAmount extracted = storage.extract(maxExtract, true);
		GlobalVec beamSource;
		if(storage instanceof IHasCenterPoint) {
			beamSource = ((IHasCenterPoint)storage).getCenterPoint();
		}
		else {
			beamSource = new GlobalVec(storageTile.getWorld().provider.getDimension(), storageTile.getPos(), direction);
		}
		return new TileRadiessenceTransfer(this, storage, extracted, beamSource, beamTarget);
	}

}
