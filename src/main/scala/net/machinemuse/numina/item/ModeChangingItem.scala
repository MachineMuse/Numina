package net.machinemuse.numina.item

import net.machinemuse.numina.network.{MusePacketModeChangeRequest, PacketSender}
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

/**
  * Author: MachineMuse (Claire Semple)
  * Created: 7:11 PM, 9/3/13
  */
trait ModeChangingItem {
  def setActiveMode(stack: ItemStack, newMode: String) = {
    NuminaItemUtils.getTagCompound(stack).setString("mode", newMode)
  }

  def getActiveMode(stack: ItemStack, player: EntityPlayer): String = {
    val modeFromNBT = NuminaItemUtils.getTagCompound(stack).getString("mode")
    if (!modeFromNBT.isEmpty) {
      modeFromNBT
    } else {
      val validModes = getValidModes(stack, player)
      if (validModes.nonEmpty) {
        validModes.head
      } else {
        ""
      }
    }
  }

  def cycleMode(stack: ItemStack, player: EntityPlayer, dMode: Int) {
    val modes = getValidModes(stack, player)
    if (modes.nonEmpty) {
      val newindex = clampMode(modes.indexOf(getActiveMode(stack, player)) + dMode, modes.size)
      val newmode = modes(newindex)
      setActiveMode(stack, newmode)
      PacketSender.sendToServer(new MusePacketModeChangeRequest(player,newmode, player.inventory.currentItem))
    }
  }

  def nextMode(stack: ItemStack, player: EntityPlayer) = {
    val modes = getValidModes(stack, player)
    if (modes.nonEmpty) {
      val newindex = clampMode(modes.indexOf(getActiveMode(stack, player)) + 1, modes.size)
      modes(newindex)
    } else {
      ""
    }
  }

  def prevMode(stack: ItemStack, player: EntityPlayer) = {
    val modes = getValidModes(stack, player)
    if (modes.nonEmpty) {
      val newindex = clampMode(modes.indexOf(getActiveMode(stack, player)) - 1, modes.size)
      modes(newindex)
    } else {
      ""
    }
  }

  private def clampMode(selection: Int, modesSize: Int): Int = {
    if (selection > 0) {
      selection % modesSize
    } else {
      (selection + modesSize * (-selection)) % modesSize
    }
  }

  def getModeIcon(mode: String, stack: ItemStack, player: EntityPlayer): Option[TextureAtlasSprite]

  def getValidModes(stack: ItemStack, player: EntityPlayer): Seq[String]

}
