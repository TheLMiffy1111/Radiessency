package thelm.radiessency.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import thelm.radiessency.Radiessency;
import thelm.radiessency.network.packet.ISelfHandleMessage;
import thelm.radiessency.network.packet.AddBeamPacket;
import thelm.radiessency.network.packet.MatrixInfoRequestPacket;
import thelm.radiessency.network.packet.MatrixInfoSyncPacket;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Radiessency.MOD_ID);

	public static void registerPackets() {
		int id = 0;
		IMessageHandler<ISelfHandleMessage, IMessage> handler = ISelfHandleMessage::handle;
		INSTANCE.registerMessage(handler, MatrixInfoRequestPacket.class, id++, Side.SERVER);
		INSTANCE.registerMessage(handler, MatrixInfoSyncPacket.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(handler, AddBeamPacket.class, id++, Side.CLIENT);
	}
}
