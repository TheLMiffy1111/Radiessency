package thelm.radiessency.api.transfer;

import java.util.Set;

import net.minecraft.util.math.BlockPos;

public interface IRayNetworkHandler extends IHasCenterPoint {

	int getDimension();

	BlockPos getPos();

	int getRange();

	int getMaxConnections();

	Set<BlockPos> getConnections();

	boolean addConnection(BlockPos connection);

	boolean removeConnection(BlockPos connection);

	Set<IRayNetworkHandler> getLoadedConnections();

	Set<DirectionalGlobalPos> getInputs();

	boolean addInput(DirectionalGlobalPos input);

	boolean removeInput(DirectionalGlobalPos input);

	IRayNetwork getNetwork();

	void setNetwork(IRayNetwork network);
}
