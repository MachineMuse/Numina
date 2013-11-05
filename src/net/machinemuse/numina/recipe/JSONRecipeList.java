package net.machinemuse.numina.recipe;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import cpw.mods.fml.common.registry.GameRegistry;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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

    public static void loadRecipesFromDir(String dir) {
        try {
            String json = readFile(dir, StandardCharsets.UTF_8);
            loadRecipesFromString(json);
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
