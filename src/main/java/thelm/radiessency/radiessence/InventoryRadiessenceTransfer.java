package thelm.radiessency.radiessence;

import thelm.radiessency.api.radiessence.BalancedAmount;
import thelm.radiessency.api.radiessence.IRadiessenceStorage;
import thelm.radiessency.api.radiessence.IRadiessenceTransfer;

public class InventoryRadiessenceTransfer implements IRadiessenceTransfer {

	protected final ItemRadiessenceTransferHandler handler;
	protected final IRadiessenceStorage storage;
	protected final BalancedAmount extracted;

	protected boolean executed;

	public InventoryRadiessenceTransfer(ItemRadiessenceTransferHandler handler, IRadiessenceStorage storage, BalancedAmount extracted) {
		this.handler = handler;
		this.storage = storage;
		this.extracted = extracted;
	}

	@Override
	public BalancedAmount getExtracted() {
		return extracted;
	}

	@Override
	public void execute() {
		if(executed || !handler.isValid()) {
			return;
		}
		storage.extract(extracted.amount(), false);
		executed = true;
	}
}
