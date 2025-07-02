package thelm.radiessency.api.item;

import net.minecraft.item.ItemStack;
import thelm.radiessency.api.transfer.DirectionalGlobalPos;

public interface IMarkerItem {

	DirectionalGlobalPos getDirectionalGlobalPos(ItemStack stack);

	void setDirectionalGlobalPos(ItemStack stack, DirectionalGlobalPos globalPos);
}
