package net.machinemuse.numina.recipe;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import net.machinemuse.numina.general.MuseLogger;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EnumTypeAdapterFactory;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.Iterator;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 5:54 PM, 11/4/13
 * Modified by: Korynkai 
 *          at: 2:57 AM, 2/19/15
 * TODO: Refactor to also handle Shapeless and Smelting
 */
public class JSONRecipeList {
    static List<JSONRecipe> recipesList = new ArrayList<JSONRecipe>();
    public static Gson gson = new GsonBuilder().registerTypeAdapterFactory(new EnumTypeAdapterFactory()).setPrettyPrinting().create();
    
    private static String[] extensions = { "recipe", "recipes" };

    public static void loadRecipesFromDir(String dir) {
        File file = new File(dir);
        if(file.exists()) {
            if(file.isDirectory()) {
								Collection files = FileUtils.listFiles(file, extensions, false);
								for (Iterator iterator = files.iterator(); iterator.hasNext();) {
										File recipeFile = (File) iterator.next();
								    loadRecipesFromFile(recipeFile);
								}
            } else {
								MuseLogger.logDebugPrintStack("Numina has detected a deprecated call to net.machinemuse.numina.recipe.JSONRecipeList.loadRecipesFromDir(java.lang.String)", new Throwable());
								MuseLogger.logDebug("This is non-fatal and will still work, but loadRecipesFromDir(java.lang.String) should be avoided if the target is a file and not a directory.");
								MuseLogger.logDebug("Use loadRecipesFromFile(java.io.File) or loadRecipesFromResource(java.net.URL) instead.");
								MuseLogger.logDebug("If you are seeing this message as a normal player, please report this message to the developer of the mod making the call to loadRecipesFromDir(java.lang.String).");
								MuseLogger.logDebug("You can hide this message by disabling debugging in the numina.cfg file.");
								loadRecipesFromFile(file);
            }
        }
    }
    
    public static void loadRecipesFromFile(File resource) {
        try {
            loadRecipesFromResource(resource.toURI().toURL());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void loadRecipesFromResource(URL resource) {
        try {
            MuseLogger.logDebug("Loading recipes from " + resource.toString());
            loadRecipesFromString(Resources.toString(resource, Charsets.UTF_8));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void loadRecipesFromString(String json) {
				JSONRecipe[] newrecipes = gson.fromJson(json, JSONRecipe[].class);
        recipesList.addAll(Arrays.asList(newrecipes));
        for (JSONRecipe recipe : newrecipes) {
            if (recipe != null)
                getCraftingRecipeList().add(recipe);
        }
    }

    public static List<IRecipe> getCraftingRecipeList() {
        return CraftingManager.getInstance().getRecipeList();
    }

    public static List<JSONRecipe> getJSONRecipesList() {
        return recipesList;
    }

}
