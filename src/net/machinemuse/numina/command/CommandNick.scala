package net.machinemuse.numina.command

import net.minecraft.command.{ICommandSender, CommandBase}
import net.machinemuse.numina.general.{Localization, MuseLogger}
import net.minecraft.entity.player.{EntityPlayerMP, EntityPlayer}
import net.machinemuse.numina.scala.{OptionCast, MuseRegistry}
import net.minecraft.util.ChatMessageComponent

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 1:43 PM, 9/3/13
 */
object CommandNick extends CommandBase {
  override def getCommandName: String = "nick"

  val nameToNicknameMap: MuseRegistry[String] = new MuseRegistry[String]

  override def getCommandUsage(icommandsender: ICommandSender): String = "/nick <new nickname>"

  override def processCommand(sender: ICommandSender, args: Array[String]) {
    if (args.length > 0 && validateName(args(0)))
      sender match {
        case player: EntityPlayer => {
          nameToNicknameMap.removeName(player.username)
          nameToNicknameMap.putName(player.username, args(0))
          OptionCast[EntityPlayerMP](player) map {
            p =>
              p.mcServer.getConfigurationManager.sendChatMsg(ChatMessageComponent.createFromText(player.username + " is now known as " + args(0)))
          }
        }
        case _ => MuseLogger.logError(sender.getCommandSenderName + " not a player")
      }
  }

  override def canCommandSenderUseCommand(par1ICommandSender: ICommandSender): Boolean = true

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
