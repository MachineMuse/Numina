package net.machinemuse.numina.network

import java.io.DataInputStream
import cpw.mods.fml.common.network.Player
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.entity.EntityClientPlayerMP
import net.machinemuse.numina.command.NicknameMap
import net.machinemuse.numina.scala.OptionCast
import net.minecraft.entity.player.EntityPlayer
import com.google.gson.Gson
import net.machinemuse.numina.recipe.JSONRecipeList

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:40 PM, 12/16/13
 */
object MusePacketRecipeUpdate extends MusePackager{
  def read(d: DataInputStream, p: Player) = {
    val recipe = readString(d)
    new MusePacketRecipeUpdate(p, recipe)
  }
}

class MusePacketRecipeUpdate(player:Player, recipe: String) extends MusePacket(player) {
  override val packager = MusePacketRecipeUpdate

  override def write() {
    writeString(recipe)
  }


  @SideOnly(Side.CLIENT)
  override def handleClient(player: EntityClientPlayerMP) {
    try {
      JSONRecipeList.loadRecipesFromString(recipe)
    } catch {
      case e:Exception =>
    }
  }
}