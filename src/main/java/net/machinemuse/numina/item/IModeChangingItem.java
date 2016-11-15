package net.machinemuse.numina.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:11 PM, 9/3/13
 *
 * Ported to Java by lehjr on 11/1/16.
 */
public interface IModeChangingItem
{
    void setActiveMode(ItemStack stack, String newMode) ;

    String getActiveMode(ItemStack stack, EntityPlayer player);

    void cycleMode(ItemStack stack, EntityPlayer player, int dMode);

    String nextMode(ItemStack stack, EntityPlayer player);

    String prevMode(ItemStack stack, EntityPlayer player);

    @Nullable
    IIcon getModeIcon(String mode, ItemStack stack, EntityPlayer player);

    @Nullable
    List<String> getValidModes(ItemStack stack, EntityPlayer player);
}