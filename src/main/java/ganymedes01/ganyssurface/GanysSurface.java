package ganymedes01.ganyssurface;

import java.io.File;

import ganymedes01.ganyssurface.configuration.ConfigurationHandler;
import ganymedes01.ganyssurface.core.handlers.FuelHandler;
import ganymedes01.ganyssurface.core.handlers.InterModComms;
import ganymedes01.ganyssurface.core.proxy.CommonProxy;
import ganymedes01.ganyssurface.creativetab.CreativeTabSurface;
import ganymedes01.ganyssurface.integration.ModIntegrator;
import ganymedes01.ganyssurface.items.Quiver;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.network.PacketHandler;
import ganymedes01.ganyssurface.recipes.ModRecipes;
import ganymedes01.ganyssurface.world.OceanMonument;
import ganymedes01.ganyssurface.world.SurfaceWorldGen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUI_FACTORY_CLASS)
public class GanysSurface {

	@Instance(Reference.MOD_ID)
	public static GanysSurface instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static CreativeTabs surfaceTab = new CreativeTabSurface();

	public static boolean enableDynamicSnow = true;
	public static boolean enablePoop = true;
	public static boolean enableChocolate = true;
	public static boolean shouldDoVersionCheck = true;
	public static boolean enableWoodenArmour = true;
	public static boolean enableTea = true;
	public static boolean poopRandomBonemeals = true;
	public static boolean enableQuiver = true;
	public static boolean enablePaintings = true;
	public static boolean enable3DRendering = true;
	public static boolean enablePlanter = true;
	public static boolean enableColouredRedstone = true;
	public static boolean enableDislocators = true;
	public static boolean enableItemDisplay = true;
	public static boolean enablePineCones = true;
	public static boolean enableCookedEgg = true;
	public static boolean enablePocketCritters = true;
	public static boolean enableWorkTables = true;
	public static boolean enableLeafWalls = true;
	public static boolean enableDisguisedTrapdoors = true;
	public static boolean enableEncasers = true;
	public static boolean enableOMC = true;
	public static boolean enableSpawnEggs = true;
	public static boolean enableAnalisers = true;
	public static boolean enableIcyPick = true;
	public static boolean enableFertilisedSoil = true;
	public static boolean enableChestPropellant = true;
	public static boolean enableInkHarvester = true;
	public static boolean enableRainDetector = true;
	public static boolean enableCushion = true;
	public static boolean enableLantern = true;
	public static boolean enableBlockOfCharcoal = true;
	public static boolean enableRot = true;
	public static boolean enableWoodenWrench = true;
	public static boolean enableVillageFinder = true;
	public static boolean enableDyedArmour = true;
	public static boolean enableRedyeingBlocks = true;
	public static boolean enableExtraVanillaRecipes = true;
	public static boolean enableEndermanDropsBlocks = true;
	public static boolean enableDynamicTextureChests = false;
	public static boolean enableSlowRail = true;
	public static boolean enableBasalt = true;
	public static boolean enableFlingablePoop = true;
	public static boolean enableWoodenTrapdoors = true;
	public static boolean enableWoodenLadders = true;
	public static boolean enableWoodenSigns = true;
	public static boolean enableStorageBlocks = true;
	public static boolean enableDyeBlocks = true;
	public static boolean enableNoRecipeConflict = true;
	public static boolean enableNoRecipeConflictForCrafTable = true;
	public static boolean enableMarket = true;
	public static boolean enableDispenserShears = true;
	public static boolean enableEatenCake = true;
	public static boolean enableInvertedRedsontLamp = true;

	public static int maxLevelOMCWorks = 15;
	public static int inkHarvesterMaxStrike = 5;
	public static int poopingChance = 15000;
	public static int basaltBlocksPerCluster = 33;
	public static final String[] WOOD_NAMES = new String[] { "oak", "spruce", "birch", "jungle", "acacia", "dark_oak" };

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModIntegrator.preInit();

		ConfigurationHandler.INSTANCE.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MASTER + File.separator + Reference.MOD_ID + ".cfg"));

		GameRegistry.registerWorldGenerator(new SurfaceWorldGen(), 0);

		ModBlocks.init();
		ModItems.init();

		OceanMonument.makeMap();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		PacketHandler.init();

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);

		GameRegistry.registerFuelHandler(new FuelHandler());
		ModRecipes.init();

		proxy.registerEvents();
		proxy.registerTileEntities();
		proxy.registerRenderers();
		proxy.registerEntities();

		ModIntegrator.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ModIntegrator.postInit();
		Quiver.ARROW_STACK_SIZE = new ItemStack(Items.arrow).getMaxStackSize();
	}

	@EventHandler
	public void processIMCRequests(IMCEvent event) {
		InterModComms.processIMC(event);
	}
}