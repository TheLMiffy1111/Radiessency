package thelm.radiessency.tile;

import java.util.UUID;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IWorldNameable;
import net.minecraftforge.items.ItemHandlerHelper;
import thelm.radiessency.inventory.TileItemHandler;
import thelm.radiessency.network.IGuiProvider;

public abstract class BaseTile extends TileEntity implements IWorldNameable, IGuiProvider {

	protected String customName = null;
	protected UUID ownerUUID = null;

	protected abstract String getDefaultName();

	public void setCustomName(String name) {
		customName = name;
	}

	@Override
	public String getName() {
		return customName != null ? customName : getDefaultName();
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(getName());
	}

	@Override
	public boolean hasCustomName() {
		return customName != null;
	}

	public void setOwner(EntityPlayer owner) {
		ownerUUID = owner.getUniqueID();
	}

	public UUID getOwnerUUID() {
		return ownerUUID;
	}

	public TileItemHandler<?> getItemHandler() {
		return null;
	}

	public int getComparatorSignal() {
		if(getItemHandler() != null) {
			return ItemHandlerHelper.calcRedstoneFromInventory(getItemHandler());
		}
		return 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		readSyncNBT(nbt);
		ownerUUID = null;
		if(nbt.hasUniqueId("OwnerUUID")) {
			ownerUUID = nbt.getUniqueId("OwnerUUID");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		writeSyncNBT(nbt);
		if(ownerUUID != null) {
			nbt.setUniqueId("OwnerUUID", ownerUUID);
		}
		return nbt;
	}

	public void readSyncNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("Name")) {
			customName = nbt.getString("Name");
		}
	}

	public NBTTagCompound writeSyncNBT(NBTTagCompound nbt) {
		if(customName != null) {
			nbt.setString("Name", customName);
		}
		return nbt;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readSyncNBT(pkt.getNbtCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, -10, getUpdateTag());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		readSyncNBT(tag);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = super.getUpdateTag();
		writeSyncNBT(nbt);
		return nbt;
	}

	public void syncTile(boolean rerender) {
		if(world != null && world.isBlockLoaded(pos)) {
			IBlockState state = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, state, state, 2 + (rerender ? 4 : 0));
		}
	}

	@Override
	public Container getServerGuiElement(EntityPlayer player, int id) {
		return null;
	}

	@Override
	public GuiContainer getClientGuiElement(EntityPlayer player, int id) {
		return null;
	}
}
