package thelm.radiessency.transfer;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

import it.unimi.dsi.fastutil.objects.Object2ObjectRBTreeMap;
import net.minecraft.util.math.BlockPos;
import thelm.radiessency.api.transfer.IRayNetworkHandler;
import thelm.radiessency.api.transfer.DirectionalGlobalPos;
import thelm.radiessency.api.transfer.IRayNetwork;

public class RayNetwork implements IRayNetwork {

	protected Map<BlockPos, IRayNetworkHandler> towers = new Object2ObjectRBTreeMap<>();
	protected Map<DirectionalGlobalPos, IRayNetworkHandler> inputs = new Object2ObjectRBTreeMap<>();

	public void init(IRayNetworkHandler source) {
		towers.clear();
		inputs.clear();
		Deque<IRayNetworkHandler> toProcess = new LinkedList<>();
		towers.put(source.getPos(), source);
		toProcess.addAll(source.getLoadedConnections());
		while(!toProcess.isEmpty()) {
			IRayNetworkHandler tower = toProcess.removeFirst();
			towers.put(tower.getPos(), tower);
			for(IRayNetworkHandler adj : tower.getLoadedConnections()) {
				if(!towers.containsKey(adj.getPos())) {
					toProcess.add(adj);
				}
			}
		}
		for(IRayNetworkHandler tower : towers.values()) {
			tower.setNetwork(this);
			for(DirectionalGlobalPos input : tower.getInputs()) {
				inputs.putIfAbsent(input, tower);
			}
		}
	}

	@Override
	public Map<BlockPos, IRayNetworkHandler> getTowers() {
		return Collections.unmodifiableMap(towers);
	}

	@Override
	public Map<DirectionalGlobalPos, IRayNetworkHandler> getInputs() {
		return Collections.unmodifiableMap(inputs);
	}

	@Override
	public void inputsChanged() {
		inputs.clear();
		for(IRayNetworkHandler tower : towers.values()) {
			for(DirectionalGlobalPos input : tower.getInputs()) {
				inputs.putIfAbsent(input, tower);
			}
		}
	}
}
