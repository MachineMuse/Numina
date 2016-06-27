package net.machinemuse.numina.render

import net.machinemuse.numina.basemod.NuminaConfig
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.I18n
import net.minecraft.client.settings.KeyBinding
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.attributes.IAttributeInstance
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.client.event.FOVUpdateEvent
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent
import net.minecraftforge.fml.relauncher.{Side, SideOnly}
import org.lwjgl.input.Keyboard

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 10:07 PM, 10/17/13
 */
object FOVUpdateEventHandler {
  @SubscribeEvent def onFOVUpdate(e: FOVUpdateEvent) {
    if (FOVUpdateToggleKeyHandler.fovIsActive) {
      val attributeinstance: IAttributeInstance = e.getEntity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
      e.setNewfov(e.getFov / ((attributeinstance.getAttributeValue / e.getEntity.capabilities.getWalkSpeed + 1.0) / 2.0).toFloat)
    }
  }
}

// TODO: save fovIsActive state ?
object FOVUpdateToggleKeyHandler {
  var fovIsActive: Boolean = NuminaConfig.fovFixDefaultState
  val fovToggleKey = new KeyBinding(I18n.format("keybind.fovfixtoggle"), Keyboard.KEY_NONE, "Numina")
  ClientRegistry.registerKeyBinding(fovToggleKey)

  @SubscribeEvent
  def onKeyInput(event: KeyInputEvent) {
    val player = Minecraft.getMinecraft.thePlayer;
    if (fovToggleKey.isPressed){
      fovIsActive = !fovIsActive
      if (fovIsActive)
        player.addChatComponentMessage(new TextComponentString(I18n.format("fovfixtoggle.enabled")))
      else
        player.addChatComponentMessage(new TextComponentString(I18n.format("fovfixtoggle.disabled")))
    }
  }
}






