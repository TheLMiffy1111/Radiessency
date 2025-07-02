package thelm.radiessency.api.radiessence;

import thelm.radiessency.api.transfer.GlobalVec;
import thelm.radiessency.api.transfer.IHasCenterPoint;

public class RadiessenceStorageBeamOriginWrapper<S extends IModifiableRadiessenceStorage & IHasCenterPoint> extends RadiessenceStorageWrapper<S> implements IHasCenterPoint {

	public RadiessenceStorageBeamOriginWrapper(S storage) {
		super(storage);
	}

	public RadiessenceStorageBeamOriginWrapper(S storage, boolean disableInsert, boolean disableExtract) {
		super(storage, disableInsert, disableExtract);
	}

	@Override
	public GlobalVec getCenterPoint() {
		return storage.getCenterPoint();
	}
}
