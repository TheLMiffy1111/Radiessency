package thelm.radiessency.api.recipe;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public interface MagicianTableRecipe {

	String getKey();

	boolean matches(List<ItemStack> inputs, float balance);

	ItemStack getOutput(List<ItemStack> inputs, float balance);

	ItemStack getOutput();

	List<Ingredient> getIngredients();

	float getLowerBalance();

	float getUpperBalance();

	int getRadiessence();
}
