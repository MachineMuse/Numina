package net.machinemuse.numina.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:11 PM, 9/3/13
 *
 * Ported to Java by lehjr on 11/1/16.
 */
public interface IModeChangingItem {
    void setActiveMode(ItemStack itemstack, String newMode);

    String getActiveMode(ItemStack itemStack, EntityPlayer player);

    void cycleMode(ItemStack itemStack, EntityPlayer player, int dMode);

    String nextMode(ItemStack itemStack, EntityPlayer player);

    String prevMode(ItemStack itemStack, EntityPlayer player);

    IIcon getModeIcon(String mode, ItemStack itemStack, EntityPlayer player);

    List<String> getValidModes(ItemStack stack, EntityPlayer player);
}
