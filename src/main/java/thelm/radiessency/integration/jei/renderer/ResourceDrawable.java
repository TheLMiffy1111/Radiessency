package thelm.radiessency.integration.jei.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import thelm.radiessency.client.gui.GuiHelper;

public class ResourceDrawable implements IMaskableDrawable {

	private final ResourceLocation atlasLocation;
	private final int u;
	private final int v;
	private final int width;
	private final int height;
	private final int textureWidth;
	private final int textureHeight;

	public ResourceDrawable(ResourceLocation atlasLocation, int u, int v, int width, int height, int textureWidth, int textureHeight) {
		this.atlasLocation = atlasLocation;
		this.u = u;
		this.v = v;
		this.width = width;
		this.height = height;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
	}

	public ResourceDrawable(ResourceLocation atlasLocation, int u, int v, int width, int height) {
		this(atlasLocation, u, v, width, height, 256, 256);
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void draw(Minecraft minecraft, float xOffset, float yOffset, float maskTop, float maskBottom, float maskLeft, float maskRight) {
		if(maskLeft + maskRight < width && maskTop + maskBottom < height) {
			minecraft.getTextureManager().bindTexture(atlasLocation);
			GuiHelper.blit(xOffset + maskLeft, yOffset + maskTop, u + maskLeft, v + maskTop, width - maskLeft - maskRight, height - maskTop - maskBottom, textureHeight, textureHeight);
		}
	}

	@Override
	public IMaskableDrawable trim(int trimTop, int trimBottom, int trimLeft, int trimRight) {
		int newWidth = Math.max(width - trimLeft - trimRight, 0);
		int newHeight = Math.max(height - trimTop - trimBottom, 0);
		if(newWidth == 0 || newHeight == 0) {
			return new BlankDrawable(newWidth, newHeight);
		}
		return new ResourceDrawable(atlasLocation, u + trimLeft, v + trimTop, newWidth, newHeight, textureWidth, textureHeight);
	}
}
