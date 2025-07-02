package thelm.radiessency.api.transfer;

import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.primitives.Ints;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public final class DirectionalGlobalPos implements Comparable<DirectionalGlobalPos> {

	private final int dimension;
	private final BlockPos blockPos;
	private final EnumFacing direction;

	public DirectionalGlobalPos(int dimension, BlockPos blockPos, EnumFacing direction) {
		this.dimension = dimension;
		this.blockPos = blockPos;
		this.direction = direction;
	}

	public DirectionalGlobalPos(int dimension, int x, int y, int z, EnumFacing direction) {
		this(dimension, new BlockPos(x, y, z), direction);
	}

	public DirectionalGlobalPos(NBTTagCompound nbt) {
		this(nbt.getInteger("Dimension"), NBTUtil.getPosFromTag(nbt), EnumFacing.byIndex(nbt.getByte("Direction")));
	}

	public int dimension() {
		return dimension;
	}

	public BlockPos blockPos() {
		return blockPos;
	}

	public EnumFacing direction() {
		return direction;
	}

	public int x() {
		return blockPos().getX();
	}

	public int y() {
		return blockPos().getY();
	}

	public int z() {
		return blockPos().getZ();
	}

	public GlobalVec toGlobalVec() {
		return new GlobalVec(this);
	}

	public NBTTagCompound serialize() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("Dimension", dimension());
		nbt.merge(NBTUtil.createPosTag(blockPos));
		nbt.setByte("Direction", (byte)direction().getIndex());
		return nbt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(blockPos, dimension, direction);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DirectionalGlobalPos) {
			DirectionalGlobalPos other = (DirectionalGlobalPos)obj;
			return dimension == other.dimension && blockPos.equals(other.blockPos) && direction == other.direction;
		}
		return false;
	}

	@Override
	public int compareTo(DirectionalGlobalPos o) {
		int comp = Integer.compare(dimension, o.dimension);
		if(comp != 0) {
			return comp;
		}
		comp = blockPos.compareTo(o.blockPos);
		if(comp != 0) {
			return comp;
		}
		return direction.compareTo(o.direction);
	}

	public static Predicate<DirectionalGlobalPos> inRange(int dimension, BlockPos pos, int range) {
		return globalPos->{
			if(globalPos == null) {
				return false;
			}
			if(globalPos.dimension() != dimension) {
				return false;
			}
			Vec3i distVec = pos.subtract(globalPos.blockPos());
			return Ints.max(Math.abs(distVec.getX()), Math.abs(distVec.getY()), Math.abs(distVec.getZ())) <= range;
		};
	}

	public static Predicate<DirectionalGlobalPos> inDimension(int... dimensions) {
		return globalPos->{
			if(globalPos == null) {
				return false;
			}
			return ArrayUtils.contains(dimensions, globalPos.dimension());
		};
	}
}
