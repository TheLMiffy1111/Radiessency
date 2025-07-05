package thelm.radiessency.integration.jei.recipe;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.translation.I18n;
import thelm.radiessency.api.recipe.IIrradiationChamberRecipe;
import thelm.radiessency.client.gui.GuiHelper;

public class IrradiationChamberRecipeWrapper implements IRecipeWrapper {

	public static final DecimalFormat BALANCE_FORMAT = new DecimalFormat("0.0");

	public final IIrradiationChamberRecipe recipe;

	public IrradiationChamberRecipeWrapper(IIrradiationChamberRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM,
				recipe.getIngredients().stream().
				map(Ingredient::getMatchingStacks).
				map(Arrays::asList).
				collect(Collectors.toList()));
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		GuiHelper.drawRadiessence(1, 1, (recipe.getLowerBalance()+recipe.getUpperBalance())/2, 3, 2, 12, 32);
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		if(mouseX >= 3 && mouseX < 15 && mouseY >= 2 && mouseY < 34) {
			return Arrays.asList(
					I18n.translateToLocalFormatted("misc.radiessency.radiessence", recipe.getRadiessence()),
					I18n.translateToLocalFormatted("misc.radiessency.balance_bounds", BALANCE_FORMAT.format(recipe.getLowerBalance()), BALANCE_FORMAT.format(recipe.getUpperBalance())));
		}
		return Collections.emptyList();
	}
}
