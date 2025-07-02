package thelm.radiessency.api.radiessence;

/**
 * An radiessence storage is the unit of interaction with radiessence inventories.
 */
public interface IRadiessenceStorage {

	/**
	 * Returns the balanced amount of radiessence currently stored in the storage.
	 * @return Balanced amount of radiessence currently stored.
	 */
	BalancedAmount getStored();

	/**
	 * Returns the balance of the storage currently stored in the storage.
	 * @return The balance currently stored.
	 */
	float getBalance();

	/**
	 * Returns the amount of radiessence currently stored in the storage.
	 * @return Amount of radiessence currently stored.
	 */
	int getAmount();

	/**
	 * Adds radiessence to the storage. Returns the balanced amount of radiessence that was inserted.
	 * @param maxInsert Maximum amount of balanced radiessence to be inserted.
	 * @param simulate If TRUE, the insertion will only be simulated.
	 * @return Amount of balanced radiessence that was (or would have been, if simulated) inserted.
	 */
	BalancedAmount insert(BalancedAmount maxInsert, boolean simulate);

	/**
	 * Adds radiessence to the storage. Returns the balanced amount of radiessence that was inserted.
	 * @param balance The balance of the radiessence to be inserted.
	 * @param maxInsert Maximum amount of radiessence to be inserted.
	 * @param simulate If TRUE, the insertion will only be simulated.
	 * @return Balanced amount of radiessence that was (or would have been, if simulated) inserted.
	 */
	BalancedAmount insert(float balance, int maxInsert, boolean simulate);

	/**
	 * Adds radiessence to the storage. Returns the balanced amount of radiessence that was inserted.
	 * @param maxInsert Maximum amount of radiessence to be inserted.
	 * @param simulate If TRUE, the insertion will only be simulated.
	 * @return Balanced amount of radiessence that was (or would have been, if simulated) inserted.
	 */
	BalancedAmount insert(int maxInsert, boolean simulate);

	/**
	 * Removes radiessence from the storage. Returns the balanced amount of radiessence that was extracted.
	 * @param maxExtract Maximum amount of radiessence to be extracted.
	 * @param simulate If TRUE, the extraction will only be simulated.
	 * @return Balanced amount of radiessence that was (or would have been, if simulated) extracted.
	 */
	BalancedAmount extract(int maxExtract, boolean simulate);

	/**
	 * Returns the amount of radiessence that can be stored in the storage.
	 * @return Amount of radiessence that can be stored.
	 */
	int getCapacity();

	/**
	 * Returns if radiessence can be inserted into this storage.
	 * @param balance The balance of the radiessence to be inserted.
	 * @return If radiessence can be inserted.
	 */
	boolean canInsert(float balance);

	/**
	 * Returns if radiessence can be inserted into this storage.
	 * @return If radiessence can be inserted.
	 */
	boolean canInsert();

	/**
	 * Returns if this storage can have radiessence extracted.
	 * @return If radiessence can be extracted.
	 */
	boolean canExtract();
}
