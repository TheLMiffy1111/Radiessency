package thelm.radiessency.radiessence;

import net.minecraftforge.items.IItemHandler;
import thelm.radiessency.api.radiessence.BalancedAmount;
import thelm.radiessency.api.radiessence.IRadiessenceStorage;
import thelm.radiessency.api.radiessence.IRadiessenceTransfer;
import thelm.radiessency.api.radiessence.IRadiessenceTransferHandler;
import thelm.radiessency.api.radiessence.NoOpRadiessenceTransfer;
import thelm.radiessency.capability.RadiessencyCapabilities;

public class InventoryRadiessenceTransferHandler implements IRadiessenceTransferHandler {

	protected final IItemHandler inventory;
	protected final int slot;

	protected boolean invalid;

	public InventoryRadiessenceTransferHandler(IItemHandler inventory, int slot) {
		this.inventory = inventory;
		this.slot = slot;
	}

	@Override
	public boolean isValid() {
		if(invalid) {
			return false;
		}
		if(!inventory.getStackInSlot(slot).hasCapability(RadiessencyCapabilities.RADIESSENCE, null)) {
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
		IRadiessenceStorage storage = inventory.getStackInSlot(slot).getCapability(RadiessencyCapabilities.RADIESSENCE, null);
		if(!storage.canExtract() || storage.getAmount() <= 0) {
			return NoOpRadiessenceTransfer.INSTANCE;
		}
		BalancedAmount extracted = storage.extract(maxExtract, true);
		return new InventoryRadiessenceTransfer(this, storage, extracted);
	}

}
