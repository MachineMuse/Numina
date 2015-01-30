package net.machinemuse.numina.network

import java.io.DataInputStream
import java.io.InputStream

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.machinemuse.numina.recipe.JSONRecipeList
import net.minecraft.client.entity.EntityClientPlayerMP
import net.minecraft.entity.player.EntityPlayer

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:40 PM, 12/16/13
 */
object MusePacketRecipeUpdate extends MusePackager {
  def read(d: DataInputStream, p: EntityPlayer) = {
    val recipe = readString(d)
    new MusePacketRecipeUpdate(p, recipe)
  }
}

class MusePacketRecipeUpdate(player: EntityPlayer, recipe: String) extends MusePacket {
  override val packager = MusePacketRecipeUpdate

  override def write() {
    writeString(recipe)
  }


  @SideOnly(Side.CLIENT)
  override def handleClient(player: EntityClientPlayerMP) {
    try {
      JSONRecipeList.loadRecipesFromString(recipe)
    } catch {
      case e: Exception =>
    }
  }
}