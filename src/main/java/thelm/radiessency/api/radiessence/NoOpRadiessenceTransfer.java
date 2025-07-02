package thelm.radiessency.api.radiessence;

public class NoOpRadiessenceTransfer implements IRadiessenceTransfer {
	
	public static final NoOpRadiessenceTransfer INSTANCE = new NoOpRadiessenceTransfer();

	@Override
	public BalancedAmount getExtracted() {
		return BalancedAmount.ZERO;
	}

	@Override
	public void execute() {}
}
