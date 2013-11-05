package net.machinemuse.numina.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 5:06 PM, 11/4/13
 */
public class ItemNameMappings {
    private static Map<String, ItemStack> itemMap;

    private static Map<String, ItemStack> getItemMap() {
        if (itemMap == null) {
            itemMap = new HashMap<String, ItemStack>();
            for (Block b : Block.blocksList) {
                if (b != null && b.getUnlocalizedName() != null) {
                    itemMap.put(b.getUnlocalizedName(), new ItemStack(b));
                }
            }
            for (Item b : Item.itemsList) {
                if (b != null && b.getUnlocalizedName() != null) {
                    itemMap.put(b.getUnlocalizedName(), new ItemStack(b));
                }
            }
        }
        return itemMap;
    }

    public static ItemStack getItem(String name) {
        if (getItemMap().containsKey(name))
            return getItemMap().get(name);
        else
            return null;
    }
}
