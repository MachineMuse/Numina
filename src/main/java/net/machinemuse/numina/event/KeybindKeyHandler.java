package net.machinemuse.numina.event;

/**
 * Created by leon on 10/17/16.
 *
 * Yes, a Keybind handler for a single key
 */

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
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeybindKeyHandler {
    public static KeyBinding fovToggleKey = new KeyBinding(I18n.format("keybind.fovfixtoggle"), Keyboard.KEY_NONE, "Numina");
    public boolean fovIsActive = NuminaConfig.fovFixDefaultState();

    public KeybindKeyHandler() {
        ClientRegistry.registerKeyBinding(fovToggleKey);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
        if (fovToggleKey.isPressed()){
            fovIsActive = !fovIsActive;
            if (fovIsActive)
                player.addChatComponentMessage(new ChatComponentText(I18n.format("fovfixtoggle.enabled")));
            else
                player.addChatComponentMessage(new ChatComponentText(I18n.format("fovfixtoggle.disabled")));
        }
    }
}
