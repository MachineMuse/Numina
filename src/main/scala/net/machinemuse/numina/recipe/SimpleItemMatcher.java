package net.machinemuse.numina.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:48 PM, 11/4/13
 */
public class SimpleItemMatcher implements IItemMatcher {
    public Item item;
    public Integer meta;
    public String unlocalizedName;
    public String oredictName;
//    public NBTTagCompound nbt;

    public SimpleItemMatcher() {
    }

    public SimpleItemMatcher(Item item) {
        this.item = item;
    }

    public SimpleItemMatcher(String oredictName) {
        this.oredictName = oredictName;
    }

    public SimpleItemMatcher(Item item, int meta) {
        this.item = item;
        this.meta = meta;
    }

    @Override
    public boolean matchesItem(ItemStack stack) {
        if (stack == null) {
            return false;
        }
        if(stack.getItem() == null) {
            return false;
        }
        if (item != null && item != stack.getItem()) {
            return false;
        }
        if (meta != null && meta != stack.getItemDamage()) {
            return false;
        }
        if (unlocalizedName != null && !unlocalizedName.equals(stack.getItem().getUnlocalizedName(stack))) {
            return false;
        }
        if (oredictName != null) {
            boolean found = false;
            for (ItemStack ore : OreDictionary.getOres(oredictName)) {
                if (ore.getItem() == stack.getItem() && ore.getItemDamage() == stack.getItemDamage()) {
                    found = true;
                }
            }
            if (!found) return false;
        }
//        if (nbt != null && !nbt.equals(stack.getTagCompound())) {
//            return false;
//        }

        return true;
    }

    public SimpleItemMatcher copy() {
        SimpleItemMatcher copy = new SimpleItemMatcher();
        copy.item = this.item;
        copy.meta = this.meta;
        copy.unlocalizedName = this.unlocalizedName;
        copy.oredictName = this.unlocalizedName;
//        copy.nbt = this.nbt;
        return copy;
    }
}
