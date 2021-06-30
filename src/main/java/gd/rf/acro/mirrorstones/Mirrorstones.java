package gd.rf.acro.mirrorstones;

import gd.rf.acro.mirrorstones.blocks.AetherBlock;
import gd.rf.acro.mirrorstones.blocks.AlchemicalBlock;
import gd.rf.acro.mirrorstones.blocks.ResearchBlock;
import gd.rf.acro.mirrorstones.effects.CushioningStatusEffect;
import gd.rf.acro.mirrorstones.effects.FastArrowsStatusEffect;
import gd.rf.acro.mirrorstones.items.TheoryItem;
import gd.rf.acro.mirrorstones.structures.MirrorstoneFeature;
import gd.rf.acro.mirrorstones.structures.MirrorstoneGenerator;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class Mirrorstones implements ModInitializer {
	public static final StructurePieceType MY_PIECE = MirrorstoneGenerator.MyPiece::new;
	public static final StructureFeature<DefaultFeatureConfig> MY_STRUCTURE = new MirrorstoneFeature(DefaultFeatureConfig.CODEC);
	private static final ConfiguredStructureFeature<?, ?> MY_CONFIGURED = MY_STRUCTURE.configure(DefaultFeatureConfig.DEFAULT);

	public static final Tag<Item> AIR_ITEMS = TagRegistry.item(new Identifier("mirrorstones","air_items"));
	public static final Tag<Item> EARTH_ITEMS = TagRegistry.item(new Identifier("mirrorstones","earth_items"));
	public static final Tag<Item> FIRE_ITEMS = TagRegistry.item(new Identifier("mirrorstones","fire_items"));
	public static final Tag<Item> WATER_ITEMS = TagRegistry.item(new Identifier("mirrorstones","water_items"));

	@Override
	public void onInitialize() {
		registerBlocks();
		registerItems();
		registerStructures();
		registerStatusEffects();
		System.out.println("Let's get this Augent");
	}
	private void registerStructures()
	{
		Registry.register(Registry.STRUCTURE_PIECE, new Identifier("mirrorstones", "my_piece"), MY_PIECE);
		FabricStructureBuilder.create(new Identifier("mirrorstones", "mirrorstone"), MY_STRUCTURE)
				.step(GenerationStep.Feature.SURFACE_STRUCTURES)
				.defaultConfig(32, 8, 12345)
				.adjustsSurface()
				.register();

		RegistryKey<ConfiguredStructureFeature<?, ?>> myConfigured = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
				new Identifier("mirrorstones", "mirrorstone"));
		BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, myConfigured.getValue(), MY_CONFIGURED);
		BiomeModifications.addStructure(BiomeSelectors.all(), myConfigured);
	}

	public static AlchemicalBlock ALCHEMICAL_AIR = new AlchemicalBlock();
	public static AlchemicalBlock ALCHEMICAL_EARTH = new AlchemicalBlock();
	public static AlchemicalBlock ALCHEMICAL_FIRE = new AlchemicalBlock();
	public static AlchemicalBlock ALCHEMICAL_WATER = new AlchemicalBlock();
	public static final AetherBlock ALCHEMICAL_AETHER = new AetherBlock();
	public static final ResearchBlock RESEARCH_BLOCK = new ResearchBlock(AbstractBlock.Settings.of(Material.AMETHYST));
	private void registerBlocks()
	{
		Registry.register(Registry.BLOCK,new Identifier("mirrorstones","alchemical_air"),ALCHEMICAL_AIR);
		Registry.register(Registry.BLOCK,new Identifier("mirrorstones","alchemical_earth"),ALCHEMICAL_EARTH);
		Registry.register(Registry.BLOCK,new Identifier("mirrorstones","alchemical_fire"),ALCHEMICAL_FIRE);
		Registry.register(Registry.BLOCK,new Identifier("mirrorstones","alchemical_water"),ALCHEMICAL_WATER);
		Registry.register(Registry.BLOCK,new Identifier("mirrorstones","alchemical_aether"),ALCHEMICAL_AETHER);
		Registry.register(Registry.BLOCK,new Identifier("mirrorstones","research_block"),RESEARCH_BLOCK);
	}
	public static final FastArrowsStatusEffect FAST_ARROWS_STATUS_EFFECT = new FastArrowsStatusEffect();
	public static final CushioningStatusEffect CUSHIONING_STATUS_EFFECT = new CushioningStatusEffect();
	private void registerStatusEffects()
	{
		Registry.register(Registry.STATUS_EFFECT,new Identifier("mirrorstones","fast_arrows"),FAST_ARROWS_STATUS_EFFECT);
		Registry.register(Registry.STATUS_EFFECT,new Identifier("mirrorstones","cushioning"),CUSHIONING_STATUS_EFFECT);
	}
	public static final TheoryItem FIRE_THEORY = new TheoryItem(new Item.Settings().group(ItemGroup.MISC),"fire_devotee");
	public static final TheoryItem WATER_THEORY = new TheoryItem(new Item.Settings().group(ItemGroup.MISC),"water_devotee");
	public static final TheoryItem AIR_THEORY = new TheoryItem(new Item.Settings().group(ItemGroup.MISC),"air_devotee");
	public static final TheoryItem EARTH_THEORY = new TheoryItem(new Item.Settings().group(ItemGroup.MISC),"earth_devotee");
	private void registerItems()
	{
		Registry.register(Registry.ITEM,new Identifier("mirrorstones","fire_theory"),FIRE_THEORY);
		Registry.register(Registry.ITEM,new Identifier("mirrorstones","earth_theory"),EARTH_THEORY);
		Registry.register(Registry.ITEM,new Identifier("mirrorstones","water_theory"),WATER_THEORY);
		Registry.register(Registry.ITEM,new Identifier("mirrorstones","air_theory"),AIR_THEORY);
		Registry.register(Registry.ITEM,new Identifier("mirrorstones","research_block"),new BlockItem(RESEARCH_BLOCK,new Item.Settings().group(ItemGroup.MISC)));
	}


	public static ItemStack createBook(String author, String title, Object ...pages)
	{
		ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
		NbtCompound tags = new NbtCompound();
		tags.putString("author",author);
		tags.putString("title",title);
		NbtList contents = new NbtList();
		for (Object page : pages) {
			contents.add(NbtString.of("{\"text\":\""+page+"\"}"));
		}
		tags.put("pages",contents);
		book.setTag(tags);
		return book;
	}
}
