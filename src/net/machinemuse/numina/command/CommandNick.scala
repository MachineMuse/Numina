package net.machinemuse.numina.command

import net.minecraft.command.{ICommandSender, CommandBase}
import net.machinemuse.numina.general.{MuseLogger}
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.event.ForgeSubscribe
import net.minecraftforge.event.entity.player.PlayerEvent
import net.machinemuse.numina.scala.MuseRegistry

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 1:43 PM, 9/3/13
 */
object CommandNick extends CommandBase {
  def getCommandName: String = "nick"

  val nameToNicknameMap: MuseRegistry[String] = new MuseRegistry[String]

  def getCommandUsage(icommandsender: ICommandSender): String = "/nick <new nickname>"

  def processCommand(sender: ICommandSender, args: Array[String]) {
    if (args.length > 0 && validateName(args(0)))
      sender match {
        case player: EntityPlayer => {
          nameToNicknameMap.putName(player.username, args(0))
        }
        case _ => MuseLogger.logError(sender.getCommandSenderName + " not a player")
      }
  }

  def validateName(s: String) = {
    s.size > 2 && s.size < 16
  }

//  @ForgeSubscribe def onNameFormat(e: PlayerEvent.NameFormat) {
//    nameToNicknameMap.get(e.username) map {
//      dn:String =>
//        e.displayname = dn
//    }
//  }
}
