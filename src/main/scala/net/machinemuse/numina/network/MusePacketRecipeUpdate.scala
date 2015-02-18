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
    //new MusePacketRecipeUpdate(p, d)
    val recipe = readString(d)
    new MusePacketRecipeUpdate(p, recipe)
  }
}

//class MusePacketRecipeUpdate(player: EntityPlayer, recipe: InputStream) extends MusePacket {
class MusePacketRecipeUpdate(player: EntityPlayer, recipe: String) extends MusePacket {
  override val packager = MusePacketRecipeUpdate

  override def write() {
    //writeStream(recipe)
    writeString(recipe)
  }


  @SideOnly(Side.CLIENT)
  override def handleClient(player: EntityClientPlayerMP) {
    try {
      //JSONRecipeList.loadRecipesFromStream(recipe)
      JSONRecipeList.loadRecipesFromString(recipe)
    } catch {
      case e: Exception =>
    }
  }
}