package thelm.radiessency.network.packet;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.radiessency.api.matrix.IMatrixHandler;
import thelm.radiessency.client.matrix.ClientMatrixHandlerCache;

public class MatrixInfoSyncPacket implements ISelfHandleMessage {

	private UUID ownerUUID;
	private int radiessence;
	private int corruption;

	public MatrixInfoSyncPacket() {}

	public MatrixInfoSyncPacket(UUID ownerUUID, int radiessence, int corruption) {
		this.ownerUUID = ownerUUID;
		this.radiessence = radiessence;
		this.corruption = corruption;
	}

	public MatrixInfoSyncPacket(IMatrixHandler matrixHandler) {
		this.ownerUUID = matrixHandler.getOwner().getUniqueID();
		this.radiessence = matrixHandler.getRadiessence();
		this.corruption = matrixHandler.getCorruption();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(ownerUUID.getMostSignificantBits());
		buf.writeLong(ownerUUID.getLeastSignificantBits());
		buf.writeInt(radiessence);
		buf.writeInt(corruption);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		ownerUUID = new UUID(buf.readLong(), buf.readLong());
		radiessence = buf.readInt();
		corruption = buf.readInt();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage handle(MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(()->ClientMatrixHandlerCache.updateMatrix(ownerUUID, radiessence, corruption));
		return null;
	}
}
