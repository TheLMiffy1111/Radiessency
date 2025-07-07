package thelm.radiessency.util;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import it.unimi.dsi.fastutil.ints.IntArrays;
import net.minecraft.item.ItemStack;
import thelm.radiessency.api.IMiscHelper;

public class MiscHelper implements IMiscHelper {

	public static final MiscHelper INSTANCE = new MiscHelper();

	private MiscHelper() {}

	@Override
	public ItemStack cloneStack(ItemStack stack, int stackSize) {
		if(stack.isEmpty()) {
			return ItemStack.EMPTY;
		}
		ItemStack retStack = stack.copy();
		retStack.setCount(stackSize);
		return retStack;
	}

	@Override
	public <T> int[] findMatches(List<? extends Predicate<? super T>> tests, List<T> inputs) {
		if(tests.size() > inputs.size()) {
			return null;
		}
		if(tests.isEmpty()) {
			return IntArrays.EMPTY_ARRAY;
		}
		int[] ret = new int[tests.size()];
		Arrays.fill(ret, -1);
		BitSet claimedTests = new BitSet(tests.size());
		BitSet usedInputs = new BitSet(inputs.size());
		BitSet[] matches = new BitSet[tests.size()];
		IntArrayFIFOQueue testQueue = new IntArrayFIFOQueue();
		IntArrayFIFOQueue inputQueue = new IntArrayFIFOQueue();
		for(int t = 0; t < tests.size(); ++t) {
			matches[t] = new BitSet(inputs.size());
			Predicate<? super T> test = tests.get(t);
			for(int i = 0; i < inputs.size(); ++i) {
				if(usedInputs.get(i)) {
					continue;
				}
				if(test.test(inputs.get(i))) {
					matches[t].set(i);
				}
			}
			int count = matches[t].cardinality();
			if(count == 0) {
				return null;
			}
			if(count == 1) {
				testQueue.enqueue(t);
				while(!testQueue.isEmpty()) {
					int t1 = testQueue.dequeueInt();
					int i = matches[t1].nextSetBit(0);
					claimedTests.set(t1);
					usedInputs.set(i);
					ret[t1] = i;
					for(int t2 = 0; t2 < t; ++t2) {
						if(!claimedTests.get(t2) && matches[t2].get(i)) {
							matches[t2].clear(i);
							count = matches[t2].cardinality();
							if(count == 0) {
								return null;
							}
							if(count == 1) {
								testQueue.enqueue(t2);
							}
						}
					}
				}
			}
		}
		if(claimedTests.cardinality() == tests.size()) {
			return ret;
		}
		// consider replacing this part with Hopcroft-Karp
		int t = claimedTests.nextClearBit(0);
		int i = matches[t].nextSetBit(0);
		o:while(t < tests.size()) {
			while(i >= 0) {
				if(!usedInputs.get(i)) {
					usedInputs.set(i);
					ret[t] = i;
					testQueue.enqueue(t);
					inputQueue.enqueue(i);
					t = claimedTests.nextClearBit(t+1);
					if(t >= tests.size()) {
						break o;
					}
					i = matches[t].nextSetBit(0);
					continue o;
				}
				i = matches[t].nextSetBit(i+1);
			}
			if(testQueue.isEmpty()) {
				return null;
			}
			t = testQueue.dequeueLastInt();
			i = inputQueue.dequeueLastInt();
			usedInputs.clear(i);
			i = matches[t].nextSetBit(i+1);
		}
		return ret;
	}

	@Override
	public int[] distributeTanks(int[] capacities, int target) {
		if(capacities.length == 0) {
			return new int[0];
		}
		if(capacities.length == 1) {
			return new int[] {Math.min(capacities[0], target)};
		}
		int[] amounts = new int[capacities.length];
		int remaining = target;
		int remainingTanks = capacities.length;
		while(remaining > 0) {
			int per = Math.max(remaining / remainingTanks, 1);
			if(per > 0) {
				for(int i = 0; i < capacities.length; ++i) {
					if(amounts[i] == capacities[i]) {
						continue;
					}
					int tankRemaining = capacities[i]-amounts[i];
					if(tankRemaining <= per) {
						amounts[i] = capacities[i];
						remaining -= tankRemaining;
						remainingTanks--;
					}
					else {
						amounts[i] += per;
						remaining -= per;
					}
					if(remaining == 0) {
						break;
					}
				}
			}
		}
		return amounts;
	}

	@Override
	public Runnable conditionalRunnable(BooleanSupplier conditionSupplier, Supplier<Runnable> trueRunnable, Supplier<Runnable> falseRunnable) {
		return ()->(conditionSupplier.getAsBoolean() ? trueRunnable : falseRunnable).get().run();
	}

	@Override
	public <T> Supplier<T> conditionalSupplier(BooleanSupplier conditionSupplier, Supplier<Supplier<T>> trueSupplier, Supplier<Supplier<T>> falseSupplier) {
		return ()->(conditionSupplier.getAsBoolean() ? trueSupplier : falseSupplier).get().get();
	}
}
