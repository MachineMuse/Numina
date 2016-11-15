package net.machinemuse.numina.item;

import net.machinemuse.numina.network.MusePacketModeChangeRequest;
import net.machinemuse.numina.network.PacketSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:11 PM, 9/3/13
 *
 * Ported to Java by lehjr on 11/1/16.
 */
public class ModeChangingItem extends Item implements IModeChangingItem {
    private static ModeChangingItem INSTANCE;

    public static ModeChangingItem getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ModeChangingItem();
        return INSTANCE;
    }

    @Override
    public void setActiveMode(ItemStack stack, String newMode) {
        NuminaItemUtils.getTagCompound(stack).setString("mode", newMode);
    }

    @Override
    public String getActiveMode(ItemStack stack, EntityPlayer player) {
        String modeFromNBT = NuminaItemUtils.getTagCompound(stack).getString("mode");
        String s;
        if (modeFromNBT.isEmpty()) {
            List<String> validModes = getValidModes(stack, player);
            s = (validModes!=null && (validModes.size() > 0) ? validModes.get(0) : "");
        }
        else {
            s = modeFromNBT;
        }
        return s;
    }

    @Override
    public void cycleMode(ItemStack stack, EntityPlayer player, int dMode) {
        List<String> modes = this.getValidModes(stack, player);
        if (modes.size() > 0) {
            int newindex = clampMode(modes.indexOf(this.getActiveMode(stack, player)) + dMode, modes.size());
            String newmode = (String)modes.get(newindex);
            this.setActiveMode(stack, newmode);
            PacketSender.sendToServer(new MusePacketModeChangeRequest(player, newmode, player.inventory.currentItem));
        }
    }

    @Override
    public String nextMode(ItemStack stack, EntityPlayer player) {
        List<String> modes = getValidModes(stack, player);
        String s;
        if (modes.size() > 0) {
            int newindex = clampMode(modes.indexOf(getActiveMode(stack, player)) + 1, modes.size());
            s = (String)modes.get(newindex);
        }
        else {
            s = "";
        }
        return s;
    }

    @Override
    public String prevMode(ItemStack stack, EntityPlayer player) {
        List<String> modes = this.getValidModes(stack, player);
        String s;
        if (modes.size() > 0) {
            int newindex = clampMode(modes.indexOf(getActiveMode(stack, player)) - 1, modes.size());
            s = (String)modes.get(newindex);
        }
        else {
            s = "";
        }
        return s;
    }

    private static int clampMode(int selection, int modesSize) {
        return (selection > 0) ? (selection % modesSize) : ((selection + modesSize * -selection) % modesSize);
    }

    @Nullable
    @Override
    public IIcon getModeIcon(String mode, ItemStack stack, EntityPlayer player) {
        return null;
    }

    @Nullable
    @Override
    public List<String> getValidModes(ItemStack stack, EntityPlayer player) {
        return new ArrayList<String>();
    }
}