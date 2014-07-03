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
            for (Object b : Block.blockRegistry) {
                Block block = (Block)b;
                if (block != null && block.getUnlocalizedName() != null) {
                    itemMap.put(block.getUnlocalizedName(), new ItemStack(block));
                }
            }
            for (Object b : Item.itemRegistry) {
                Item item = (Item)b;
                if (item != null && item.getUnlocalizedName() != null) {
                    itemMap.put(item.getUnlocalizedName(), new ItemStack(item));
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
