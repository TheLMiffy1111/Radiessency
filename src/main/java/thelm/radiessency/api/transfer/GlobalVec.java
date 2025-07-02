package thelm.radiessency.api.transfer;

import java.util.Objects;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public final class GlobalVec implements Comparable<GlobalVec> {

	private int dimension;
	private Vec3d vec;

	public GlobalVec(int dimension, Vec3d vec) {
		this.dimension = dimension;
		this.vec = vec;
	}

	public GlobalVec(int dimension, double x, double y, double z) {
		this(dimension, new Vec3d(x, y, z));
	}

	public GlobalVec(int dimension, BlockPos pos) {
		this(dimension, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5);
	}

	public GlobalVec(int dimension, BlockPos pos, Vec3d offset) {
		this(dimension, pos.getX()+offset.x, pos.getY()+offset.y, pos.getZ()+offset.z);
	}

	public GlobalVec(int dimension, BlockPos pos, EnumFacing offset) {
		this(dimension, pos.getX()+(1+offset.getXOffset())*0.5, pos.getY()+(1+offset.getYOffset())*0.5, pos.getZ()+(1+offset.getZOffset())*0.5);
	}

	public GlobalVec(DirectionalGlobalPos globalPos) {
		this(globalPos.dimension(), globalPos.x()+(1+globalPos.direction().getXOffset())*0.5, globalPos.y()+(1+globalPos.direction().getYOffset())*0.5, globalPos.z()+(1+globalPos.direction().getZOffset())*0.5);
	}

	public int dimension() {
		return dimension;
	}

	public Vec3d vec() {
		return vec;
	}

	public double x() {
		return vec.x;
	}

	public double y() {
		return vec.y;
	}

	public double z() {
		return vec.z;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dimension, vec);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof GlobalVec) {
			GlobalVec other = (GlobalVec)obj;
			return dimension == other.dimension && Objects.equals(vec, other.vec);
		}
		return false;
	}

	@Override
	public int compareTo(GlobalVec o) {
		int comp = Integer.compare(dimension, o.dimension);
		if(comp != 0) {
			return comp;
		}
		comp = Double.compare(x(), o.x());
		if(comp != 0) {
			return comp;
		}
		comp = Double.compare(y(), o.y());
		if(comp != 0) {
			return comp;
		}
		return Double.compare(z(), o.z());
	}
}
