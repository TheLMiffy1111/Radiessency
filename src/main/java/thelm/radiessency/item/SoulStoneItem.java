package thelm.radiessency.item;

import java.util.List;
import java.util.UUID;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.radiessency.api.item.ISoulStoneItem;
import thelm.radiessency.client.matrix.ClientMatrixHandler;
import thelm.radiessency.client.matrix.ClientMatrixHandlerCache;

public class SoulStoneItem extends BasicItem implements ISoulStoneItem {

	public SoulStoneItem() {
		super("radiessency:soul_stone");
		setMaxStackSize(1);
		setCreativeTab(RadiessencyCreativeTabs.TOOLS);
	}

	@Override
	public UUID getOwnerUUID(ItemStack stack) {
		if(stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt.hasUniqueId("OwnerUUID")) {
				return nbt.getUniqueId("OwnerUUID");
			}
		}
		return null;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(!world.isRemote) {
			ItemStack stack = player.getHeldItem(hand);
			if(getOwnerUUID(stack) != null) {
				if(player.isSneaking()) {
					stack = stack.copy();
					NBTTagCompound nbt = stack.getTagCompound();
					nbt.removeTag("OwnerUUIDMost");
					nbt.removeTag("OwnerUUIDLeast");
					if(nbt.isEmpty()) {
						stack.setTagCompound(null);;
					}
					return new ActionResult<>(EnumActionResult.SUCCESS, stack);
				}
				return super.onItemRightClick(world, player, hand);
			}
			stack = stack.copy();
			if(!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}
			NBTTagCompound nbt = stack.getTagCompound();
			nbt.setUniqueId("OwnerUUID", player.getUniqueID());
			return new ActionResult<>(EnumActionResult.SUCCESS, stack);
		}
		return super.onItemRightClick(world, player, hand);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		UUID ownerUUID = getOwnerUUID(stack);
		if(ownerUUID != null) {
			String ownerName = UsernameCache.getMap().getOrDefault(ownerUUID, ownerUUID.toString());
			ClientMatrixHandler matrixHandler = ClientMatrixHandlerCache.getMatrix(ownerUUID);
			if(matrixHandler != null) {
				EntityLivingBase owner = matrixHandler.getOwner();
				if(owner != null) {
					ownerName = owner.getName();
				}
				tooltip.add(I18n.translateToLocalFormatted("item.radiessency.soul_stone.owner", ownerName));
				tooltip.add(I18n.translateToLocalFormatted("item.radiessency.soul_stone.radiessence", matrixHandler.getRadiessence()));
				tooltip.add(I18n.translateToLocalFormatted("item.radiessency.soul_stone.corruption", matrixHandler.getCorruption()));
			}
			else {
				tooltip.add(I18n.translateToLocalFormatted("item.radiessency.soul_stone.owner", ownerName));
				tooltip.add(I18n.translateToLocal("item.radiessency.soul_stone.absent"));
			}
		}
	}
}
