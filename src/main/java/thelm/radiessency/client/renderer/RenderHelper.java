package thelm.radiessency.client.renderer;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.primitives.Doubles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import thelm.radiessency.api.transfer.DirectionalGlobalPos;

public class RenderHelper {

	public static final RenderHelper INSTANCE = new RenderHelper();

	private RenderHelper() {}

	public void drawMarker(BlockPos blockPos, float range, Vec3d size, float width, float r, float g, float b, float a) {
		if(blockPos != null) {
			drawMarkers(Collections.singletonList(blockPos), range, size, width, r, g, b, a);
		}
	}

	public void drawMarkers(Collection<BlockPos> positions, float range, Vec3d size, float width, float r, float g, float b, float a) {
		Minecraft mc = Minecraft.getMinecraft();
		double viewerPosX = mc.getRenderManager().viewerPosX;
		double viewerPosY = mc.getRenderManager().viewerPosY;
		double viewerPosZ = mc.getRenderManager().viewerPosZ;

		GlStateManager.pushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GlStateManager.disableDepth();
		GlStateManager.disableCull();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.translate(-viewerPosX, -viewerPosY, -viewerPosZ);

		for(BlockPos blockPos : positions) {
			if(blockPos == null) {
				continue;
			}
			double distX = viewerPosX-blockPos.getX()-0.5;
			double distY = viewerPosY-blockPos.getY()-0.5;
			double distZ = viewerPosZ-blockPos.getZ()-0.5;
			if(Doubles.max(Math.abs(distX), Math.abs(distY), Math.abs(distZ)) > range) {
				continue;
			}

			GlStateManager.pushMatrix();
			GlStateManager.translate(blockPos.getX(), blockPos.getY(), blockPos.getZ());
			drawMarker(size, width, null, r, g, b, a);
			GlStateManager.popMatrix();
		}

		GlStateManager.enableDepth();
		GlStateManager.enableCull();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GL11.glPopAttrib();
		GlStateManager.popMatrix();
	}

	public void drawDirectionalMarker(DirectionalGlobalPos globalPos, float range, Vec3d size, float width, float r, float g, float b, float a) {
		if(globalPos != null) {
			drawDirectionalMarkers(Collections.singletonList(globalPos), range, size, width, r, g, b, a);
		}
	}

	public void drawDirectionalMarkers(Collection<DirectionalGlobalPos> positions, float range, Vec3d size, float width, float r, float g, float b, float a) {
		Minecraft mc = Minecraft.getMinecraft();
		double viewerPosX = mc.getRenderManager().viewerPosX;
		double viewerPosY = mc.getRenderManager().viewerPosY;
		double viewerPosZ = mc.getRenderManager().viewerPosZ;

		GlStateManager.pushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GlStateManager.disableDepth();
		GlStateManager.disableCull();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.translate(-viewerPosX, -viewerPosY, -viewerPosZ);

		for(DirectionalGlobalPos globalPos : positions) {
			if(globalPos == null || globalPos.dimension() != mc.world.provider.getDimension()) {
				continue;
			}
			BlockPos blockPos = globalPos.blockPos();
			double distX = viewerPosX-blockPos.getX()-0.5;
			double distY = viewerPosY-blockPos.getY()-0.5;
			double distZ = viewerPosZ-blockPos.getZ()-0.5;
			if(Doubles.max(Math.abs(distX), Math.abs(distY), Math.abs(distZ)) > range) {
				continue;
			}

			GlStateManager.pushMatrix();
			GlStateManager.translate(blockPos.getX(), blockPos.getY(), blockPos.getZ());
			drawMarker(size, width, globalPos.direction(), r, g, b, a/2);
			drawMarker(size, width, null, r, g, b, a);
			GlStateManager.popMatrix();
		}

		GlStateManager.enableDepth();
		GlStateManager.enableCull();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GL11.glPopAttrib();
		GlStateManager.popMatrix();
	}

	public void drawMarker(Vec3d delta, float width, EnumFacing direction, float r, float g, float b, float a) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		GL11.glLineWidth(width);
		float x = (float)delta.x;
		float y = (float)delta.y;
		float z = (float)delta.z;
		buffer.begin(direction == null ? GL11.GL_LINES : GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		if(direction == null || direction == EnumFacing.NORTH) {
			// Face North, Edge Bottom
			buffer.pos(0, 0, 0).color(r, g, b, a).endVertex();
			buffer.pos(x, 0, 0).color(r, g, b, a).endVertex();
			// Face North, Edge Top
			buffer.pos(x, y, 0).color(r, g, b, a).endVertex();
			buffer.pos(0, y, 0).color(r, g, b, a).endVertex();
		}
		if(direction == null || direction == EnumFacing.SOUTH) {
			// Face South, Edge Bottom
			buffer.pos(x, 0, z).color(r, g, b, a).endVertex();
			buffer.pos(0, 0, z).color(r, g, b, a).endVertex();
			// Face South, Edge Top
			buffer.pos(0, y, z).color(r, g, b, a).endVertex();
			buffer.pos(x, y, z).color(r, g, b, a).endVertex();
		}
		if(direction == null || direction == EnumFacing.WEST) {
			// Face West, Edge Bottom
			buffer.pos(0, 0, 0).color(r, g, b, a).endVertex();
			buffer.pos(0, 0, z).color(r, g, b, a).endVertex();
			// Face West, Edge Top
			buffer.pos(0, y, z).color(r, g, b, a).endVertex();
			buffer.pos(0, y, 0).color(r, g, b, a).endVertex();
		}
		if(direction == null || direction == EnumFacing.EAST) {
			// Face East, Edge Bottom
			buffer.pos(x, 0, z).color(r, g, b, a).endVertex();
			buffer.pos(x, 0, 0).color(r, g, b, a).endVertex();
			// Face East, Edge Top
			buffer.pos(x, y, 0).color(r, g, b, a).endVertex();
			buffer.pos(x, y, z).color(r, g, b, a).endVertex();
		}
		if(direction == EnumFacing.DOWN) {
			// Face Down
			buffer.pos(0, 0, 0).color(r, g, b, a).endVertex();
			buffer.pos(x, 0, 0).color(r, g, b, a).endVertex();
			buffer.pos(x, 0, z).color(r, g, b, a).endVertex();
			buffer.pos(0, 0, z).color(r, g, b, a).endVertex();
		}
		if(direction == EnumFacing.UP) {
			// Face Up
			buffer.pos(0, y, 0).color(r, g, b, a).endVertex();
			buffer.pos(x, y, 0).color(r, g, b, a).endVertex();
			buffer.pos(x, y, z).color(r, g, b, a).endVertex();
			buffer.pos(0, y, z).color(r, g, b, a).endVertex();
		}
		if(direction == null) {
			// Face North, Edge West
			buffer.pos(0, 0, 0).color(r, g, b, a).endVertex();
			buffer.pos(0, y, 0).color(r, g, b, a).endVertex();
			// Face North, Edge East
			buffer.pos(x, y, 0).color(r, g, b, a).endVertex();
			buffer.pos(x, 0, 0).color(r, g, b, a).endVertex();
			// Face South, Edge East
			buffer.pos(x, 0, z).color(r, g, b, a).endVertex();
			buffer.pos(x, y, z).color(r, g, b, a).endVertex();
			// Face South, Edge West
			buffer.pos(0, y, z).color(r, g, b, a).endVertex();
			buffer.pos(0, 0, z).color(r, g, b, a).endVertex();
		}
		tessellator.draw();
	}

	public void drawLine(Vec3d delta, float width, float r, float g, float b, float a) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		GL11.glLineWidth(width);
		float x = (float)delta.x;
		float y = (float)delta.y;
		float z = (float)delta.z;
		buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
		buffer.pos(0, 0, 0).color(r, g, b, a).endVertex();
		buffer.pos(x, y, z).color(r, g, b, a).endVertex();
		tessellator.draw();
	}
}
