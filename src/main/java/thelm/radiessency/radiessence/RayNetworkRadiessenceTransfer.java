package thelm.radiessency.radiessence;

import java.util.List;

import thelm.radiessency.api.radiessence.BalancedAmount;
import thelm.radiessency.api.radiessence.IRadiessenceStorage;
import thelm.radiessency.api.radiessence.IRadiessenceTransfer;
import thelm.radiessency.api.transfer.GlobalVec;
import thelm.radiessency.util.ApiImpl;

public class RayNetworkRadiessenceTransfer implements IRadiessenceTransfer {

	protected final RayNetworkRadiessenceTransferHandler handler;
	protected final List<IRadiessenceStorage> storages;
	protected final List<BalancedAmount> extracted;
	protected final GlobalVec source;
	protected final GlobalVec dest;
	protected final BalancedAmount sum;

	protected boolean executed;

	public RayNetworkRadiessenceTransfer(RayNetworkRadiessenceTransferHandler handler, List<IRadiessenceStorage> storages, List<BalancedAmount> extracted, GlobalVec source, GlobalVec dest) {
		this.handler = handler;
		this.storages = storages;
		this.extracted = extracted;
		this.source = source;
		this.dest = dest;
		sum = BalancedAmount.sum(extracted);
	}

	@Override
	public BalancedAmount getExtracted() {
		return sum;
	}

	@Override
	public void execute() {
		if(executed || !handler.isValid()) {
			return;
		}
		for(int i = 0; i < storages.size(); ++i) {
			storages.get(i).extract(extracted.get(i).amount(), false);
		}
		if(source != null && dest != null) {
			ApiImpl.INSTANCE.addBeam(source, dest, 4, ApiImpl.INSTANCE.radiessenceColor(sum.balance()), 6);
		}
		executed = true;
	}
}
