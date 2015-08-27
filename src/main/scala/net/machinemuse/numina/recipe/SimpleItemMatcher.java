package net.machinemuse.numina.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import net.machinemuse.numina.general.MuseLogger;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:48 PM, 11/4/13
 */
public class SimpleItemMatcher implements IItemMatcher {
    public Integer meta;
    public String unlocalizedName;
    public String oredictName;
    public String registryName;
    public String itemStackName;
    public String nbtString;

    public SimpleItemMatcher() {
    }

    @Override
    public boolean matchesItem(ItemStack stack) {
        if (stack == null) {
            return false;
        }
        if (stack.getItem() == null) {
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
        if (registryName != null) {
            String[] names = registryName.split(":");
            Item item = GameRegistry.findItem(names[0], names[1]);
            if (item == null) {
                MuseLogger.logError("Item " + registryName + " not found in registry for recipe.");
                return false;
            }
            if (item != stack.getItem()) return false;
        }
        if (itemStackName != null) {
            String[] names = itemStackName.split(":");
            ItemStack compareStack = GameRegistry.findItemStack(names[0], names[1], 1);
            if(compareStack == null) {
                MuseLogger.logError("ItemStack " + itemStackName + " not found in registry for recipe.");
                return false;
            }
            if(stack.getItemDamage() != compareStack.getItemDamage()) return false;
            if(stack.getItem() != compareStack.getItem()) return false;
            if(!stack.getTagCompound().equals(compareStack.getTagCompound())) return false;

        }
        if (nbtString != null) {
            try {
                NBTTagCompound nbt = (NBTTagCompound) JsonToNBT.func_150315_a(nbtString);
                if (!nbt.equals(stack.getTagCompound())) {
                    return false;
                }
            } catch (Exception e) {
                MuseLogger.logException("Bad NBT string in item!", e);
                return false;
            }
        }

        return true;
    }

    public SimpleItemMatcher copy() {
        SimpleItemMatcher copy = new SimpleItemMatcher();
        copy.meta = this.meta;
        copy.unlocalizedName = this.unlocalizedName;
        copy.oredictName = this.unlocalizedName;
//        copy.nbt = this.nbt;
        return copy;
    }
}
