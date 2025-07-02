package thelm.radiessency.client.renderer.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import thelm.radiessency.client.ClientTimer;
import thelm.radiessency.tile.MatrixExtractorTile;

public class MatrixExtractorRenderer extends TileEntitySpecialRenderer<MatrixExtractorTile> {

	@Override
	public void render(MatrixExtractorTile tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		ItemStack stack = tile.itemHandler.getStackInSlot(0);
		if(!stack.isEmpty()) {
			float renderTicks = ClientTimer.INSTANCE.getRenderTicks()+partialTicks;
			GlStateManager.pushMatrix();
			GlStateManager.translate(x+0.5, y+0.625, z+0.5);
			GlStateManager.rotate(renderTicks, 0, 1, 0);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
			GlStateManager.popMatrix();
		}
	}
}
