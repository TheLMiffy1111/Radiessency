package thelm.radiessency.api.radiessence;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;
import thelm.radiessency.api.transfer.GlobalVec;
import thelm.radiessency.api.transfer.IHasCenterPoint;

public class TileRadiessenceStorage<T extends TileEntity> extends RadiessenceStorage implements IHasCenterPoint {

	public static final Vec3d DEFAULT_BEAM_OFFSET = new Vec3d(0.5, 0.5, 0.5);

	public final T tile;
	public final Vec3d beamOffset;

	public TileRadiessenceStorage(T tile, int capacity) {
		this(tile, capacity, DEFAULT_BEAM_OFFSET);
	}

	public TileRadiessenceStorage(T tile, int capacity, Vec3d beamOffset) {
		super(capacity);
		this.tile = tile;
		this.beamOffset = beamOffset;
	}

	@Override
	public GlobalVec getCenterPoint() {
		return new GlobalVec(tile.getWorld().provider.getDimension(), tile.getPos(), beamOffset);
	}

	@Override
	public void onChanged() {
		if(tile != null) {
			tile.markDirty();
		}
	}
}
