package thelm.radiessency.api.recipe;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import thelm.radiessency.api.RadiessencyApi;

public class IrradiationChamberRecipe implements IIrradiationChamberRecipe {

	protected final String key;
	protected List<Ingredient> ingredients;
	protected ItemStack output;
	protected float lowerBalance;
	protected float upperBalance;
	protected int radiessence;

	IrradiationChamberRecipe(String key, List<Ingredient> ingredients, ItemStack output, float lowerBalance, float upperBalance, int radiessence) {
		this.key = key;
		this.ingredients = ImmutableList.copyOf(ingredients);
		this.output = output;
		this.lowerBalance = lowerBalance;
		this.upperBalance = upperBalance;
		this.radiessence = radiessence;
	}

	public IrradiationChamberRecipe(String key, Ingredient ingredient, ItemStack output, float lowerBalance, float upperBalance, int radiessence) {
		this(key, ImmutableList.of(ingredient), output, lowerBalance, upperBalance, radiessence);
	}

	public IrradiationChamberRecipe(String key, Ingredient ingredient1, Ingredient ingredient2, ItemStack output, float lowerBalance, float upperBalance, int radiessence) {
		this(key, ImmutableList.of(ingredient1, ingredient2), output, lowerBalance, upperBalance, radiessence);
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public boolean matches(List<ItemStack> inputs, float balance) {
		if(balance < lowerBalance || balance > upperBalance) {
			return false;
		}
		return RadiessencyApi.instance().miscHelper().findMatches(ingredients, inputs) != null;
	}

	@Override
	public ItemStack getOutput(List<ItemStack> inputs, float balance) {
		return getOutput();
	}

	@Override
	public ItemStack getOutput() {
		return output.copy();
	}

	@Override
	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	@Override
	public float getLowerBalance() {
		return lowerBalance;
	}

	@Override
	public float getUpperBalance() {
		return upperBalance;
	}

	@Override
	public int getRadiessence() {
		return radiessence;
	}
}
