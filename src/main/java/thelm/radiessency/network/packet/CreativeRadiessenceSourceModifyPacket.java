package thelm.radiessency.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thelm.radiessency.container.CreativeRadiessenceSourceContainer;

public class CreativeRadiessenceSourceModifyPacket implements ISelfHandleMessage {

	private float balance;
	private int amount;

	public CreativeRadiessenceSourceModifyPacket() {}

	public CreativeRadiessenceSourceModifyPacket(float balance, int amount) {
		this.balance = balance;
		this.amount = amount;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(balance);
		buf.writeInt(amount);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		balance = buf.readFloat();
		amount = buf.readInt();
	}

	@Override
	public IMessage handle(MessageContext ctx) {
		EntityPlayerMP player = ctx.getServerHandler().player;
		WorldServer world = player.getServerWorld();
		world.addScheduledTask(()->{
			if(player.openContainer instanceof CreativeRadiessenceSourceContainer) {
				CreativeRadiessenceSourceContainer container = (CreativeRadiessenceSourceContainer)player.openContainer;
				container.tile.setStored(balance, amount);
			}
		});
		return null;
	}
}
