package thelm.radiessency.client.matrix;

import java.util.UUID;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import thelm.radiessency.client.ClientTimer;
import thelm.radiessency.network.PacketHandler;
import thelm.radiessency.network.packet.MatrixInfoRequestPacket;

public class ClientMatrixHandlerCache {

	private static final LoadingCache<UUID, Integer> TICK_CACHE = CacheBuilder.newBuilder().
			build(CacheLoader.from(ClientTimer.INSTANCE::getTotalTicks));
	private static final LoadingCache<UUID, ClientMatrixHandler> MATRIX_CACHE = CacheBuilder.newBuilder().
			maximumSize(10).
			removalListener(notification->TICK_CACHE.invalidate(notification.getKey())).
			build(CacheLoader.from(ClientMatrixHandler::new));

	public static synchronized ClientMatrixHandler getMatrix(UUID ownerUUID) {
		Integer lastTick = TICK_CACHE.getIfPresent(ownerUUID);
		if(lastTick == null || ClientTimer.INSTANCE.getTotalTicks()-lastTick >= 20) {
			PacketHandler.INSTANCE.sendToServer(new MatrixInfoRequestPacket(ownerUUID));
			TICK_CACHE.refresh(ownerUUID);
		}
		return MATRIX_CACHE.getIfPresent(ownerUUID);
	}

	public static synchronized void updateMatrix(UUID ownerUUID, int radiessence, int corruption) {
		ClientMatrixHandler matrixHandler = MATRIX_CACHE.getUnchecked(ownerUUID);
		matrixHandler.setRadiessence(radiessence);
		matrixHandler.setCorruption(corruption);
		TICK_CACHE.refresh(ownerUUID);
	}
}
