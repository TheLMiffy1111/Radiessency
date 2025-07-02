package thelm.radiessency.radiessence;

import thelm.radiessency.api.radiessence.BalancedAmount;
import thelm.radiessency.api.radiessence.IRadiessenceStorage;
import thelm.radiessency.api.radiessence.IRadiessenceTransfer;
import thelm.radiessency.api.transfer.GlobalVec;
import thelm.radiessency.util.ApiImpl;

public class TileRadiessenceTransfer implements IRadiessenceTransfer {

	protected final TileRadiessenceTransferHandler handler;
	protected final IRadiessenceStorage storage;
	protected final BalancedAmount extracted;
	protected final GlobalVec source;
	protected final GlobalVec dest;
	protected boolean executed;

	public TileRadiessenceTransfer(TileRadiessenceTransferHandler handler, IRadiessenceStorage storage, BalancedAmount extracted, GlobalVec source, GlobalVec dest) {
		this.handler = handler;
		this.storage = storage;
		this.extracted = extracted;
		this.source = source;
		this.dest = dest;
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
		if(source != null && dest != null) {
			ApiImpl.INSTANCE.addBeam(source, dest, 4, ApiImpl.INSTANCE.radiessenceColor(extracted.balance()), 6);
		}
		executed = true;
	}
}
