package thelm.radiessency.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.registries.IForgeRegistry;
import thelm.radiessency.item.BasicBlockItem;
import thelm.radiessency.item.RadiessencyCreativeTabs;

public class RadiessencyBlocks {

	public static final List<Supplier<Block>> BLOCK_SUPPLIERS = new ArrayList<>();
	public static final List<Supplier<Item>> ITEM_BLOCK_SUPPLIERS = new ArrayList<>();
	public static final List<Block> BLOCKS = new ArrayList<>();
	public static final List<Item> ITEM_BLOCKS = new ArrayList<>();

	// Essence Gem Blocks
	public static final Supplier<Block> FIRE_ESSENCE_BLOCK = add(()->new BeaconBaseBlock("radiessency:fire_essence_block",
			MapColor.ADOBE).setHardness(3F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));
	public static final Supplier<Block> AIR_ESSENCE_BLOCK = add(()->new BeaconBaseBlock("radiessency:air_essence_block",
			MapColor.SILVER).setHardness(3F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));
	public static final Supplier<Block> WATER_ESSENCE_BLOCK = add(()->new BeaconBaseBlock("radiessency:water_essence_block",
			MapColor.BLUE).setHardness(3F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));
	public static final Supplier<Block> EARTH_ESSENCE_BLOCK = add(()->new BeaconBaseBlock("radiessency:earth_essence_block",
			MapColor.BROWN).setHardness(3F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));
	public static final Supplier<Block> BALANCED_ESSENCE_BLOCK = add(()->new BeaconBaseBlock("radiessency:balanced_essence_block",
			MapColor.PINK).setHardness(3F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));
	public static final Supplier<Block> STASIS_ESSENCE_BLOCK = add(()->new BeaconBaseBlock("radiessency:stasis_essence_block",
			MapColor.LIGHT_BLUE).setHardness(3F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));
	public static final Supplier<Block> CHAOS_ESSENCE_BLOCK = add(()->new BeaconBaseBlock("radiessency:chaos_essence_block",
			MapColor.RED).setHardness(3F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));

	// Ingot Blocks
	public static final Supplier<Block> RADISTEEL_BLOCK = add(()->new BeaconBaseBlock("radiessency:radisteel_block",
			MapColor.PURPLE).setHardness(3F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));
	public static final Supplier<Block> MAGNETIZED_RADISTEEL_BLOCK = add(()->new BeaconBaseBlock("radiessency:magnetized_radisteel_block",
			MapColor.PURPLE).setHardness(3F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));
	public static final Supplier<Block> RADIGOLD_BLOCK = add(()->new BeaconBaseBlock("radiessency:radigold_block",
			MapColor.GOLD).setHardness(3F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));
	public static final Supplier<Block> SPHUZITE_BLOCK = add(()->new BeaconBaseBlock("radiessency:sphuzite_block",
			MapColor.GREEN).setHardness(3F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));
	public static final Supplier<Block> ALLASSITE_BLOCK = add(()->new BeaconBaseBlock("radiessency:allassite_block",
			MapColor.BLUE).setHardness(3F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));
	public static final Supplier<Block> KENOTITE_BLOCK = add(()->new BeaconBaseBlock("radiessency:kenotite_block",
			MapColor.BLACK).setHardness(3F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));
	public static final Supplier<Block> TWINKLING_KENOTITE_BLOCK = add(()->new BeaconBaseBlock("radiessency:twinkling_kenotite_block",
			MapColor.SILVER).setHardness(3F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));
	public static final Supplier<Block> ACKRONITE_BLOCK = add(()->new BeaconBaseBlock("radiessency:ackronite_block",
			MapColor.RED).setHardness(3F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));

	// Dust Blocks
	public static final Supplier<Block> MITHRILINE_BLOCK = add(()->new BeaconBaseBlock("radiessency:mithriline_block",
			MapColor.GREEN).setHardness(3F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));

	// Panels
	public static final Supplier<Block> RADISTEEL_PANEL = add(()->new BasicBlock("radiessency:radisteel_panel",
			Material.IRON, MapColor.PURPLE, SoundType.METAL).
			setHardness(10F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));
	public static final Supplier<Block> SPHUZITE_PANEL = add(()->new BasicBlock("radiessency:sphuzite_panel",
			Material.IRON, MapColor.GREEN, SoundType.METAL).
			setHardness(10F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));
	public static final Supplier<Block> ALLASSITE_PANEL = add(()->new BasicBlock("radiessency:allassite_panel",
			Material.IRON, MapColor.BLUE, SoundType.METAL).
			setHardness(10F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));
	public static final Supplier<Block> KENOTITE_PANEL = add(()->new BasicBlock("radiessency:kenotite_panel",
			Material.IRON, MapColor.BLACK, SoundType.METAL).
			setHardness(10F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));
	public static final Supplier<Block> ACKRONITE_PANEL = add(()->new BasicBlock("radiessency:ackronite_panel",
			Material.IRON, MapColor.RED, SoundType.METAL).
			setHardness(10F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));

	// Building Blocks
	public static final Supplier<Block> FORTIFIED_STONE = add(()->new BasicBlock("radiessency:fortified_stone",
			Material.ROCK, MapColor.STONE, SoundType.STONE).
			setHardness(3F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));
	public static final Supplier<Block> FORTIFIED_GLASS = add(()->new PartialBlock("radiessency:fortified_glass",
			Material.GLASS, MapColor.AIR, SoundType.GLASS, BlockRenderLayer.CUTOUT).
			setHardness(3F).setLightOpacity(0).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));
	public static final Supplier<Block> KENOTITE_GLASS = add(()->new PartialBlock("radiessency:kenotite_glass",
			Material.GLASS, MapColor.BLACK, SoundType.METAL, BlockRenderLayer.TRANSLUCENT).
			setHardness(8F).setCreativeTab(RadiessencyCreativeTabs.BLOCKS));

	// Radiessence Generators
	public static final Supplier<Block> MATRIX_EXTRACTOR = add(MatrixExtractorBlock::new);
	public static final Supplier<Block> HEAT_GENERATOR = add(HeatGeneratorBlock::new);
	public static final Supplier<Block> BOTANICAL_BURNER = add(BotanicalBurnerBlock::new);
	public static final Supplier<Block> COLD_DISTILLER = add(ColdDistillerBlock::new);
	public static final Supplier<Block> SUN_RAY_ABSORBER = add(SunRayAbsorberBlock::new);
	public static final Supplier<Block> MOON_WELL = add(MoonWellBlock::new);
	public static final Supplier<Block> ENDER_GENERATOR = add(EnderGeneratorBlock::new);

	// Enderpulse Generators
	public static final Supplier<Block> PULSATILE_COLLECTOR_CRYSTAL = add(CollectorCrystalBlock.Pulsatile::new);
	public static final Supplier<Block> ALTERANT_COLLECTOR_CRYSTAL = add(CollectorCrystalBlock.Alterant::new);
	public static final Supplier<Block> VOID_COLLECTOR_CRYSTAL = add(CollectorCrystalBlock.Void::new);
	public static final Supplier<Block> DEMONIC_COLLECTOR_CRYSTAL = add(CollectorCrystalBlock.Demonic::new);

	// Transfer
	public static final Supplier<Block> RAY_TOWER = add(RayTowerBlock::new);

	// RENEC
	public static final Supplier<Block> RENEC_CONTROLLER = add(RENECControllerBlock::new);
	public static final Supplier<Block> RENEC_INJECTOR = add(RENECInjectorBlock::new);
	public static final Supplier<Block> RENEC_EJECTOR = add(RENECEjectorBlock::new);
	public static final Supplier<Block> RENEC_STABILIZER = add(RENECStabilizerBlock::new);
	public static final Supplier<Block> RENEC_EXPANDER = add(RENECExpanderBlock::new);
	public static final Supplier<Block> RENEC_INSPECTOR = add(RENECInspectorBlock::new);

	// Crafting
	public static final Supplier<Block> IRRADIATION_CHAMBER = add(IrradiationChamberBlock::new);
	public static final Supplier<Block> MAGICIAN_TABLE = add(MagicianTableBlock::new);
	public static final Supplier<Block> MITHRILINE_FURNACE = add(MithrilineFurnaceBlock::new);

	// Functional
	public static final Supplier<Block> CHARGING_CHAMBER = add(ChargingChamberBlock::new);
	public static final Supplier<Block> DARKNESS_OBELISK = add(DarknessObeliskBlock::new);

	public static void registerBlocks(IForgeRegistry<Block> registry) {
		BLOCK_SUPPLIERS.stream().map(Supplier::get).filter(Objects::nonNull).forEach(block->{
			registry.register(block);
			BLOCKS.add(block);
		});
	}

	public static void registerItemBlocks(IForgeRegistry<Item> registry) {
		ITEM_BLOCK_SUPPLIERS.stream().map(Supplier::get).filter(Objects::nonNull).forEach(itemBlock->{
			registry.register(itemBlock);
			ITEM_BLOCKS.add(itemBlock);
		});
		registerOredict();
	}

	public static void registerOredict() {

	}

	private static Supplier<Block> add(Supplier<Block> block) {
		return add(block, BasicBlockItem::new);
	}

	private static Supplier<Block> add(Supplier<Block> block, Function<Block, Item> toItem) {
		if(block == null) {
			return ()->null;
		}
		Supplier<Block> blockMemo = Suppliers.memoize(block::get);
		BLOCK_SUPPLIERS.add(blockMemo);
		if(toItem != null) {
			Supplier<Item> item = Suppliers.memoize(()->blockMemo.get() == null ? null : toItem.apply(blockMemo.get()));
			ITEM_BLOCK_SUPPLIERS.add(item);
		}
		return blockMemo;
	}
}
