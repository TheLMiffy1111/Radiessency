package thelm.radiessency.client.matrix;

import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import thelm.radiessency.api.matrix.IMatrixHandler;

public class ClientMatrixHandler implements IMatrixHandler {

	protected final UUID ownerUUID;

	protected int radiessence;
	protected int corruption;
	
	public ClientMatrixHandler(UUID ownerUUID) {
		this.ownerUUID = ownerUUID;
	}

	@Override
	public EntityLivingBase getOwner() {
		return Minecraft.getMinecraft().world.loadedEntityList.stream().
				filter(EntityLivingBase.class::isInstance).map(EntityLivingBase.class::cast).
				filter(e->ownerUUID.equals(e.getUniqueID())).findFirst().orElse(null);
	}

	@Override
	public int getRadiessence() {
		return radiessence;
	}

	@Override
	public int addRadiessence(int amount) {
		return 0;
	}

	@Override
	public int removeRadiessence(int amount) {
		return 0;
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
		return 0;
	}

	@Override
	public int removeCorruption(int amount) {
		return 0;
	}

	@Override
	public void setCorruption(int amount) {
		corruption = amount;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return null;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {}
}
