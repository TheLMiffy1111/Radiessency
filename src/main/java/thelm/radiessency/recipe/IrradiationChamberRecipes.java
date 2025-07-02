package thelm.radiessency.recipe;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import it.unimi.dsi.fastutil.objects.Object2ObjectRBTreeMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import thelm.radiessency.api.recipe.IIrradiationChamberRecipe;
import thelm.radiessency.api.recipe.IrradiationChamberRecipe;

public class IrradiationChamberRecipes {

	public static final Map<String, IIrradiationChamberRecipe> RECIPES = new Object2ObjectRBTreeMap<>();

	public static void registerRecipe(IIrradiationChamberRecipe recipe) {
		RECIPES.put(recipe.getKey(), recipe);
	}

	public static void registerRecipe(String key, Ingredient ingredient, ItemStack output, float lowerBalance, float upperBalance, int radiessence) {
		registerRecipe(new IrradiationChamberRecipe(key, ingredient, output, lowerBalance, upperBalance, radiessence));
	}

	public static void registerRecipe(String key, Ingredient ingredient1, Ingredient ingredient2, ItemStack output, float lowerBalance, float upperBalance, int radiessence) {
		registerRecipe(new IrradiationChamberRecipe(key, ingredient1, ingredient2, output, lowerBalance, upperBalance, radiessence));
	}

	public static IIrradiationChamberRecipe findRecipe(List<ItemStack> inputs, float balance) {
		return RECIPES.values().stream().
				sorted(Comparator.comparingInt(r->-r.getIngredients().size())).
				filter(r->r.matches(inputs, balance)).
				findFirst().orElse(null);
	}

	public static IIrradiationChamberRecipe getRecipe(String key) {
		return RECIPES.get(key);
	}
}
