package net.machinemuse.numina.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEINuminaConfig implements IConfigureNEI {
    @Override
    public void loadConfig()
    {
        System.out.println("In NEI loadConfig");
        API.registerRecipeHandler(new JSONRecipeHandler());
        API.registerUsageHandler(new JSONRecipeHandler());
    }

    @Override
    public String getName()
    {
        return "Numina";
    }

    @Override
    public String getVersion()
    {
        return "1.0";
    }
}
