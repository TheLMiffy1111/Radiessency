package thelm.radiessency.api.radiessence;

public interface IRadiessenceTransferHandler {

	/**
	 * Checks if this transfer handler is valid and usable.
	 * @return If this transfer handler valid.
	 */
	boolean isValid();

	/**
	 * Prepares an extraction of radiessence from this handler.
	 * @param maxExtract Maximum amount of radiessence to be extracted.
	 * @return A radiessence transfer object that can be executed.
	 */
	IRadiessenceTransfer prepare(int maxExtract);
}
