package net.machinemuse.numina.recipe;

import net.minecraft.item.ItemStack;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:51 PM, 11/4/13
 */
public interface IItemMatcher {
    public boolean matchesItem(ItemStack stack);
}
