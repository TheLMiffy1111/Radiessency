package thelm.radiessency.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class RadiessencyItems {

	public static final List<Supplier<Item>> ITEM_SUPPLIERS = new ArrayList<>();
	public static final List<Item> ITEMS = new ArrayList<>();

	// Essence Gems
	public static final Supplier<Item> FIRE_ESSENCE_GEM = add(()->new BasicItem("radiessency:fire_essence_gem").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> AIR_ESSENCE_GEM = add(()->new BasicItem("radiessency:air_essence_gem").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> WATER_ESSENCE_GEM = add(()->new BasicItem("radiessency:water_essence_gem").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> EARTH_ESSENCE_GEM = add(()->new BasicItem("radiessency:earth_essence_gem").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> BALANCED_ESSENCE_GEM = add(()->new BasicItem("radiessency:balanced_essence_gem").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> STASIS_ESSENCE_GEM = add(()->new BasicItem("radiessency:stasis_essence_gem").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> CHAOS_ESSENCE_GEM = add(()->new BasicItem("radiessency:chaos_essence_gem").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));

	// Ingots
	public static final Supplier<Item> RADISTEEL_INGOT = add(()->new BasicItem("radiessency:radisteel_ingot").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> MAGNETIZED_RADISTEEL_INGOT = add(()->new BasicItem("radiessency:magnetized_radisteel_ingot").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> RADIGOLD_INGOT = add(()->new BasicItem("radiessency:radigold_ingot").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> SPHUZITE_INGOT = add(()->new BasicItem("radiessency:sphuzite_ingot").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> ALLASSITE_INGOT = add(()->new BasicItem("radiessency:allassite_ingot").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> KENOTITE_INGOT = add(()->new BasicItem("radiessency:kenotite_ingot").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> TWINKLING_KENOTITE_INGOT = add(()->new BasicItem("radiessency:twinkling_kenotite_ingot").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> ACKRONITE_INGOT = add(()->new BasicItem("radiessency:ackronite_ingot").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));

	// Dusts
	public static final Supplier<Item> RADISTEEL_DUST = add(()->new BasicItem("radiessency:radisteel_dust").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> MITHRILINE_DUST = add(()->new BasicItem("radiessency:mithriline_dust").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> ALLASSITE_DUST = add(()->new BasicItem("radiessency:allassite_dust").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));

	// Plates
	public static final Supplier<Item> DIAMOND_PLATE = add(()->new BasicItem("radiessency:diamond_plate").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> EMERALD_PLATE = add(()->new BasicItem("radiessency:emerald_plate").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> OBSIDIAN_PLATE = add(()->new BasicItem("radiessency:obsidian_plate").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));

	// Platings
	public static final Supplier<Item> STONE_PLATING = add(()->new BasicItem("radiessency:stone_plating").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> RADISTEEL_PLATING = add(()->new BasicItem("radiessency:radisteel_plating").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> SPHUZITE_PLATING = add(()->new BasicItem("radiessency:sphuzite_plating").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> ALLASSITE_PLATING = add(()->new BasicItem("radiessency:allassite_plating").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> KENOTITE_PLATING = add(()->new BasicItem("radiessency:kenotite_plating").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> ACKRONITE_PLATING = add(()->new BasicItem("radiessency:ackronite_plating").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> GLASS_PLATING = add(()->new BasicItem("radiessency:glass_plating").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> REDSTONE_PLATING = add(()->new BasicItem("radiessency:redstone_plating").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> ENDER_PLATING = add(()->new BasicItem("radiessency:ender_plating").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> BLAZE_PLATING = add(()->new BasicItem("radiessency:blaze_plating").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));

	// Tiered Crystals
	public static final Supplier<Item> RADIANT_CRYSTAL = add(()->new BasicItem("radiessency:radiant_crystal").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> PULSATILE_CRYSTAL = add(()->new BasicItem("radiessency:pulsatile_crystal").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> ALTERANT_CRYSTAL = add(()->new BasicItem("radiessency:alterant_crystal").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> VOID_CRYSTAL = add(()->new BasicItem("radiessency:void_crystal").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> DEMONIC_CRYSTAL = add(()->new BasicItem("radiessency:demonic_crystal").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));

	// Tiered Cores
	public static final Supplier<Item> ELEMENTAL_CORE = add(()->new BasicItem("radiessency:elemental_core").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> RADIANT_CORE = add(()->new BasicItem("radiessency:radiant_core").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> PULSATILE_CORE = add(()->new BasicItem("radiessency:pulsatile_core").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> ALTERANT_CORE = add(()->new BasicItem("radiessency:alterant_core").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> VOID_CORE = add(()->new BasicItem("radiessency:void_core").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> DEMONIC_CORE = add(()->new BasicItem("radiessency:demonic_core").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));

	// Crystal Radiessence
	public static final Supplier<Item> CRYSTAL_RADIESSENCE_SHARD = add(()->new BasicItem("radiessency:crystal_radiessence_shard").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> CRYSTAL_RADIESSENCE_PIECE = add(()->new BasicItem("radiessency:crystal_radiessence_piece").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> CRYSTAL_RADIESSENCE_CHUNK = add(()->new BasicItem("radiessency:crystal_radiessence_chunk").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> CRYSTAL_RADIESSENCE_CLUMP = add(()->new BasicItem("radiessency:crystal_radiessence_clump").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> CRYSTAL_RADIESSENCE_CLUSTER = add(()->new BasicItem("radiessency:crystal_radiessence_cluster").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));

	// Elemental Fuel
	public static final Supplier<Item> FIRE_FUEL = add(()->new BasicItem("radiessency:fire_fuel").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> AIR_FUEL = add(()->new BasicItem("radiessency:air_fuel").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> WATER_FUEL = add(()->new BasicItem("radiessency:water_fuel").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> EARTH_FUEL = add(()->new BasicItem("radiessency:earth_fuel").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));

	// Elemental Foci
	public static final Supplier<Item> FIRE_FOCUS = add(()->new BasicItem("radiessency:fire_focus").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> AIR_FOCUS = add(()->new BasicItem("radiessency:air_focus").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> WATER_FOCUS = add(()->new BasicItem("radiessency:water_focus").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> EARTH_FOCUS = add(()->new BasicItem("radiessency:earth_focus").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));

	// Matrix Projections
	public static final Supplier<Item> EMPTY_MATRIX_PROJECTION = add(()->new BasicItem("radiessency:empty_matrix_projection").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> BALANCED_MATRIX_PROJECTION = add(()->new BasicItem("radiessency:balanced_matrix_projection").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> CHAOS_MATRIX_PROJECTION = add(()->new BasicItem("radiessency:chaos_matrix_projection").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> STASIS_MATRIX_PROJECTION = add(()->new BasicItem("radiessency:stasis_matrix_projection").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));

	// Basic Components
	public static final Supplier<Item> RADIESSENCE_LINKER = add(()->new BasicItem("radiessency:radiessence_linker").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> RADIESSENCE_COLLECTOR = add(()->new BasicItem("radiessency:radiessence_collector").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> RADIESSENCE_CONVERTER = add(()->new BasicItem("radiessency:radiessence_converter").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> WORLD_INTERACTOR = add(()->new BasicItem("radiessency:world_interactor").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> RADIESSENCE_REACTOR = add(()->new BasicItem("radiessency:radiessence_reactor").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> IRON_FRAME = add(()->new BasicItem("radiessency:iron_frame").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> GLASS_FRAME = add(()->new BasicItem("radiessency:glass_frame").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> MAGICAL_DISPLAY = add(()->new BasicItem("radiessency:magical_display").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> EYE_OF_ABSORPTION = add(()->new BasicItem("radiessency:eye_of_absorption").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> GOLDEN_ORB = add(()->new BasicItem("radiessency:golden_orb").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));
	public static final Supplier<Item> RADIESSENCE_SOLUTION = add(()->new BasicItem("radiessency:radiessence_solution").
			setCreativeTab(RadiessencyCreativeTabs.ITEMS));

	// Computer Armor Components

	// Equipment

	// Tools
	public static final Supplier<Item> BINDING_GEM = add(BindingGemItem::new);
	public static final Supplier<Item> LINKING_TOOL = add(LinkingToolItem::new);
	public static final Supplier<Item> SOUL_STONE = add(SoulStoneItem::new);

	public static void registerItems(IForgeRegistry<Item> registry) {
		ITEM_SUPPLIERS.stream().map(Supplier::get).filter(Objects::nonNull).forEach(item->{
			registry.register(item);
			ITEMS.add(item);
		});
		registerOredict();
	}

	public static void registerOredict() {

	}

	private static Supplier<Item> add(Supplier<Item> item) {
		if(item == null) {
			return ()->null;
		}
		Supplier<Item> itemMemo = Suppliers.memoize(item::get);
		ITEM_SUPPLIERS.add(itemMemo);
		return itemMemo;
	}
}
