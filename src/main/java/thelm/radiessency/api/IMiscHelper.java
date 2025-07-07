package thelm.radiessency.api;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

import net.minecraft.item.ItemStack;

public interface IMiscHelper {

	ItemStack cloneStack(ItemStack stack, int stackSize);

	/**
	 * A version of {@link net.minecraftforge.common.util.RecipeMatcher#findMatches}
	 * that can accept more inputs than tests, which matches tests to inputs.
	 * Returns null if no satisfiable match can be found.
	 * @param <T>
	 * @param tests List of tests
	 * @param inputs List of inputs
	 * @return An array mapping tests to inputs, where {@code ret[x] == y} means {@code tests[x].test(inputs[y])}.
	 */
	<T> int[] findMatches(List<? extends Predicate<? super T>> tests, List<T> inputs);

	int[] distributeTanks(int[] capacities, int target);

	Runnable conditionalRunnable(BooleanSupplier conditionSupplier, Supplier<Runnable> trueRunnable, Supplier<Runnable> falseRunnable);

	<T> Supplier<T> conditionalSupplier(BooleanSupplier conditionSupplier, Supplier<Supplier<T>> trueSupplier, Supplier<Supplier<T>> falseSupplier);
}
