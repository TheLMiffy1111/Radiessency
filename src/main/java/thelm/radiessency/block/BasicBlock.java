package thelm.radiessency.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import thelm.radiessency.Radiessency;
import thelm.radiessency.client.model.IBlockModelRegister;
import thelm.radiessency.network.IGuiProvider;
import thelm.radiessency.tile.BaseTile;

public class BasicBlock extends Block implements IBlockModelRegister {

	public final BlockRenderLayer renderLayer;

	public BasicBlock(String registryName, Material material, SoundType soundType) {
		this(registryName, material, material.getMaterialMapColor(), soundType, BlockRenderLayer.SOLID);
	}

	public BasicBlock(String registryName, Material material, SoundType soundType, BlockRenderLayer renderLayer) {
		this(registryName, material, material.getMaterialMapColor(), soundType, renderLayer);
	}

	public BasicBlock(String registryName, Material material, MapColor mapColor, SoundType soundType) {
		this(registryName, material, mapColor, soundType, BlockRenderLayer.SOLID);
	}

	public BasicBlock(String registryName, Material material, MapColor mapColor, SoundType soundType, BlockRenderLayer renderLayer) {
		super(material, mapColor);
		setTranslationKey(registryName.replace(':', '.'));
		setRegistryName(registryName);
		setSoundType(soundType);
		this.renderLayer = renderLayer;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer() {
		return renderLayer;
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		super.getDrops(drops, world, pos, state, fortune);
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof IWorldNameable && ((IWorldNameable)tile).hasCustomName()) {
			for(ItemStack drop : drops) {
				drop.setStackDisplayName(((IWorldNameable)tile).getName());
			}
		}
	}

	@Override
	public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
		TileEntity tile = world.getTileEntity(pos);
		return tile == null ? false : tile.receiveClientEvent(id, param);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(player.isSneaking()) {
			return false;
		}
		if(world.getTileEntity(pos) instanceof IGuiProvider) {
			if(!world.isRemote) {
				player.openGui(Radiessency.MOD_ID, side.getIndex(), world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		}
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if(!world.isRemote) {
			TileEntity tile = world.getTileEntity(pos);
			if(tile instanceof BaseTile) {
				if(stack.hasDisplayName()) {
					((BaseTile)tile).setCustomName(stack.getDisplayName());
				}
				if(placer instanceof EntityPlayer) {
					((BaseTile)tile).setOwner((EntityPlayer)placer);
				}
			}
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)  {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof BaseTile) {
			IItemHandler itemHandler = ((BaseTile)tile).getItemHandler();
			if(itemHandler != null) {
				for(int i = 0; i < itemHandler.getSlots(); ++i) {
					ItemStack stack = itemHandler.getStackInSlot(i);
					if(!stack.isEmpty()) {
						InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
					}
				}
			}
			world.updateComparatorOutputLevel(pos, this);
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof BaseTile) {
			return ((BaseTile)tile).getComparatorSignal();
		}
		return 0;
	}
}
