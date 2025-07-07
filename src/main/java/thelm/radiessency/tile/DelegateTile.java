package thelm.radiessency.tile;

import java.util.UUID;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.radiessency.inventory.TileItemHandler;

public abstract class DelegateTile extends BaseTile {

	public abstract Vec3i getOffset();
	
	public TileEntity getActualTile() {
		return world.getTileEntity(pos.add(getOffset()));
	}

	@Override
	protected String getDefaultName() {
		TileEntity tile = getActualTile();
		if(tile instanceof BaseTile) {
			return ((BaseTile)tile).getDefaultName();
		}
		return "Delegate";
	}

	@Override
	public String getName() {
		TileEntity tile = getActualTile();
		if(tile instanceof BaseTile) {
			return ((BaseTile)tile).getName();
		}
		return super.getName();
	}

	@Override
	public ITextComponent getDisplayName() {
		TileEntity tile = getActualTile();
		if(tile instanceof BaseTile) {
			return ((BaseTile)tile).getDisplayName();
		}
		return super.getDisplayName();
	}

	@Override
	public UUID getOwnerUUID() {
		TileEntity tile = getActualTile();
		if(tile instanceof BaseTile) {
			return ((BaseTile)tile).getOwnerUUID();
		}
		return super.getOwnerUUID();
	}

	@Override
	public TileItemHandler<?> getItemHandler() {
		TileEntity tile = getActualTile();
		if(tile instanceof BaseTile) {
			return ((BaseTile)tile).getItemHandler();
		}
		return null;
	}

	@Override
	public int getComparatorSignal() {
		TileEntity tile = getActualTile();
		if(tile instanceof BaseTile) {
			return ((BaseTile)tile).getComparatorSignal();
		}
		return super.getComparatorSignal();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		TileEntity tile = getActualTile();
		if(tile != null) {
			return tile.hasCapability(capability, facing);
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		TileEntity tile = getActualTile();
		if(tile != null) {
			return tile.getCapability(capability, facing);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public Container getServerGuiElement(EntityPlayer player, int id) {
		TileEntity tile = getActualTile();
		if(tile instanceof BaseTile) {
			return ((BaseTile)tile).getServerGuiElement(player, id);
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer getClientGuiElement(EntityPlayer player, int id) {
		TileEntity tile = getActualTile();
		if(tile instanceof BaseTile) {
			return ((BaseTile)tile).getClientGuiElement(player, id);
		}
		return null;
	}
}
