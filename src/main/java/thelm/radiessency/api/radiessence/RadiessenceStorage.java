package thelm.radiessency.api.radiessence;

import com.google.common.primitives.Floats;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import thelm.radiessency.api.RadiessencyApi;

public class RadiessenceStorage implements IModifiableRadiessenceStorage, INBTSerializable<NBTTagCompound>, ICapabilityProvider {

	protected int capacity;

	protected float balance = 1;
	protected int amount;

	public RadiessenceStorage(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public BalancedAmount getStored() {
		return new BalancedAmount(balance, amount);
	}

	@Override
	public void setStored(BalancedAmount balancedAmount) {
		setStored(balancedAmount.balance(), balancedAmount.amount());
	}

	@Override
	public void setStored(float balance, int amount) {
		this.balance = Floats.constrainToRange(balance, 0, 2);
		this.amount = amount;
		onChanged();
	}

	@Override
	public float getBalance() {
		return balance;
	}

	@Override
	public void setBalance(float balance) {
		this.balance = Floats.constrainToRange(balance, 0, 2);
		onChanged();
	}

	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public void setAmount(int amount) {
		this.amount = amount;
		onChanged();
	}

	@Override
	public BalancedAmount insert(BalancedAmount maxInsert, boolean simulate) {
		return insert(maxInsert.balance(), maxInsert.amount(), simulate);
	}

	@Override
	public BalancedAmount insert(float balance, int maxInsert, boolean simulate) {
		if(maxInsert <= 0 || amount >= capacity || !canInsert(balance)) {
			return new BalancedAmount(this.balance, 0);
		}
		maxInsert = Math.min(maxInsert, capacity-amount);
		balance = averageBalance(Floats.constrainToRange(balance, 0, 2), maxInsert, this.balance, amount);
		if(!simulate) {
			this.balance = balance;
			amount += maxInsert;
			onChanged();
		}
		return new BalancedAmount(balance, maxInsert);
	}

	@Override
	public BalancedAmount insert(int maxInsert, boolean simulate) {
		if(maxInsert <= 0 || amount >= capacity || !canInsert(balance)) {
			return new BalancedAmount(this.balance, 0);
		}
		maxInsert = Math.min(maxInsert, capacity-amount);
		if(!simulate) {
			amount += maxInsert;
			onChanged();
		}
		return new BalancedAmount(balance, maxInsert);
	}

	@Override
	public BalancedAmount extract(int maxExtract, boolean simulate) {
		if(maxExtract <= 0 || amount <= 0 || !canExtract()) {
			return new BalancedAmount(balance, 0);
		}
		maxExtract = Math.min(maxExtract, amount);
		if(!simulate) {
			amount -= maxExtract;
			onChanged();
		}
		return new BalancedAmount(balance, maxExtract);
	}

	@Override
	public int getCapacity() {
		return capacity;
	}

	@Override
	public void setCapacity(int capacity) {
		this.capacity = capacity;
		onChanged();
	}

	@Override
	public boolean canInsert(float balance) {
		return true;
	}

	@Override
	public boolean canInsert() {
		return true;
	}

	@Override
	public boolean canExtract() {
		return true;
	}

	public void onChanged() {

	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat("Balance", balance);
		nbt.setInteger("Amount", amount);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		balance = nbt.getFloat("Balance");
		amount = nbt.getInteger("Amount");
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == RadiessencyApi.instance().radiessenceCapability();
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == RadiessencyApi.instance().radiessenceCapability() ? (T)this : null;
	}

	public static float averageBalance(float balance1, int amount1, float balance2, int amount2) {
		long amountSum = amount1+amount2;
		if(amountSum <= 0) {
			return balance1;
		}
		return (balance1*amount1+balance2*amount2)/amountSum;
	}
}
