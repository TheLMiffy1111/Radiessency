package thelm.radiessency.network.packet;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public interface ISelfHandleMessage extends IMessage {

	IMessage handle(MessageContext ctx);
}
