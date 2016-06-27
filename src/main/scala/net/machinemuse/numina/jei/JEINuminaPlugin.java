package net.machinemuse.numina.jei;


import mezz.jei.api.*;

import javax.annotation.Nonnull;


/**
 * The main class for a plugin. Everything communicated between a mod and JEI is through this class.
 * IModPlugins must have the @JEIPlugin annotation to get loaded by JEI.
 * This class must not import anything that could be missing at runtime (i.e. code from any other mod).
 */
@JEIPlugin
public class JEINuminaPlugin implements IModPlugin {
    public static IJeiHelpers jeiHelpers;


    /**
     * Register this mod plugin with the mod registry.
     * Called just before the game launches.
     * Will be called again if config
     */
    @Override
    public void register(@Nonnull IModRegistry registry) {
//        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeHandlers(new JSONRecipeHandler());
    }


    /**
     * Called when jei's runtime features are available, after all mods have registered.
     * @since JEI 2.23.0
     */
    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {

    }
}
