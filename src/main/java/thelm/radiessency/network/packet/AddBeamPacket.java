package thelm.radiessency.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.radiessency.client.renderer.BeamHandler;
import thelm.radiessency.network.PacketHandler;

public class AddBeamPacket implements ISelfHandleMessage {

	private Vec3d source;
	private Vec3d delta;
	private float width;
	private int color;
	private int lifetime;

	public AddBeamPacket() {}

	public AddBeamPacket(Vec3d source, Vec3d delta, float width, int color, int lifetime) {
		this.source = source;
		this.delta = delta;
		this.width = width;
		this.color = color;
		this.lifetime = lifetime;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(source.x);
		buf.writeDouble(source.y);
		buf.writeDouble(source.z);
		buf.writeDouble(delta.x);
		buf.writeDouble(delta.y);
		buf.writeDouble(delta.z);
		buf.writeFloat(width);
		buf.writeInt(color);
		buf.writeInt(lifetime);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		source = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
		delta = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
		width = buf.readFloat();
		color = buf.readInt();
		lifetime = buf.readInt();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage handle(MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(()->{
			float r = (color >>> 16 & 0xFF)/255F;
			float g = (color >>> 8 & 0xFF)/255F;
			float b = (color & 0xFF)/255F;
			float a = (color >>> 24 & 0xFF)/255F;
			BeamHandler.INSTANCE.addBeam(source, delta, width, r, g, b, a, lifetime);	
		});
		return null;
	}

	public static void sendBeam(Vec3d source, int dimension, double range, Vec3d delta, float width, int color, int lifetime) {
		PacketHandler.INSTANCE.sendToAllAround(new AddBeamPacket(source, delta, width, color, lifetime), new TargetPoint(dimension, source.x, source.y, source.z, range));
	}
}
