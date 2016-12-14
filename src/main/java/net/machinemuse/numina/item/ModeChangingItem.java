//package net.machinemuse.numina.item;
//
//import net.machinemuse.numina.network.MusePacketModeChangeRequest;
//import net.machinemuse.numina.network.PacketSender;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.IIcon;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Author: MachineMuse (Claire Semple)
// * Created: 7:11 PM, 9/3/13
// *
// * Ported to Java by lehjr on 12/12/16.
// */
//public class ModeChangingItem implements IModeChangingItem {
//    private static ModeChangingItem ourInstance = new ModeChangingItem();
//
//    public static ModeChangingItem getInstance() {
//        return ourInstance;
//    }
//
//    private ModeChangingItem() {
//    }
//
//    @Override
//    public void setActiveMode(ItemStack itemStack, String newMode) {
//        NuminaItemUtils.getTagCompound(itemStack).setString("mode", newMode);
//    }
//
//    @Override
//    public String getActiveMode(ItemStack itemStack, EntityPlayer player) {
//        String modeFromNBT = NuminaItemUtils.getTagCompound(itemStack).getString("mode");
//        if (!modeFromNBT.isEmpty()) {
//            return modeFromNBT;
//        } else {
//            List<String> validModes = getValidModes(itemStack, player);
//            if (!validModes.isEmpty()) {
//                return validModes.get(0);
//            } else {
//                return "";
//            }
//        }
//    }
//
//    @Override
//    public void cycleMode(ItemStack itemStack, EntityPlayer player, int dMode) {
//        List<String> modes = getValidModes(itemStack, player);
//        if (!modes.isEmpty()) {
//            int newindex = clampMode(modes.indexOf(getActiveMode(itemStack, player)) + dMode, modes.size());
//            String newmode = modes.get(newindex);
//            setActiveMode(itemStack, newmode);
//            PacketSender.sendToServer(new MusePacketModeChangeRequest(player,newmode, player.inventory.currentItem));
//        }
//    }
//
//    @Override
//    public String nextMode(ItemStack itemStack, EntityPlayer player) {
//        List<String> modes = getValidModes(itemStack, player);
//        if (!modes.isEmpty()) {
//            int newindex = clampMode(modes.indexOf(getActiveMode(itemStack, player)) + 1, modes.size());
//            return modes.get(newindex);
//        } else {
//            return "";
//        }
//    }
//
//    @Override
//    public String prevMode(ItemStack itemStack, EntityPlayer player) {
//        List<String> modes = getValidModes(itemStack, player);
//        if (!modes.isEmpty()) {
//            int newindex = clampMode(modes.indexOf(getActiveMode(itemStack, player)) - 1, modes.size());
//            return modes.get(newindex);
//        } else {
//            return "";
//        }
//    }
//
//    private int clampMode(int selection, int modesSize) {
//        if (selection > 0) {
//            return selection % modesSize;
//        } else {
//            return (selection + modesSize * (-selection)) % modesSize;
//        }
//    }
//
//    @Override
//    public IIcon getModeIcon(String mode, ItemStack itemStack, EntityPlayer player) {
//        return null;
//    }
//
//    @Override
//    public List<String> getValidModes(ItemStack stack, EntityPlayer player) {
//        return new ArrayList<String>();
//    }
//}