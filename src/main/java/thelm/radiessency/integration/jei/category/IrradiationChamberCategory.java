package thelm.radiessency.integration.jei.category;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.translation.I18n;
import thelm.radiessency.Radiessency;
import thelm.radiessency.client.gui.GuiHelper;
import thelm.radiessency.client.gui.IrradiationChamberGui;
import thelm.radiessency.integration.jei.recipe.IrradiationChamberRecipeWrapper;
import thelm.radiessency.integration.jei.renderer.BlankDrawable;

public class IrradiationChamberCategory implements IRecipeCategory<IrradiationChamberRecipeWrapper> {

	public static final String UID = "radiessency:irradiation_chamber";

	public static final IDrawable BACKGROUND = new BlankDrawable(72, 36);

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return I18n.translateToLocal("tile.radiessency.irradiation_chamber.name");
	}

	@Override
	public String getModName() {
		return Radiessency.MOD_ID;
	}

	@Override
	public IDrawable getBackground() {
		return BACKGROUND;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IrradiationChamberRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		itemStacks.init(0, true, 18, 0);
		itemStacks.init(1, true, 18, 18);
		itemStacks.init(2, false, 54, 9);
		itemStacks.set(ingredients);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		minecraft.getTextureManager().bindTexture(IrradiationChamberGui.BACKGROUND);
		GuiHelper.blit(0, 0, 7, 16, 18, 36);
		GuiHelper.blit(18, 0, 61, 25, 54, 36);
	}
}
