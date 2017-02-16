package net.machinemuse.numina.basemod.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.machinemuse.numina.basemod.Numina;
import net.machinemuse.numina.basemod.NuminaConfig;
import net.machinemuse.numina.network.NuminaPackets;

import java.io.File;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:57 AM, 9/3/13
 *
 * Ported to Java by lehjr on 10/26/16.
 */
public abstract class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        NuminaConfig.init(event);
        Numina.getInstance().configDir = event.getModConfigurationDirectory();
        File recipesFolder = new File(Numina.getInstance().configDir, "machinemuse/recipes");
        recipesFolder.mkdirs();
        recipesFolder.mkdir();
        //MinecraftForge.EVENT_BUS.register(PlayerTickHandler)
        //    MinecraftForge.EVENT_BUS.register(DeathEventHandler)
        //    NetworkRegistry.instance.registerGuiHandler(Numina.getInstance(), NuminaGuiHandler);
    }

    public void init(FMLInitializationEvent event) {
        NuminaPackets.init();
    }

    public void postInit(FMLPostInitializationEvent event) {}
}
