package thelm.radiessency.client.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import com.google.common.primitives.Floats;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import thelm.radiessency.api.radiessence.IRadiessenceStorage;

public class GuiHelper {

	public static void blit(float x, float y, float uOffset, float vOffset, float width, float height) {
		blit(x, y, uOffset, vOffset, width, height, 256, 256);
	}

	public static void blit(float x, float y, float uOffset, float vOffset, float width, float height, int textureWidth, int textureHeight) {
		if(width == 0 || height == 0 || textureWidth == 0 || textureHeight == 0) {
			return;
		}
		float uMin = uOffset / textureWidth;
		float uMax = (uOffset + width) / textureWidth;
		float vMin = vOffset / textureHeight;
		float vMax = (vOffset + height) / textureHeight;
		blit(x, x + width, y, y + height, uMin, uMax, vMin, vMax);
	}

	public static void blitSprite(TextureAtlasSprite sprite, float x, float y, float uOffset, float vOffset, float width, float height, int textureWidth, int textureHeight) {
		if(width == 0 || height == 0 || textureWidth == 0 || textureHeight == 0) {
			return;
		}
		float spriteWidth = sprite.getMaxU() - sprite.getMinU();
		float spriteHeight = sprite.getMaxV() - sprite.getMinV();
		float uMin = sprite.getMinU() + uOffset / textureWidth * spriteWidth;
		float uMax = sprite.getMinU() + (uOffset + width) / textureWidth * spriteWidth;
		float vMin = sprite.getMinV() + vOffset / textureHeight * spriteHeight;
		float vMax = sprite.getMinV() + (vOffset + height) / textureHeight * spriteHeight;
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		blit(x, x + width, y, y + height, uMin, uMax, vMin, vMax);
	}

	// Modified from Mekanism
	public static void blitTiledSprite(TextureAtlasSprite sprite, float x, float y, float width, float height, float targetWidth, float targetHeight, int textureWidth, int textureHeight, boolean fromRight, boolean fromDown) {
		if(width == 0 || height == 0 || targetWidth == 0 || targetHeight == 0 || textureWidth == 0 || textureHeight == 0) {
			return;
		}
		if(fromRight) {
			x = x + width - Math.min(width, targetWidth);
		}
		if(fromDown) {
			y = y + height - Math.min(height, targetHeight);
		}
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		Tessellator tessellator = Tessellator.getInstance();	
		BufferBuilder buffer = tessellator.getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		int xTileCount = (int)(targetWidth / textureWidth);
		float xRemainder = targetWidth % textureWidth;
		int yTileCount = (int)(targetHeight / textureHeight);
		float yRemainder = targetHeight % textureHeight;
		float uMin = sprite.getMinU();
		float uMax = sprite.getMaxU();
		float vMin = sprite.getMinV();
		float vMax = sprite.getMaxV();
		float uDif = uMax - uMin;
		float vDif = vMax - vMin;
		for(int xTile = 0; xTile <= xTileCount; ++xTile) {
			float tileWidth = xTile == xTileCount ? xRemainder : textureWidth;
			if(tileWidth == 0) {
				break;
			}
			float uMask = uDif*(textureWidth-tileWidth)/textureWidth;
			float xMinTile, xMaxTile;
			float uMinTile, uMaxTile;
			if(fromRight) {
				xMaxTile = x+targetWidth-xTile*textureWidth;
				xMinTile = xMaxTile-tileWidth;
				uMaxTile = uMax;
				uMinTile = uMin+uMask;
			}
			else {
				xMinTile = x+xTile*textureWidth;
				xMaxTile = xMinTile+tileWidth;
				uMinTile = uMin;
				uMaxTile = uMax-uMask;
			}
			for(int yTile = 0; yTile <= yTileCount; ++yTile) {
				float tileHeight = yTile == yTileCount ? yRemainder : textureHeight;
				if(tileHeight == 0) {
					break;
				}
				float vMask = vDif*(textureHeight-tileHeight)/textureHeight;
				float yMinTile, yMaxTile;
				float vMinTile, vMaxTile;
				if(fromDown) {
					yMaxTile = y+targetHeight-yTile*textureHeight;
					yMinTile = yMaxTile-tileHeight;
					vMaxTile = vMax;
					vMinTile = vMin+vMask;
				}
				else {
					yMinTile = y+yTile*textureHeight;
					yMaxTile = yMinTile+tileHeight;
					vMinTile = vMin;
					vMaxTile = vMax-vMask;
				}
				buffer.pos(xMinTile, yMaxTile, 0).tex(uMinTile, vMaxTile).endVertex();
				buffer.pos(xMaxTile, yMaxTile, 0).tex(uMaxTile, vMaxTile).endVertex();
				buffer.pos(xMaxTile, yMinTile, 0).tex(uMaxTile, vMinTile).endVertex();
				buffer.pos(xMinTile, yMinTile, 0).tex(uMinTile, vMinTile).endVertex();
			}
		}
		tessellator.draw();
	}

	static void blit(float xMin, float xMax, float yMin, float yMax, float uMin, float uMax, float vMin, float vMax) {
		Tessellator tessellator = Tessellator.getInstance();	
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		bufferBuilder.pos(xMin, yMin, 0).tex(uMin, vMin).endVertex();
		bufferBuilder.pos(xMin, yMax, 0).tex(uMin, vMax).endVertex();
		bufferBuilder.pos(xMax, yMax, 0).tex(uMax, vMax).endVertex();
		bufferBuilder.pos(xMax, yMin, 0).tex(uMax, vMin).endVertex();
		tessellator.draw();
	}

	public static void drawRadiessence(IRadiessenceStorage storage, int x, int y, int width, int height) {
		drawRadiessence(storage.getAmount(), storage.getCapacity(), storage.getBalance(), x, y, width, height);
	}

	public static void drawRadiessence(int amount, int capacity, float balance, int x, int y, int width, int height) {
		float scaledAmount = getScaledAmount(amount, capacity, height);
		GlStateManager.color(1, 1, 1, 1);
		blitTiledSprite(RadiessencyIcons.radiessenceBalanced, x, y, width, height, width, scaledAmount, 16, 16, false, true);
		float overlayWeight = Floats.constrainToRange(Math.abs(1-balance), 0, 1);
		TextureAtlasSprite overlaySprite = balance < 1 ? RadiessencyIcons.radiessenceStasis : RadiessencyIcons.radiessenceChaos;
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.CONSTANT_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
		GL14.glBlendColor(1, 1, 1, overlayWeight);
		blitTiledSprite(overlaySprite, x, y, width, height, width, scaledAmount, 16, 16, false, true);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableBlend();
	}

	public static float getScaledAmount(int amount, int max, int scale) {
		int guiScale = getGuiScale();
		return Math.round((long)MathHelper.clamp(amount, 0, max) * guiScale * scale / (double)max) / (float)guiScale;
	}

	public static int getGuiScale() {
		return new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor();
	}
}
