package thelm.radiessency.api.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandlerModifiable;

public abstract class SidedItemHandlerWrapper<H extends IItemHandlerModifiable> implements IItemHandlerModifiable {

	protected final H itemHandler;
	protected final EnumFacing direction;

	public SidedItemHandlerWrapper(H itemHandler, EnumFacing direction) {
		this.itemHandler = itemHandler;
		this.direction = direction;
	}

	public int getSlot(int slot, EnumFacing direction) {
		int[] slots = getSlotsForDirection(direction);
		if(slot < slots.length) {
			return slots[slot];
		}
		return -1;
	}

	@Override
	public int getSlots() {
		return getSlotsForDirection(direction).length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		int i = getSlot(slot, direction);
		return i == -1 ? ItemStack.EMPTY : itemHandler.getStackInSlot(i);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		int i = getSlot(slot, direction);
		return i == -1 || !canInsertItem(i, stack, direction) ? stack : itemHandler.insertItem(i, stack, simulate);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		int i = getSlot(slot, direction);
		return i == -1 || !canExtractItem(i, direction) ? ItemStack.EMPTY : itemHandler.extractItem(i, amount, simulate);
	}

	@Override
	public int getSlotLimit(int slot) {
		int i = getSlot(slot, direction);
		return i == -1 ? 0 : itemHandler.getSlotLimit(i);
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		int i = getSlot(slot, direction);
		return i == -1 ? false : itemHandler.isItemValid(i, stack);
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		int i = getSlot(slot, direction);
		if(i != -1) {
			itemHandler.setStackInSlot(i, stack);
		}
	}

	public abstract int[] getSlotsForDirection(EnumFacing direction);

	public abstract boolean canInsertItem(int slot, ItemStack stack, EnumFacing direction);

	public abstract boolean canExtractItem(int slot, EnumFacing direction);
}
