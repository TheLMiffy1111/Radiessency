package thelm.radiessency.tile;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import thelm.radiessency.api.item.ISoulStoneItem;
import thelm.radiessency.api.matrix.IMatrixHandler;
import thelm.radiessency.api.radiessence.IRadiessenceStorage;
import thelm.radiessency.api.radiessence.RadiessenceStorageBeamOriginWrapper;
import thelm.radiessency.api.radiessence.TileRadiessenceStorage;
import thelm.radiessency.capability.RadiessencyCapabilities;
import thelm.radiessency.client.gui.MatrixExtractorGui;
import thelm.radiessency.container.MatrixExtractorContainer;
import thelm.radiessency.inventory.MatrixExtractorItemHandler;

public class MatrixExtractorTile extends BaseTile implements ITickable {

	public static int radiessenceCapacity = 5000;
	public static int operationInterval = 1;
	public static int matrixRadiessenceUsage = 10;
	public static float balanceGenerated = 1;
	public static int radiessenceGenerated = 1;

	public final MatrixExtractorItemHandler itemHandler = new MatrixExtractorItemHandler(this);
	public final TileRadiessenceStorage<MatrixExtractorTile> radiessenceStorage = new TileRadiessenceStorage<>(this, radiessenceCapacity);
	public final IRadiessenceStorage radiessenceStorageWrapper = new RadiessenceStorageBeamOriginWrapper<>(radiessenceStorage, true, false);

	public UUID matrixOwnerUUID;
	public boolean powered = false;
	public boolean firstTick = true;

	@Override
	protected String getDefaultName() {
		return I18n.translateToLocal("tile.radiessency.matrix_extractor.name");
	}

	@Override
	public MatrixExtractorItemHandler getItemHandler() {
		return itemHandler;
	}

	@Override
	public void update() {
		if(firstTick) {
			updateMatrix();
			updatePowered();
			firstTick = false;
		}
		if(!world.isRemote) {
			if(world.getTotalWorldTime() % operationInterval == 0 &&
					!powered && radiessenceStorage.insert(balanceGenerated, radiessenceGenerated, true).amount() == radiessenceGenerated) {
				Entity entity = world.getMinecraftServer().getEntityFromUuid(matrixOwnerUUID);
				if(entity != null && entity.hasCapability(RadiessencyCapabilities.MATRIX, null)) {
					IMatrixHandler matrixHandler = entity.getCapability(RadiessencyCapabilities.MATRIX, null);
					if(matrixHandler.getRadiessence() >= matrixRadiessenceUsage) {
						matrixHandler.removeRadiessence(matrixRadiessenceUsage);
						radiessenceStorage.insert(balanceGenerated, radiessenceGenerated, false);
					}
				}
			}
		}
	}

	public void updateMatrix() {
		if(!world.isRemote) {
			matrixOwnerUUID = null;
			ItemStack stack = itemHandler.getStackInSlot(0);
			if(stack.getItem() instanceof ISoulStoneItem) {
				matrixOwnerUUID = ((ISoulStoneItem)stack.getItem()).getOwnerUUID(stack);
			}
			syncTile(false);
		}
	}

	public void updatePowered() {
		if(world.getRedstonePowerFromNeighbors(pos) > 0 != powered) {
			powered = !powered;
		}
	}

	@Override
	public int getComparatorSignal() {
		if(radiessenceStorage.getAmount() == 0) {
			return 0;
		}
		return 1+(int)((long)radiessenceStorage.getAmount()*14/radiessenceStorage.getCapacity());
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		if(capability == RadiessencyCapabilities.RADIESSENCE) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T)itemHandler.getWrapperForDirection(facing);
		}
		if(capability == RadiessencyCapabilities.RADIESSENCE) {
			return (T)radiessenceStorageWrapper;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		radiessenceStorage.deserializeNBT(nbt.getCompoundTag("Radiessence"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setTag("Radiessence", radiessenceStorage.serializeNBT());
		return nbt;
	}

	@Override
	public void readSyncNBT(NBTTagCompound nbt) {
		super.readSyncNBT(nbt);
		itemHandler.deserializeNBT(nbt.getCompoundTag("Inventory"));
	}

	@Override
	public NBTTagCompound writeSyncNBT(NBTTagCompound nbt) {
		super.writeSyncNBT(nbt);
		nbt.setTag("Inventory", itemHandler.serializeNBT());
		return nbt;
	}

	@Override
	public MatrixExtractorContainer getServerGuiElement(EntityPlayer player, int id) {
		return new MatrixExtractorContainer(this, player.inventory);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public MatrixExtractorGui getClientGuiElement(EntityPlayer player, int id) {
		return new MatrixExtractorGui(getServerGuiElement(player, id));
	}
}
