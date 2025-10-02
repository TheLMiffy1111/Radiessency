package thelm.radiessency.api;

import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.IItemHandler;
import thelm.radiessency.api.enderpulse.IEnderpulseStorage;
import thelm.radiessency.api.matrix.IMatrixHandler;
import thelm.radiessency.api.radiessence.IRadiessenceStorage;
import thelm.radiessency.api.radiessence.IRadiessenceTransferHandler;
import thelm.radiessency.api.transfer.DirectionalGlobalPos;
import thelm.radiessency.api.transfer.GlobalVec;
import thelm.radiessency.api.transfer.IRayNetworkHandler;

public abstract class RadiessencyApi {

	protected static RadiessencyApi instance;

	public static RadiessencyApi instance() {
		return instance;
	}

	public abstract IMiscHelper miscHelper();

	public abstract Capability<IRadiessenceStorage> radiessenceCapability();

	public abstract Capability<IEnderpulseStorage> enderpulseCapability();

	public abstract Capability<IMatrixHandler> matrixCapability();

	public abstract Capability<IRayNetworkHandler> rayNetworkCapability();

	public abstract DirectionalGlobalPos getDirectionalGlobalPos(ItemStack stack);

	public abstract TileEntity getBoundTile(World world, ItemStack stack, Predicate<DirectionalGlobalPos> predicate);

	public abstract IRadiessenceTransferHandler getRadiessenceTransferHandler(World world, Supplier<ItemStack> item, Predicate<DirectionalGlobalPos> predicate, Set<BlockPos> rayTowerExcluded, GlobalVec beamTarget);

	public abstract IRadiessenceTransferHandler getRadiessenceTransferHandler(World world, IItemHandler inventory, int slot, Predicate<DirectionalGlobalPos> predicate, Set<BlockPos> rayTowerExcluded, GlobalVec beamTarget);

	public abstract int radiessenceColor(float balance);

	public abstract int enderpulseColor();

	public abstract void addBeam(int dimension, Vec3d source, Vec3d delta, float width, int color, int lifetime);

	public abstract void addBeam(GlobalVec source, GlobalVec dest, float width, int color, int lifetime);
}
