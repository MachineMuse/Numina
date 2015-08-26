package net.machinemuse.numina.recipe;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            for (Object obj : Block.blockRegistry) {
                Block block = (Block)obj;
                if (block != null && block.getUnlocalizedName() != null) {
                    itemMap.put(block.getUnlocalizedName(), new ItemStack(block));
                }
            }
            for (Object obj : Item.itemRegistry) {
                Item item = (Item)obj;
                if (item != null) {
                    if(item.getHasSubtypes()) {
                        List<ItemStack> stacklist = new ArrayList<ItemStack>();
                        for(CreativeTabs tab : item.getCreativeTabs()) {
                            item.getSubItems(item, tab, stacklist);
                        }
                        for(ItemStack stack : stacklist) {
                            itemMap.put(stack.getUnlocalizedName(), stack.copy());
                        }
                    } else {
                        itemMap.put(item.getUnlocalizedName(), new ItemStack(item));
                    }
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
