package thelm.radiessency.matrix;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import thelm.radiessency.api.matrix.IMatrixHandler;
import thelm.radiessency.capability.RadiessencyCapabilities;

public class MatrixHandler implements IMatrixHandler, ICapabilityProvider {

	protected final EntityLivingBase owner;

	protected int radiessence;
	protected int corruption;

	public MatrixHandler(EntityLivingBase owner) {
		this.owner = owner;
	}

	@Override
	public EntityLivingBase getOwner() {
		return owner;
	}

	@Override
	public int getRadiessence() {
		return radiessence;
	}

	@Override
	public int addRadiessence(int amount) {
		amount = Math.min(amount, Integer.MAX_VALUE-radiessence);
		radiessence += amount;
		return amount;
	}

	@Override
	public int removeRadiessence(int amount) {
		amount = Math.min(amount, radiessence);
		radiessence -= amount;
		return amount;
	}

	@Override
	public void setRadiessence(int amount) {
		radiessence = amount;
	}

	@Override
	public int getCorruption() {
		return corruption;
	}

	@Override
	public int addCorruption(int amount) {
		amount = Math.min(amount, Integer.MAX_VALUE-corruption);
		corruption += amount;
		return amount;
	}

	@Override
	public int removeCorruption(int amount) {
		amount = Math.min(amount, corruption);
		corruption -= amount;
		return amount;
	}

	@Override
	public void setCorruption(int amount) {
		corruption = amount;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("Radiessence", radiessence);
		nbt.setInteger("Corruption", corruption);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		radiessence = nbt.getInteger("Radiessence");
		corruption = nbt.getInteger("Corruption");
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == RadiessencyCapabilities.MATRIX;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == RadiessencyCapabilities.MATRIX ? (T)this : null;
	}
}
