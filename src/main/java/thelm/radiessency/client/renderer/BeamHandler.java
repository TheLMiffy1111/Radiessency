package thelm.radiessency.client.renderer;

import java.util.LinkedList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thelm.radiessency.client.ClientTimer;

public class BeamHandler {

	public static final BeamHandler INSTANCE = new BeamHandler();

	private BeamHandler() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	private List<BeamInfo> beams = new LinkedList<>();

	public void addBeam(Vec3d source, Vec3d delta, float width, float r, float g, float b, float a, int lifetime) {
		beams.add(new BeamInfo(source, delta, width, r, g, b, a, lifetime));
	}

	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event) {
		if(event.getWorld() == Minecraft.getMinecraft().world) {
			beams.clear();
		}
	}

	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		int currentTick = ClientTimer.INSTANCE.getRenderTicks();
		beams.removeIf(beam->beam.shouldRemove(currentTick));

		float renderTick = currentTick+event.getPartialTicks();
		double viewerPosX = Minecraft.getMinecraft().getRenderManager().viewerPosX;
		double viewerPosY = Minecraft.getMinecraft().getRenderManager().viewerPosY;
		double viewerPosZ = Minecraft.getMinecraft().getRenderManager().viewerPosZ;

		GlStateManager.pushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GlStateManager.disableCull();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();

		for(BeamInfo beam : beams) {
			Vec3d source = beam.source;
			GlStateManager.pushMatrix();
			GlStateManager.translate(source.x-viewerPosX, source.y-viewerPosY, source.z-viewerPosZ);
			RenderHelper.INSTANCE.drawLine(beam.delta, beam.width, beam.r, beam.g, beam.b, beam.getAlpha(renderTick));
			GlStateManager.popMatrix();
		}

		GlStateManager.enableCull();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GL11.glPopAttrib();
		GlStateManager.popMatrix();
	}

	public static class BeamInfo {

		private Vec3d source;
		private Vec3d delta;
		private float width;
		private float r;
		private float g;
		private float b;
		private float a;
		private int lifetime;
		private int startTick;

		public BeamInfo(Vec3d source, Vec3d delta, float width, float r, float g, float b, float a, int lifetime) {
			this.source = source;
			this.delta = delta;
			this.width = width;
			this.r = r;
			this.g = g;
			this.b = b;
			this.a = a;
			this.lifetime = lifetime;
			startTick = ClientTimer.INSTANCE.getRenderTicks();
		}

		public boolean shouldRemove(int currentTick) {
			if(currentTick < startTick) {
				currentTick += 0x1FFFFF;
			}
			return currentTick-startTick >= lifetime;
		}

		public float getAlpha(float renderTick) {
			float diff = renderTick-startTick;
			if(diff < 0) {
				diff += 0x1FFFFF;
			}
			float factor = diff/lifetime;
			return a*(1-factor*factor);
		}
	}
}
