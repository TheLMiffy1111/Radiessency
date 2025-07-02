package thelm.radiessency.tile;

import java.util.Set;

import com.google.common.primitives.Ints;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.radiessency.api.tile.ILinkableTile;
import thelm.radiessency.api.transfer.DirectionalGlobalPos;
import thelm.radiessency.capability.RadiessencyCapabilities;
import thelm.radiessency.transfer.RayTowerNetworkHandler;

public class RayTowerTile extends BaseTile implements ILinkableTile {

	public static int range = 8;
	public static int maxConnections = 4;
	public static int maxInputs = 4;

	public final RayTowerNetworkHandler rayTowerHandler = new RayTowerNetworkHandler(this, range, maxConnections);

	@Override
	protected String getDefaultName() {
		return I18n.translateToLocal("tile.radiessency.ray_tower.name");
	}

	@Override
	public boolean link(EnumFacing selfDirection, DirectionalGlobalPos globalPos, EntityPlayer player) {
		if(globalPos.dimension() != world.provider.getDimension()) {
			return false;
		}
		BlockPos tilePos = globalPos.blockPos();
		TileEntity tile = world.getTileEntity(tilePos);
		if(tile instanceof DelegateTile) {
			tile = ((DelegateTile)tile).getActualTile();
			tilePos = tile.getPos();
		}
		if(tile != null && tile.hasCapability(RadiessencyCapabilities.RAY_NETWORK, null)) {
			Set<BlockPos> connections = rayTowerHandler.getConnections();
			if(!connections.contains(tilePos)) {
				Vec3i distVec = pos.subtract(tilePos);
				if(connections.size() >= rayTowerHandler.getMaxConnections()) {
					player.sendMessage(new TextComponentTranslation("tile.radiessency.ray_tower.add_connection.too_many"));
					return false;
				}
				else if(rayTowerHandler.getRange() < Ints.max(Math.abs(distVec.getX()), Math.abs(distVec.getY()), Math.abs(distVec.getZ()))) {
					player.sendMessage(new TextComponentTranslation("tile.radiessency.ray_tower.add_connection.outside_range"));
					return false;
				}
				else if(rayTowerHandler.addConnection(tilePos)) {
					player.sendMessage(new TextComponentTranslation("tile.radiessency.ray_tower.add_connection.success"));
					return true;
				}
				else {
					player.sendMessage(new TextComponentTranslation("tile.radiessency.ray_tower.add_connection.fail"));
					return false;
				}
			}
			else {
				rayTowerHandler.removeConnection(tilePos);
				player.sendMessage(new TextComponentTranslation("tile.radiessency.ray_tower.remove_connection"));
				return true;
			}
		}
		Set<DirectionalGlobalPos> inputs = rayTowerHandler.getInputs();
		if(!inputs.contains(globalPos)) {
			Vec3i distVec = pos.subtract(globalPos.blockPos());
			if(inputs.size() >= maxInputs) {
				player.sendMessage(new TextComponentTranslation("tile.radiessency.ray_tower.add_input.too_many"));
				return false;
			}
			else if(rayTowerHandler.getRange() < Ints.max(Math.abs(distVec.getX()), Math.abs(distVec.getY()), Math.abs(distVec.getZ()))) {
				player.sendMessage(new TextComponentTranslation("tile.radiessency.ray_tower.add_input.outside_range"));
				return false;
			}
			else if(rayTowerHandler.addInput(globalPos)) {
				player.sendMessage(new TextComponentTranslation("tile.radiessency.ray_tower.add_input.success"));
				syncTile(false);
				return true;
			}
			else {
				player.sendMessage(new TextComponentTranslation("tile.radiessency.ray_tower.add_input.fail"));
				return false;
			}
		}
		else if(rayTowerHandler.removeInput(globalPos)) {
			player.sendMessage(new TextComponentTranslation("tile.radiessency.ray_tower.remove_input"));
			syncTile(false);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == RadiessencyCapabilities.RAY_NETWORK) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == RadiessencyCapabilities.RAY_NETWORK) {
			return (T)rayTowerHandler;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void readSyncNBT(NBTTagCompound nbt) {
		super.readSyncNBT(nbt);
		rayTowerHandler.deserializeNBT(nbt.getCompoundTag("Tower"));
	}

	@Override
	public NBTTagCompound writeSyncNBT(NBTTagCompound nbt) {
		super.writeSyncNBT(nbt);
		nbt.setTag("Tower", rayTowerHandler.serializeNBT());
		return nbt;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}
}
