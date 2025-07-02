package thelm.radiessency.api.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import thelm.radiessency.api.transfer.DirectionalGlobalPos;

public interface ILinkableTile {

	boolean link(EnumFacing selfDirection, DirectionalGlobalPos globalPos, EntityPlayer player);
}
