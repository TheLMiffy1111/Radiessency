package thelm.radiessency.api.item;

import java.util.UUID;

import net.minecraft.item.ItemStack;

public interface ISoulStoneItem {

	UUID getOwnerUUID(ItemStack stack);
}
