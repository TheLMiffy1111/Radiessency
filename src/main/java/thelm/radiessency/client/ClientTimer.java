package thelm.radiessency.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientTimer {

	public static final ClientTimer INSTANCE = new ClientTimer();

	private Minecraft mc;
	private int ticks;

	private ClientTimer() {
		mc = Minecraft.getMinecraft();
		MinecraftForge.EVENT_BUS.register(this);
	}

	public int getRenderTicks() {
		return ticks & 0x1FFFFF;
	}

	public int getTotalTicks() {
		return ticks;
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if(event.phase == TickEvent.Phase.START || mc.world == null || mc.player == null || mc.isGamePaused()) {
			return;
		}
		ticks++;
	}
}
