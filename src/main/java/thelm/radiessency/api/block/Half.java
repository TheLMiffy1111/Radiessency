package thelm.radiessency.api.block;

import java.util.Locale;

import net.minecraft.util.IStringSerializable;

public enum Half implements IStringSerializable {
	BOTTOM, TOP;

	public final String name = name().toLowerCase(Locale.US);

	@Override
	public String getName() {
		return name;
	}

	public int getIndex() {
		return this == BOTTOM ? 0 : 1;
	}

	public static Half byIndex(int index) {
		return (index & 1) == 0 ? BOTTOM : TOP;
	}
}
