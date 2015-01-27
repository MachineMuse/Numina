package net.machinemuse.numina.recipe;

import net.machinemuse.numina.general.MuseLogger;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:55 PM, 11/4/13
 */
public class SimpleItemMaker implements IItemMaker {
    public Item item;
    public Integer meta;
    public Integer quantity;
    public String unlocalizedName;
    public String oredictName;
//    public NBTTagCompound nbt;

    @Override
    public ItemStack makeItem(InventoryCrafting i) {
        return getRecipeOutput();
    }

    private int getOrElse(Integer input, int defaultval) {
        if (input == null)
            return defaultval;
        else
            return input;
    }

    @Override
    public ItemStack getRecipeOutput() {
        int newmeta = getOrElse(this.meta, 0);
        int newquantity = getOrElse(this.quantity, 1);
        if (item != null) {
            ItemStack stack = new ItemStack(item, newquantity, newmeta);
//            if (nbt != null) stack.stackTagCompound = (NBTTagCompound) nbt.copy();
            return stack;
        } else if (oredictName != null) {
            try {
                ItemStack stack = OreDictionary.getOres(oredictName).get(0).copy();
                stack.stackSize = newquantity;
                return stack;
            } catch (Exception e) {
                MuseLogger.logError("Unable to load " + oredictName + " from oredict");
                return null;
            }
        } else if (unlocalizedName != null) {
            try {
                ItemStack stack = ItemNameMappings.getItem(unlocalizedName).copy();
                newmeta = getOrElse(this.meta, stack.getItemDamage());
                stack.setItemDamage(newmeta);
                stack.stackSize = newquantity;
                return stack;
            } catch (Exception e) {
                MuseLogger.logError("Unable to load " + unlocalizedName + " from unlocalized names");
                return null;
            }
        } else {
            return null;
        }
    }
}
