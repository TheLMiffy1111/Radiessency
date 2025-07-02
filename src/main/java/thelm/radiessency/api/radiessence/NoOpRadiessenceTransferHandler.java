package thelm.radiessency.api.radiessence;

public class NoOpRadiessenceTransferHandler implements IRadiessenceTransferHandler {

	public static final NoOpRadiessenceTransferHandler INSTANCE = new NoOpRadiessenceTransferHandler();

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public IRadiessenceTransfer prepare(int maxExtract) {
		return NoOpRadiessenceTransfer.INSTANCE;
	}
}
