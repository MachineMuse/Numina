package net.machinemuse.numina.basemod

import net.minecraftforge.common.MinecraftForge
import net.machinemuse.numina.mouse.MouseEventHandler
import net.machinemuse.numina.render.{FOVUpdateEventHandler, RenderGameOverlayEventHandler}
import net.machinemuse.numina.network.{MusePacketRecipeUpdate, MusePacket}
import net.minecraft.entity.player.{EntityPlayerMP, EntityPlayer}
import net.minecraft.client.Minecraft
import net.machinemuse.numina.recipe.{JSONRecipe, JSONRecipeList}
import cpw.mods.fml.common.IPlayerTracker
import cpw.mods.fml.common.network.{Player, PacketDispatcher}

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:57 AM, 9/3/13
 */
trait NuminaProxy {
  def PreInit() = {}

  def Init() = {}

  def PostInit() = {}

  def sendPacketToClient(packet: MusePacket, player: EntityPlayer) = {
    player.asInstanceOf[EntityPlayerMP].playerNetServerHandler.sendPacketToPlayer(packet.getPacket131)
  }

  def sendPacketToServer(packet: MusePacket) = {
    Minecraft.getMinecraft.thePlayer.sendQueue.addToSendQueue(packet.getPacket131)
  }
}

object NuminaProxyClient extends NuminaProxy {
  override def Init() = {
    MinecraftForge.EVENT_BUS.register(MouseEventHandler)
    MinecraftForge.EVENT_BUS.register(RenderGameOverlayEventHandler)
    MinecraftForge.EVENT_BUS.register(FOVUpdateEventHandler)
  }
}

object NuminaProxyServer extends NuminaProxy {
  override def PostInit() = {
    JSONRecipeList.loadRecipesFromDir(Numina.configDir.toString + "/machinemuse/recipes/")
  }
}

class NuminaPlayerTracker extends IPlayerTracker {
  def onPlayerLogin(player: EntityPlayer) {
    for (recipe <- JSONRecipeList.getJSONRecipesList.toArray) {
      val recipeArray = Array(recipe)
      val recipeAsString:String = JSONRecipeList.gson.toJson(recipeArray)
      PacketDispatcher.sendPacketToPlayer(
        new MusePacketRecipeUpdate(player.asInstanceOf[Player], recipeAsString).getPacket131,
        player.asInstanceOf[Player]
      )
    }
  }

  def onPlayerLogout(player: EntityPlayer) {}

  def onPlayerChangedDimension(player: EntityPlayer) {}

  def onPlayerRespawn(player: EntityPlayer) {}
}

