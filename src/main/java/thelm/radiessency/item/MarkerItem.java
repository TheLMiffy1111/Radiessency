package thelm.radiessency.item;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import thelm.radiessency.api.item.IMarkerItem;
import thelm.radiessency.api.transfer.DirectionalGlobalPos;

public abstract class MarkerItem extends BasicItem implements IMarkerItem {

	public MarkerItem(String registryName) {
		super(registryName);
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		if(!world.isRemote && !player.isSneaking()) {
			ItemStack stack = player.getHeldItem(hand);
			if(isBound(stack)) {
				return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
			}
			int dim = world.provider.getDimension();
			DirectionalGlobalPos globalPos = new DirectionalGlobalPos(dim, pos, side);
			if(stack.getCount() > 1) {
				ItemStack stack1 = stack.splitStack(1);
				setDirectionalGlobalPos(stack1, globalPos);
				if(!player.inventory.addItemStackToInventory(stack1)) {
					EntityItem item = new EntityItem(world, player.posX, player.posY, player.posZ, stack1);
					item.setThrower(player.getName());
					world.spawnEntity(item);
				}
			}
			else {
				setDirectionalGlobalPos(stack, globalPos);
			}
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(!worldIn.isRemote && playerIn.isSneaking() && isBound(playerIn.getHeldItem(handIn))) {
			ItemStack stack = playerIn.getHeldItem(handIn).copy();
			setDirectionalGlobalPos(stack, null);
			return new ActionResult<>(EnumActionResult.SUCCESS, stack);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		DirectionalGlobalPos pos = getDirectionalGlobalPos(stack);
		if(pos != null) {
			tooltip.add(I18n.translateToLocalFormatted("misc.radiessency.dimension", pos.dimension()));
			String posString = "["+pos.x()+", "+pos.y()+", "+pos.z()+"]";
			tooltip.add(I18n.translateToLocalFormatted("misc.radiessency.position", posString));
			String dirString = I18n.translateToLocal("misc.radiessency."+pos.direction().getName());
			tooltip.add(I18n.translateToLocalFormatted("misc.radiessency.direction", dirString));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public DirectionalGlobalPos getDirectionalGlobalPos(ItemStack stack) {
		if(stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt.hasKey("Position")) {
				return new DirectionalGlobalPos(nbt.getCompoundTag("Position"));
			}
		}
		return null;
	}

	@Override
	public void setDirectionalGlobalPos(ItemStack stack, DirectionalGlobalPos globalPos) {
		if(globalPos != null) {
			if(!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}
			stack.getTagCompound().setTag("Position", globalPos.serialize());
		}
		else if(stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			nbt.removeTag("Position");
			if(nbt.isEmpty()) {
				stack.setTagCompound(null);
			}
		}
	}

	public boolean isBound(ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
		return nbt != null && nbt.hasKey("Position");
	}
}
