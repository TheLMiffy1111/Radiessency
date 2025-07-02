package thelm.radiessency.api.radiessence;

public class RadiessenceStorageWrapper<S extends IModifiableRadiessenceStorage> implements IModifiableRadiessenceStorage {

	protected S storage;

	protected boolean disableInsert = false;
	protected boolean disableExtract = false;

	public RadiessenceStorageWrapper(S storage) {
		this.storage = storage;
	}

	public RadiessenceStorageWrapper(S storage, boolean disableInsert, boolean disableExtract) {
		this.storage = storage;
		this.disableInsert = disableInsert;
		this.disableExtract = disableExtract;
	}

	@Override
	public BalancedAmount getStored() {
		return storage.getStored();
	}

	@Override
	public void setStored(BalancedAmount balancedAmount) {
		storage.setStored(balancedAmount);
	}

	@Override
	public void setStored(float balance, int amount) {
		storage.setStored(balance, amount);
	}

	@Override
	public float getBalance() {
		return storage.getBalance();
	}

	@Override
	public void setBalance(float balance) {
		storage.setBalance(balance);
	}

	@Override
	public int getAmount() {
		return storage.getAmount();
	}

	@Override
	public void setAmount(int amount) {
		storage.setAmount(amount);
	}

	@Override
	public BalancedAmount insert(BalancedAmount maxInsert, boolean simulate) {
		return storage.insert(maxInsert, simulate);
	}

	@Override
	public BalancedAmount insert(float balance, int maxInsert, boolean simulate) {
		return storage.insert(balance, maxInsert, simulate);
	}

	@Override
	public BalancedAmount insert(int maxInsert, boolean simulate) {
		return storage.insert(maxInsert, simulate);
	}

	@Override
	public BalancedAmount extract(int maxExtract, boolean simulate) {
		return storage.extract(maxExtract, simulate);
	}

	@Override
	public int getCapacity() {
		return storage.getCapacity();
	}

	@Override
	public void setCapacity(int capacity) {
		storage.setCapacity(capacity);
	}

	@Override
	public boolean canInsert(float balance) {
		return !disableInsert && storage.canInsert(balance);
	}

	@Override
	public boolean canInsert() {
		return !disableInsert && storage.canInsert();
	}

	@Override
	public boolean canExtract() {
		return !disableExtract && storage.canExtract();
	}
}
