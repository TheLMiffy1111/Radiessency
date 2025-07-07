package thelm.radiessency.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class CubeModel extends ModelBase {

	public final ModelRenderer cube;

	public CubeModel(int textureWidth, int textureHeight, int xOffset, int yOffset) {
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		cube = new ModelRenderer(this, xOffset, yOffset);
		cube.addBox(-8, -8, -8, 16, 16, 16);
	}

	public void render() {
		cube.render(0.0625F);
	}
}
