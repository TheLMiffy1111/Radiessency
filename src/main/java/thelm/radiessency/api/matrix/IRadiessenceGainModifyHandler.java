package thelm.radiessency.api.matrix;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IRadiessenceGainModifyHandler {

	public float getModifiedValue(float original, ItemStack stack, EntityPlayer player);
}
