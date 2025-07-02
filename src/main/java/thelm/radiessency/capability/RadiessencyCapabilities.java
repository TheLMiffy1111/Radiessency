package thelm.radiessency.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import thelm.radiessency.api.enderpulse.IEnderpulseStorage;
import thelm.radiessency.api.matrix.IMatrixHandler;
import thelm.radiessency.api.radiessence.IRadiessenceStorage;
import thelm.radiessency.api.transfer.IRayNetworkHandler;

public class RadiessencyCapabilities {

	@CapabilityInject(IRadiessenceStorage.class)
	public static Capability<IRadiessenceStorage> RADIESSENCE;
	@CapabilityInject(IEnderpulseStorage.class)
	public static Capability<IEnderpulseStorage> ENDERPULSE;
	@CapabilityInject(IMatrixHandler.class)
	public static Capability<IMatrixHandler> MATRIX;
	@CapabilityInject(IRayNetworkHandler.class)
	public static Capability<IRayNetworkHandler> RAY_NETWORK;

	public static void registerCapabilities() {
		CapabilityManager.INSTANCE.register(IRadiessenceStorage.class, new EmptyStorage<>(), ()->null);
		CapabilityManager.INSTANCE.register(IEnderpulseStorage.class, new EmptyStorage<>(), ()->null);
		CapabilityManager.INSTANCE.register(IMatrixHandler.class, new EmptyStorage<>(), ()->null);
		CapabilityManager.INSTANCE.register(IRayNetworkHandler.class, new EmptyStorage<>(), ()->null);
	}

	private static class EmptyStorage<T> implements Capability.IStorage<T> {

		@Override
		public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
			return null;
		}

		@Override
		public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) {

		}
	}
}
