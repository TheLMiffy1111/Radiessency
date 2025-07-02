package thelm.radiessency.network;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IGuiProvider {

	Container getServerGuiElement(EntityPlayer player, int id);

	@SideOnly(Side.CLIENT)
	GuiContainer getClientGuiElement(EntityPlayer player, int id);
}
