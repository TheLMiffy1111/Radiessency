package thelm.radiessency.event;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thelm.radiessency.api.matrix.IMatrixHandler;
import thelm.radiessency.capability.RadiessencyCapabilities;
import thelm.radiessency.matrix.MatrixHandler;

public class WorldEventHandler {

	public static final WorldEventHandler INSTANCE = new WorldEventHandler();

	private WorldEventHandler() {}

	@SubscribeEvent
	public void onEntityAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
		if(event.getObject() instanceof EntityLivingBase && !(event.getObject() instanceof FakePlayer)) {
			event.addCapability(new ResourceLocation("radiessency:soul_matrix"), new MatrixHandler((EntityLivingBase)event.getObject()));
		}
	}

	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {
		IMatrixHandler originalHandler = event.getOriginal().getCapability(RadiessencyCapabilities.MATRIX, null);
		IMatrixHandler newHandler = event.getEntityLiving().getCapability(RadiessencyCapabilities.MATRIX, null);
		newHandler.deserializeNBT(originalHandler.serializeNBT());
	}

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {
		EntityLivingBase targetEntity = event.getEntityLiving();
		DamageSource source = event.getSource();
		int added = 0;
		if(targetEntity.hasCapability(RadiessencyCapabilities.MATRIX, null)) {
			IMatrixHandler targetMatrix = targetEntity.getCapability(RadiessencyCapabilities.MATRIX, null);
			added = targetMatrix.getRadiessence()/2;
			targetMatrix.setRadiessence(0);
		}
		if(source != null && source.getTrueSource() != null) {
			Entity sourceEntity = source.getTrueSource();
			if(sourceEntity.hasCapability(RadiessencyCapabilities.MATRIX, null)) {
				IMatrixHandler sourceMatrix = sourceEntity.getCapability(RadiessencyCapabilities.MATRIX, null);
				float maxHP = targetEntity.getMaxHealth();
				Random rand = targetEntity.getEntityWorld().rand;
				added += Math.round((20+10*(rand.nextFloat()-rand.nextFloat()))*maxHP);
				sourceMatrix.addRadiessence(added);
			}
		}
	}
}
