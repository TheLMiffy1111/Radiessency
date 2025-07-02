package thelm.radiessency.api.enderpulse;

/**
 * An enderpulse storage is the unit of interaction with enderpulse inventories.
 */
public interface IEnderpulseStorage {

	/**
	 * Returns the amount of enderpulse currently stored in the storage.
	 * @return Amount of enderpulse currently stored.
	 */
	int getStored();

	/**
	 * Adds enderpulse to the storage. Returns quantity of enderpulse that was inserted.
	 * @param maxInsert Maximum amount of enderpulse to be inserted.
	 * @param simulate If TRUE, the insertion will only be simulated.
	 * @return Amount of radiessence that was (or would have been, if simulated) inserted into the storage.
	 */
	int insert(int maxInsert, boolean simulate);

	/**
	 * Removes enderpulse from the storage. Returns quantity of enderpulse that was extracted.
	 * @param maxExtract Maximum amount of enderpulse to be extracted.
	 * @param simulate If TRUE, the extraction will only be simulated.
	 * @return Amount of enderpulse that was (or would have been, if simulated) extracted from the storage.
	 */
	int extract(int maxExtract, boolean simulate);

	/**
	 * Returns the amount of enderpulse that can be stored in the storage.
	 * @return Amount of enderpulse that can be stored.
	 */
	int getCapacity();

	/**
	 * Returns if enderpulse can be inserted into this storage.
	 * @return If enderpulse can be inserted.
	 */
	boolean canInsert();

	/**
	 * Returns if this storage can have enderpulse extracted.
	 * @return If enderpulse can be extracted.
	 */
	boolean canExtract();
}
