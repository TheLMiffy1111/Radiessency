package thelm.radiessency.api.matrix;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IMatrixHandler extends INBTSerializable<NBTTagCompound> {

	/**
	 * Returns the owner of this matrix.
	 * @return Owner of this matrix.
	 */
	EntityLivingBase getOwner();

	/**
	 * Returns the amount of radiessence currently held in the matrix.
	 * @return Amount of radiessence currently held.
	 */
	int getRadiessence();

	/**
	 * Adds radiessence to the matrix.
	 * @param amount Amount of radiessence to add.
	 * @return Actual amount of radiessence that was added.
	 */
	int addRadiessence(int amount);

	/**
	 * Removes radiessence from the matrix.
	 * @param amount Amount of radiessence to remove.
	 * @return Actual amount of radiessence that was removed.
	 */
	int removeRadiessence(int amount);

	/**
	 * Sets the radiessence of the matrix. 
	 * @param amount Amount of radiessence to set.
	 */
	void setRadiessence(int amount);

	/**
	 * Returns the amount of corruption currently held.
	 * @return Amount of corruption currently held.
	 */
	int getCorruption();

	/**
	 * Adds corruption to the matrix.
	 * @param amount Amount of corruption to add.
	 * @return Actual amount of corruption that was added.
	 */
	int addCorruption(int amount);

	/**
	 * Removes corruption from the matrix.
	 * @param amount Amount of corruption to remove.
	 * @return Actual amount of corruption that was removed.
	 */
	int removeCorruption(int amount);

	/**
	 * Sets the corruption of the matrix. 
	 * @param amount Amount of corruption to set.
	 */
	void setCorruption(int amount);
}
