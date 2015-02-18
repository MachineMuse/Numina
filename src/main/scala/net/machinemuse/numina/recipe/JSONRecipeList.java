package net.machinemuse.numina.recipe;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 5:54 PM, 11/4/13
 * Modified by: Korynkai 
 *          at: 2:43 PM, 2/4/15
 * TODO: Refactor to also handle Shapeless and Smelting
 */
public class JSONRecipeList {
    static List<JSONRecipe> recipesList = new ArrayList<JSONRecipe>();
    public static Gson gson = new GsonBuilder().registerTypeAdapterFactory(new EnumTypeAdapterFactory()).setPrettyPrinting().create();

    private static FilenameFilter filter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".recipe") || name.endsWith(".recipes");
        }
    };

    public static void loadRecipesFromDir(String dir) {
        try {
            File file = new File(dir);
            if(file.exists()) {
                if(file.isDirectory()) {
                    String[] filenames = file.list(filter);
                    for(String filename:filenames) {
                        MuseLogger.logDebug("Loading recipes from " + filename);
                        loadRecipesFromStream(new FileInputStream(dir + "/" + filename));
                    }
                } else {
                    MuseLogger.logDebug("Loading recipes from " + dir);
                    loadRecipesFromStream(new FileInputStream(file));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadRecipesFromResource(URL resource) {
        try {
            loadRecipesFromStream(new FileInputStream(resource.toString()));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void loadRecipesFromFile(File resource) {
        try {
            loadRecipesFromStream(new FileInputStream(resource));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadRecipesFromStream(InputStream stream) {
        InputStreamReader reader = new InputStreamReader(stream);
        JSONRecipe[] newrecipes = gson.fromJson(reader, JSONRecipe[].class);
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
