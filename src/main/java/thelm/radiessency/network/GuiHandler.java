package thelm.radiessency.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final GuiHandler INSTANCE = new GuiHandler();

	private GuiHandler() {}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);
		if(tile == null) {
			return null;
		}
		else if(tile instanceof IGuiProvider) {
			return ((IGuiProvider)tile).getClientGuiElement(player, id);
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);
		if(tile == null) {
			return null;
		}
		else if(tile instanceof IGuiProvider) {
			return ((IGuiProvider)tile).getServerGuiElement(player, id);
		}
		return null;
	}

}
