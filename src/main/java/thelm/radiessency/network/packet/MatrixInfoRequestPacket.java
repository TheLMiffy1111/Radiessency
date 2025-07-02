package thelm.radiessency.network.packet;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thelm.radiessency.api.matrix.IMatrixHandler;
import thelm.radiessency.capability.RadiessencyCapabilities;
import thelm.radiessency.network.PacketHandler;

public class MatrixInfoRequestPacket implements ISelfHandleMessage {

	private UUID ownerUUID;

	public MatrixInfoRequestPacket() {}

	public MatrixInfoRequestPacket(UUID ownerUUID) {
		this.ownerUUID = ownerUUID;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(ownerUUID.getMostSignificantBits());
		buf.writeLong(ownerUUID.getLeastSignificantBits());
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		ownerUUID = new UUID(buf.readLong(), buf.readLong());
	}

	@Override
	public IMessage handle(MessageContext ctx) {
		EntityPlayerMP player = ctx.getServerHandler().player;
		WorldServer world = player.getServerWorld();
		world.addScheduledTask(()->{
			Entity owner = world.getMinecraftServer().getEntityFromUuid(ownerUUID);
			if(owner != null && owner.hasCapability(RadiessencyCapabilities.MATRIX, null)) {
				IMatrixHandler matrixHandler = owner.getCapability(RadiessencyCapabilities.MATRIX, null);
				PacketHandler.INSTANCE.sendTo(new MatrixInfoSyncPacket(matrixHandler), player);
			}
		});
		return null;
	}
}
