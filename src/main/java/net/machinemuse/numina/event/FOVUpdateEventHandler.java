package net.machinemuse.numina.event;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.machinemuse.numina.basemod.NuminaConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.FOVUpdateEvent;
import org.lwjgl.input.Keyboard;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 10:07 PM, 10/17/13
 *
 * Ported to Java by lehjr on 10/10/16.
 */
public class FOVUpdateEventHandler {
    public static KeyBinding fovToggleKey = new KeyBinding(I18n.format("keybind.fovfixtoggle"), Keyboard.KEY_NONE, "Numina");
    public boolean fovIsActive = NuminaConfig.fovFixDefaultState();

    @SubscribeEvent
    public void onFOVUpdate(FOVUpdateEvent e) {
        if (NuminaConfig.useFOVFixLevel() > 0) {
            if (fovToggleKey.isPressed()){
                fovIsActive = !fovIsActive;
                if (fovIsActive)
                    e.entity.addChatComponentMessage(new ChatComponentText(I18n.format("fovfixtoggle.enabled")));
                else
                    e.entity.addChatComponentMessage(new ChatComponentText(I18n.format("fovfixtoggle.disabled")));
            }

            if (fovIsActive) {
                IAttributeInstance attributeinstance = e.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
                e.newfov = (float) (e.fov / ((attributeinstance.getAttributeValue() / e.entity.capabilities.getWalkSpeed() + 1.0) / 2.0));
                if ( NuminaConfig.useFOVFixLevel() > 1 && e.entity.isSprinting() ) {
                    e.newfov += 0.15F;
                }
            }
        }
    }
}