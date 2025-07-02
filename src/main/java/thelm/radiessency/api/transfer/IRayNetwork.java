package thelm.radiessency.api.transfer;

import java.util.Map;
import java.util.Set;

import net.minecraft.util.math.BlockPos;

public interface IRayNetwork {

	Map<BlockPos, IRayNetworkHandler> getTowers();

	Map<DirectionalGlobalPos, IRayNetworkHandler> getInputs();

	void inputsChanged();
}
