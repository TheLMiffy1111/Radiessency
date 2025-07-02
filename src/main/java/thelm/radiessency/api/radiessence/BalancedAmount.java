package thelm.radiessency.api.radiessence;

import java.util.List;

import com.google.common.primitives.Floats;

public class BalancedAmount {

	public static final BalancedAmount ZERO = new BalancedAmount(1, 0);

	private final float balance;
	private final int amount;

	public BalancedAmount(float balance, int amount) {
		this.balance = Floats.constrainToRange(balance, 0, 2);
		this.amount = amount;
	}

	public float balance() {
		return balance;
	}

	public int amount() {
		return amount;
	}

	public static BalancedAmount sum(List<BalancedAmount> balancedAmounts) {
		int amountSum = balancedAmounts.stream().mapToInt(BalancedAmount::amount).sum();
		double balanceSum = balancedAmounts.stream().mapToDouble(a->a.balance*a.amount).sum();
		return new BalancedAmount((float)(balanceSum/amountSum), amountSum);
	}
}