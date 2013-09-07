package net.machinemuse.numina.command

import net.minecraft.command.{ICommandSender, CommandBase}
import net.machinemuse.numina.general.MuseLogger
import net.minecraft.entity.player.{EntityPlayerMP, EntityPlayer}
import net.machinemuse.numina.scala.OptionCast
import net.minecraft.util.ChatMessageComponent
import cpw.mods.fml.common.network.{Player, PacketDispatcher}
import net.machinemuse.numina.network.MusePacketNameChangeRequest

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 1:43 PM, 9/3/13
 */
object CommandNick extends CommandBase {
  override def getCommandName: String = "nick"

  override def getCommandUsage(icommandsender: ICommandSender): String = "/nick <new nickname>"

  override def processCommand(sender: ICommandSender, args: Array[String]) {
    if (args.length > 0 && validateName(args(0)))
      sender match {
        case player: EntityPlayer => {
          NicknameMap.removeName(player.username)
          NicknameMap.putName(player.username, args(0))
          OptionCast[EntityPlayerMP](player) map {
            p =>
              p.mcServer.getConfigurationManager.sendChatMsg(ChatMessageComponent.createFromText(p.username + " is now known as " + args(0)))
              PacketDispatcher.sendPacketToAllPlayers(new MusePacketNameChangeRequest(p.asInstanceOf[Player], p.username, args(0)).getPacket131)
              p.refreshDisplayName()
          }
        }
        case _ => MuseLogger.logError(sender.getCommandSenderName + " not a player")
      }
  }

  override def canCommandSenderUseCommand(par1ICommandSender: ICommandSender): Boolean = true

  def validateName(s: String) = {
    s.size > 2 && s.size < 16
  }

}
