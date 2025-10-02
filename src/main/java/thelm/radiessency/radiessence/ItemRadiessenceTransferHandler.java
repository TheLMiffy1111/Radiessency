package thelm.radiessency.radiessence;

import java.util.function.Supplier;

import net.minecraft.item.ItemStack;
import thelm.radiessency.api.radiessence.BalancedAmount;
import thelm.radiessency.api.radiessence.IRadiessenceStorage;
import thelm.radiessency.api.radiessence.IRadiessenceTransfer;
import thelm.radiessency.api.radiessence.IRadiessenceTransferHandler;
import thelm.radiessency.api.radiessence.NoOpRadiessenceTransfer;
import thelm.radiessency.capability.RadiessencyCapabilities;

public class ItemRadiessenceTransferHandler implements IRadiessenceTransferHandler {

	protected final Supplier<ItemStack> item;

	protected boolean invalid;

	public ItemRadiessenceTransferHandler(Supplier<ItemStack> item) {
		this.item = item;
	}

	@Override
	public boolean isValid() {
		if(invalid) {
			return false;
		}
		ItemStack stack = item.get();
		if(stack == null || stack.isEmpty() || !stack.hasCapability(RadiessencyCapabilities.RADIESSENCE, null)) {
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
		IRadiessenceStorage storage = item.get().getCapability(RadiessencyCapabilities.RADIESSENCE, null);
		if(!storage.canExtract() || storage.getAmount() <= 0) {
			return NoOpRadiessenceTransfer.INSTANCE;
		}
		BalancedAmount extracted = storage.extract(maxExtract, true);
		return new InventoryRadiessenceTransfer(this, storage, extracted);
	}

}
