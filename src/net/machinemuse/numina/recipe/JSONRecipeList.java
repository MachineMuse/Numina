package net.machinemuse.numina.recipe;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import cpw.mods.fml.common.registry.GameRegistry;
import net.machinemuse.numina.general.MuseLogger;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 5:54 PM, 11/4/13
 */
public class JSONRecipeList {
    static List<JSONRecipe> recipesList = new ArrayList<JSONRecipe>();
    static final Gson gson = new Gson();

    private static FilenameFilter filter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".recipe") || name.endsWith(".recipes");
        }
    };

    public static void loadRecipesFromDir(String dir) {
        try {
            File file= new File(dir);
            if(file.exists()) {
                if(file.isDirectory()) {
                    String[] filenames = file.list(filter);
                    for(String filename:filenames) {
                        String json = readFile(dir + "/" + filename, Charsets.UTF_8);
                        MuseLogger.logDebug("Loading recipes from " + filename);
                        loadRecipesFromString(json);
                    }
                } else {
                    String json = readFile(dir, Charsets.UTF_8);
                    MuseLogger.logDebug("Loading recipes from " + dir);
                    loadRecipesFromString(json);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadRecipesFromResource(URL resource) {
        try {
            String json = Resources.toString(resource, Charsets.UTF_8);
            loadRecipesFromString(json);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadRecipesFromString(String json) {
        JSONRecipe[] newrecipes = gson.fromJson(json, JSONRecipe[].class);
        recipesList.addAll(Arrays.asList(newrecipes));
        for (JSONRecipe recipe : newrecipes) {
            if (recipe != null)
                GameRegistry.addRecipe(recipe);
        }
    }

    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }
}
