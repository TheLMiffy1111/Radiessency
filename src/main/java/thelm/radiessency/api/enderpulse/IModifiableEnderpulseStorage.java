package thelm.radiessency.api.enderpulse;

public interface IModifiableEnderpulseStorage extends IEnderpulseStorage {

	/**
	 * Sets the amount of enderpulse stored in the storage.
	 * @param stored Amount of enderpulse to set.
	 */
	void setStored(int stored);

	/**
	 * Sets the amount of enderpulse that can be stored in the storage.
	 * @param capacity Amount of enderpulse to set.
	 */
	void setCapacity(int capacity);
}
