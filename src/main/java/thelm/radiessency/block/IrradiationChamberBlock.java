package thelm.radiessency.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thelm.radiessency.item.RadiessencyCreativeTabs;
import thelm.radiessency.tile.IrradiationChamberTile;

public class IrradiationChamberBlock extends PartialBlock implements ITileEntityProvider {

	public IrradiationChamberBlock() {
		super("radiessency:irradiation_chamber", Material.IRON, MapColor.GOLD, SoundType.METAL);
		setHardness(10F);
		setResistance(25F);
		setCreativeTab(RadiessencyCreativeTabs.MACHINES);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new IrradiationChamberTile();
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof IrradiationChamberTile) {
			((IrradiationChamberTile)tile).updatePowered();
		}
	}
}
