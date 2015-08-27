package net.machinemuse.numina.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import net.machinemuse.numina.general.MuseLogger;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:55 PM, 11/4/13
 */
public class SimpleItemMaker implements IItemMaker {
    public Integer meta;
    public Integer quantity;
    public String unlocalizedName;
    public String oredictName;
    public String registryName;
    public String itemStackName;
    public String nbtString;

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
        NBTTagCompound nbt = null;

        if(nbtString != null) {
            try {
                nbt = (NBTTagCompound) JsonToNBT.func_150315_a(nbtString);
            } catch (Exception e) {
                MuseLogger.logException("Bad NBT string in item! Attempting to create generic item instead.", e);
            }
        }

        if (itemStackName != null) {
            try {
                String[] names = itemStackName.split(":");
                ItemStack stack = GameRegistry.findItemStack(names[0], names[1], newquantity);
                if(this.meta != null) stack.setItemDamage(meta);
                if(nbt != null) stack.setTagCompound(nbt);
                return stack;
            } catch (Exception e) {
                MuseLogger.logError("Unable to load " + itemStackName + " from Item Registry");
                return null;
            }
        } else if (registryName != null) {
            try {
                String[] names = registryName.split(":");
                ItemStack stack = new ItemStack(GameRegistry.findItem(names[0], names[1]), newquantity, newmeta);
                if(nbt != null) stack.setTagCompound(nbt);
                return stack;
            } catch (Exception e) {
                MuseLogger.logError("Unable to load " + registryName + " from Item Registry");
                return null;
            }
        } else if (oredictName != null) {
            try {
                ItemStack stack = OreDictionary.getOres(oredictName).get(0).copy();
                stack.stackSize = Math.min(newquantity, stack.getMaxStackSize());
                if(nbt != null) stack.setTagCompound(nbt);
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
                stack.stackSize = Math.min(newquantity, stack.getMaxStackSize());
                if(nbt != null) stack.setTagCompound(nbt);
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
