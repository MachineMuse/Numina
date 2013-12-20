package net.machinemuse.numina.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:48 PM, 11/4/13
 */
public class SimpleItemMatcher implements IItemMatcher {
    public Integer id;
    public Integer meta;
    public String unlocalizedName;
    public String oredictName;
    public NBTTagCompound nbt;

    public SimpleItemMatcher() {
    }

    public SimpleItemMatcher(int id) {
        this.id = id;
    }

    public SimpleItemMatcher(String oredictName) {
        this.oredictName = oredictName;
    }

    public SimpleItemMatcher(int id, int meta) {
        this.id = id;
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
        if (id != null && id != stack.itemID) {
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
                if (ore.itemID == stack.itemID && ore.getItemDamage() == stack.getItemDamage()) {
                    found = true;
                }
            }
            if (!found) return false;
        }
        if (nbt != null && !nbt.equals(stack.getTagCompound())) {
            return false;
        }

        return true;
    }

    public SimpleItemMatcher copy() {
        SimpleItemMatcher copy = new SimpleItemMatcher();
        copy.id = this.id;
        copy.meta = this.meta;
        copy.unlocalizedName = this.unlocalizedName;
        copy.oredictName = this.unlocalizedName;
        copy.nbt = this.nbt;
        return copy;
    }
}
