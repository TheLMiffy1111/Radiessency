package thelm.radiessency.api.radiessence;

public interface IModifiableRadiessenceStorage extends IRadiessenceStorage {

	/**
	 * Sets the balanced amount of radiessence stored in the storage.
	 * @param balancedAmount Balanced amount of radiessence to set.
	 */
	void setStored(BalancedAmount balancedAmount);

	/**
	 * Sets the balanced amount of radiessence stored in the storage.
	 * @param balance Balance to set.
	 * @param amount Amount of radiessence to set.
	 */
	void setStored(float balance, int amount);
	
	/**
	 * Sets the balance stored in the storage. 
	 * @param balance Balance to set.
	 */
	void setBalance(float balance);

	/**
	 * Sets the amount of radiessence stored in the storage.
	 * @param amount Amount of radiessence to set.
	 */
	void setAmount(int amount);

	/**
	 * Sets the amount of radiessence that can be stored in the storage.
	 * @param capacity Amount of radiessence to set.
	 */
	void setCapacity(int capacity);
}
