package net.machinemuse.numina.recipe;

import com.google.gson.Gson;
import cpw.mods.fml.common.registry.GameRegistry;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 5:54 PM, 11/4/13
 */
public class JSONRecipeList {
    static JSONRecipe[] recipesList;

    public static void loadRecipes(String dir) {
        Gson gson = new Gson();
        String json = "";
        try {
            json = readFile(dir, StandardCharsets.UTF_8);
            recipesList = gson.fromJson(json, JSONRecipe[].class);
            for(JSONRecipe recipe : recipesList) {
                GameRegistry.addRecipe(recipe);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }
}
