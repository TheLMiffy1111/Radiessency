package thelm.radiessency.integration.jei.renderer;

import mezz.jei.api.gui.IDrawableStatic;
import net.minecraft.client.Minecraft;

public interface IMaskableDrawable extends IDrawableStatic {

	@Override
	default void draw(Minecraft minecraft, int xOffset, int yOffset) {
		draw(minecraft, xOffset, yOffset, 0, 0, 0, 0);
	}

	default void draw(Minecraft minecraft, float xOffset, float yOffset) {
		draw(minecraft, xOffset, yOffset, 0, 0, 0, 0);
	}

	@Override
	default void draw(Minecraft minecraft, int xOffset, int yOffset, int maskTop, int maskBottom, int maskLeft, int maskRight) {
		draw(minecraft, (float)xOffset, (float)yOffset, maskTop, maskBottom, maskLeft, maskRight);
	}

	void draw(Minecraft minecraft, float xOffset, float yOffset, float maskTop, float maskBottom, float maskLeft, float maskRight);

	IMaskableDrawable trim(int trimTop, int trimBottom, int trimLeft, int trimRight);
}
