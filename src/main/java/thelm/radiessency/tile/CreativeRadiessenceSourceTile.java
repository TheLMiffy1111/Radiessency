package thelm.radiessency.tile;

import com.google.common.primitives.Floats;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.radiessency.api.radiessence.IRadiessenceStorage;
import thelm.radiessency.api.radiessence.RadiessenceStorageBeamOriginWrapper;
import thelm.radiessency.api.radiessence.TileRadiessenceStorage;
import thelm.radiessency.capability.RadiessencyCapabilities;
import thelm.radiessency.client.gui.CreativeRadiessenceSourceGui;
import thelm.radiessency.container.CreativeRadiessenceSourceContainer;

public class CreativeRadiessenceSourceTile extends BaseTile implements ITickable {

	public final TileRadiessenceStorage<CreativeRadiessenceSourceTile> radiessenceStorage = new TileRadiessenceStorage<>(this, 5000);
	public final IRadiessenceStorage radiessenceStorageWrapper = new RadiessenceStorageBeamOriginWrapper<>(radiessenceStorage, true, false);

	public float balance = 1;
	public int amount = 5000;

	@Override
	protected String getDefaultName() {
		return I18n.translateToLocal("tile.radiessency.creative_radiessence_source.name");
	}

	@Override
	public void update() {
		radiessenceStorage.setStored(balance, amount);
	}

	public void setStored(float balance, int amount) {
		this.balance = Floats.constrainToRange(balance, 0, 2);
		this.amount = Math.max(amount, 0);
		radiessenceStorage.setCapacity(amount);
	}

	@Override
	public void readSyncNBT(NBTTagCompound nbt) {
		super.readSyncNBT(nbt);
		balance = nbt.getFloat("Balance");
		amount = nbt.getInteger("Amount");
		radiessenceStorage.setCapacity(amount);
	}

	@Override
	public NBTTagCompound writeSyncNBT(NBTTagCompound nbt) {
		super.writeSyncNBT(nbt);
		nbt.setFloat("Balance", balance);
		nbt.setInteger("Amount", amount);
		return nbt;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == RadiessencyCapabilities.RADIESSENCE) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == RadiessencyCapabilities.RADIESSENCE) {
			return (T)radiessenceStorageWrapper;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public CreativeRadiessenceSourceContainer getServerGuiElement(EntityPlayer player, int id) {
		syncTile(false);
		return new CreativeRadiessenceSourceContainer(this, player.inventory);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public CreativeRadiessenceSourceGui getClientGuiElement(EntityPlayer player, int id) {
		return new CreativeRadiessenceSourceGui(getServerGuiElement(player, id));
	}
}
