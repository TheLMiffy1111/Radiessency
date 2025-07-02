package thelm.radiessency.tile;

import net.minecraft.util.math.Vec3i;

public class DoubleDelegateTile extends DelegateTile {

	public static final Vec3i OFFSET = new Vec3i(0, -1, 0);

	@Override
	public Vec3i getOffset() {
		return OFFSET;
	}
}
