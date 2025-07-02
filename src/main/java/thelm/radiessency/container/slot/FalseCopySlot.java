package thelm.radiessency.container.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class FalseCopySlot extends SlotItemHandler {

	public int slotIndex;

	public FalseCopySlot(IItemHandler itemHandler, int index, int x, int y) {
		super(itemHandler, index, x, y);
		slotIndex = index;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return false;
	}

	@Override
	public void putStack(ItemStack stack) {
		if(!stack.isEmpty() && !isItemValid(stack)) {
			return;
		}
		super.putStack(stack);
	}
}
