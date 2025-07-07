package thelm.radiessency.client.renderer.tile;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import thelm.radiessency.api.transfer.DirectionalGlobalPos;
import thelm.radiessency.api.transfer.GlobalVec;
import thelm.radiessency.api.transfer.IRayNetworkHandler;
import thelm.radiessency.client.ClientTimer;
import thelm.radiessency.client.model.CubeModel;
import thelm.radiessency.client.renderer.RenderHelper;
import thelm.radiessency.tile.RayTowerTile;

public class RayTowerRenderer extends TileEntitySpecialRenderer<RayTowerTile> {

	private static final CubeModel GLASS = new CubeModel(64, 64, 0, 0);
	private static final CubeModel CUBE = new CubeModel(64, 64, 0, 32);
	private static final ResourceLocation TEXTURE = new ResourceLocation("radiessency:textures/block/ray_tower_crystal.png");

	@Override
	public void render(RayTowerTile tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);

		// Cube
		GlStateManager.pushMatrix();
		float renderTicks = ClientTimer.INSTANCE.getRenderTicks()+partialTicks;
		float rotation = renderTicks*3 % 360;
		bindTexture(TEXTURE);
		GlStateManager.translate(0.5, 1.75, 0.5);
		GlStateManager.scale(0.25, 0.25, 0.25);
		GlStateManager.rotate(rotation, 0, 1, 0);
		GlStateManager.rotate(54.7356F, 1, 0, 1);
		GLASS.render();
		GlStateManager.scale(0.75, 0.75, 0.75);
		GlStateManager.rotate(54.7356F, 1, 0, 1);
		GlStateManager.rotate(rotation, 0, 1, 0);
		GlStateManager.rotate(54.7356F, 1, 0, 1);
		GlStateManager.rotate(rotation, 0, 1, 0);
		CUBE.render();
		GlStateManager.popMatrix();

		// Beams
		GlStateManager.pushMatrix();
		Minecraft.getMinecraft().entityRenderer.disableLightmap();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GlStateManager.disableCull();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		BlockPos pos = tile.getPos();
		GlobalVec source = tile.rayTowerHandler.getCenterPoint();
		GlStateManager.translate(source.x()-pos.getX(), source.y()-pos.getY(), source.z()-pos.getZ());

		for(IRayNetworkHandler connection : tile.rayTowerHandler.getLoadedConnections()) {
			GlobalVec target = connection.getCenterPoint();
			Vec3d delta = target.vec().subtract(source.vec());
			if(shouldRenderConnection(delta)) {
				RenderHelper.INSTANCE.drawLine(delta, 3, 1F, 1F, 1F, 0.7F);
			}
		}

		for(DirectionalGlobalPos input : tile.rayTowerHandler.getInputs()) {
			GlobalVec target = input.toGlobalVec();
			Vec3d delta = target.vec().subtract(source.vec());
			RenderHelper.INSTANCE.drawLine(delta, 3, 1F, 1F, 1F, 0.7F);
		}

		GlStateManager.enableCull();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GL11.glPopAttrib();
		Minecraft.getMinecraft().entityRenderer.enableLightmap();
		GlStateManager.popMatrix();

		GlStateManager.popMatrix();
	}

	public boolean shouldRenderConnection(Vec3d delta) {
		return delta.x > 0 || delta.x == 0 && (delta.y > 0 || delta.y == 0 && delta.z > 0);
	}
}
