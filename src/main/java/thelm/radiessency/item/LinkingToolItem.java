package thelm.radiessency.item;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.radiessency.api.item.ILinkingToolItem;
import thelm.radiessency.api.tile.ILinkableTile;
import thelm.radiessency.api.transfer.DirectionalGlobalPos;
import thelm.radiessency.tile.DelegateTile;

public class LinkingToolItem extends MarkerItem implements ILinkingToolItem {

	public static final ModelResourceLocation MODEL_LOCATION = new ModelResourceLocation("radiessency:linking_tool#inventory");
	public static final ModelResourceLocation MODEL_LOCATION_BOUND = new ModelResourceLocation("radiessency:linking_tool_bound#inventory");

	public LinkingToolItem() {
		super("radiessency:linking_tool");
		setMaxStackSize(1);
		setCreativeTab(RadiessencyCreativeTabs.TOOLS);
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		if(!world.isRemote && !player.isSneaking()) {
			ItemStack stack = player.getHeldItem(hand);
			if(!isBound(stack)) {
				return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
			}
			DirectionalGlobalPos tilePos = getDirectionalGlobalPos(stack);
			if(tilePos.dimension() == world.provider.getDimension()) {
				TileEntity tile = world.getTileEntity(tilePos.blockPos());
				if(tile instanceof DelegateTile) {
					tile = ((DelegateTile)tile).getActualTile();
				}
				if(tile instanceof ILinkableTile) {
					DirectionalGlobalPos globalPos = new DirectionalGlobalPos(tilePos.dimension(), pos, side);
					ILinkableTile linkableTile = (ILinkableTile)tile;
					if(linkableTile.link(tilePos.direction(), globalPos, player)) {
						return EnumActionResult.SUCCESS;
					}
				}
			}
		}
		return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelLoader.setCustomMeshDefinition(this, stack->isBound(stack) ? MODEL_LOCATION_BOUND : MODEL_LOCATION);
		ModelBakery.registerItemVariants(this, MODEL_LOCATION, MODEL_LOCATION_BOUND);
	}
}
