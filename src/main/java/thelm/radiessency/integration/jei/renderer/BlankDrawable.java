package thelm.radiessency.integration.jei.renderer;

import net.minecraft.client.Minecraft;

public class BlankDrawable implements IMaskableDrawable {

	private final int width;
	private final int height;

	public BlankDrawable(int width, int height) {
		this.width = width;
		this.height = height;
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
	public void draw(Minecraft minecraft, float xOffset, float yOffset, float maskTop, float maskBottom, float maskLeft, float maskRight) {}

	@Override
	public IMaskableDrawable trim(int trimTop, int trimBottom, int trimLeft, int trimRight) {
		int newWidth = Math.max(width - trimLeft - trimRight, 0);
		int newHeight = Math.max(height - trimTop - trimBottom, 0);
		return new BlankDrawable(newWidth, newHeight);
	}
}
