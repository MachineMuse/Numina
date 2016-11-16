package net.machinemuse.numina.basemod;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import net.machinemuse.numina.network.NuminaPackets;
import net.machinemuse.numina.recipe.JSONRecipeList;

import javax.annotation.Nonnull;
import java.io.File;

import static net.machinemuse.numina.basemod.Numina.MODID;
import static net.machinemuse.numina.basemod.Numina.VERSION;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:06 AM, 6/18/13
 *
 * Ported to Java by lehjr on 11/15/16.
 */
@Mod(modid = MODID, version = VERSION)
public class Numina {
    public static final String MODID = "numina";
    public static final String VERSION = "@numina_version@";

    @SidedProxy(clientSide = "net.machinemuse.numina.basemod.NuminaProxyClient", serverSide = "net.machinemuse.numina.basemod.NuminaProxyServer")
    static NuminaProxy proxy = null;
    public static File configDir = null;

    @Nonnull
    private static Numina INSTANCE;

    @Nonnull
    @Mod.InstanceFactory
    public static Numina getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Numina();
        return INSTANCE;
    }

    @Mod.EventHandler
    private void preinit(FMLPreInitializationEvent e) {
        NuminaConfig.init(e);
        configDir = e.getModConfigurationDirectory();
        File recipesFolder = new File(configDir, "machinemuse/recipes");
        recipesFolder.mkdirs();
        recipesFolder.mkdir();
        //MinecraftForge.EVENT_BUS.register(PlayerTickHandler)
        //    MinecraftForge.EVENT_BUS.register(DeathEventHandler)
        //    NetworkRegistry.instance.registerGuiHandler(Numina, NuminaGuiHandler)
        proxy.PreInit();
    }

    @Mod.EventHandler
    private void init(FMLInitializationEvent e) {
        proxy.Init();
        NuminaPackets.init();
    }

    @Mod.EventHandler
    private void postinit(FMLPostInitializationEvent e) {
        proxy.PostInit();
    }

    @Mod.EventHandler private void serverstart(FMLServerStartedEvent e) {
        JSONRecipeList.loadRecipesFromDir(Numina.configDir.toString() + "/machinemuse/recipes/");
        FMLCommonHandler.instance().bus().register(new NuminaPlayerTracker());
    }
}