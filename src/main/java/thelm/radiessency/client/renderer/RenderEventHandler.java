package thelm.radiessency.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thelm.radiessency.api.item.IBindingGemItem;
import thelm.radiessency.api.item.ILinkingToolItem;
import thelm.radiessency.api.transfer.DirectionalGlobalPos;
import thelm.radiessency.tile.DelegateTile;
import thelm.radiessency.tile.RayTowerTile;

public class RenderEventHandler {

	public static final RenderEventHandler INSTANCE = new RenderEventHandler();

	private RenderEventHandler() {}

	public static final Vec3d BLOCK_SIZE = new Vec3d(1, 1, 1);

	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		// Held Items
		EntityPlayer player = Minecraft.getMinecraft().player;
		World world = Minecraft.getMinecraft().world;
		for(EnumHand hand : EnumHand.values()) {
			ItemStack stack = player.getHeldItem(hand);
			// Binding Gem
			if(stack.getItem() instanceof IBindingGemItem) {
				DirectionalGlobalPos globalPos = ((IBindingGemItem)stack.getItem()).getDirectionalGlobalPos(stack);
				if(globalPos != null && globalPos.dimension() == world.provider.getDimension()) {
					RenderHelper.INSTANCE.drawDirectionalMarker(globalPos, 24, BLOCK_SIZE, 4, 0.90F, 0.76F, 0.59F, 1F);
				}
			}
			// Linking Tool
			if(stack.getItem() instanceof ILinkingToolItem) {
				DirectionalGlobalPos globalPos = ((ILinkingToolItem)stack.getItem()).getDirectionalGlobalPos(stack);
				if(globalPos != null && globalPos.dimension() == world.provider.getDimension()) {
					RenderHelper.INSTANCE.drawDirectionalMarker(globalPos, 24, BLOCK_SIZE, 4, 0.90F, 0.76F, 0.59F, 1F);
					// Block being configured
					TileEntity tile = world.getTileEntity(globalPos.blockPos());
					if(tile instanceof DelegateTile) {
						tile = ((DelegateTile)tile).getActualTile();
					}
					// Ray Tower
					if(tile instanceof RayTowerTile) {
						RayTowerTile rayTower = (RayTowerTile)tile;
						RenderHelper.INSTANCE.drawMarkers(rayTower.rayTowerHandler.getConnections(), 24, BLOCK_SIZE, 4, 0.8F, 0.8F, 0.8F, 1F);
						RenderHelper.INSTANCE.drawDirectionalMarkers(rayTower.rayTowerHandler.getInputs(), 24, BLOCK_SIZE, 4, 0F, 1F, 1F, 1F);
					}
				}
			}
		}
	}
}
