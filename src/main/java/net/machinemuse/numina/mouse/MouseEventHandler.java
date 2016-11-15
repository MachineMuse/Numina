package net.machinemuse.numina.mouse;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.machinemuse.numina.item.IModeChangingItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.MouseEvent;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:40 PM, 9/5/13
 *
 * Ported to Java by lehjr on 10/22/16.
 */
public final class MouseEventHandler {
    @SubscribeEvent
    public void onMouseEvent(MouseEvent e) {
        if (e.dwheel != 0) {
            EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
            ItemStack stack = player.getCurrentEquippedItem();
            if (stack != null && stack.getItem() instanceof IModeChangingItem) {
                IModeChangingItem item = (IModeChangingItem) stack.getItem();
                if (player.isSneaking()) {
                    item.cycleMode(stack, player, e.dwheel / 120);
                    e.setCanceled(true);
                }
            }
        }
    }
}