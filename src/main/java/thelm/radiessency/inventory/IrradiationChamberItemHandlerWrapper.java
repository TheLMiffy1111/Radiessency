package thelm.radiessency.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class IrradiationChamberItemHandlerWrapper extends SidedItemHandlerWrapper<IrradiationChamberItemHandler> {

	public static final int[] SLOTS = {0, 1, 2};

	public IrradiationChamberItemHandlerWrapper(IrradiationChamberItemHandler itemHandler, EnumFacing direction) {
		super(itemHandler, direction);
	}

	@Override
	public int[] getSlotsForDirection(EnumFacing direction) {
		return SLOTS;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing direction) {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, EnumFacing direction) {
		return false;
	}
}
