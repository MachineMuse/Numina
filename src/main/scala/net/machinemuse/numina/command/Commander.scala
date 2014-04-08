package net.machinemuse.numina.command

import net.minecraft.server.MinecraftServer
import net.minecraft.command.ServerCommandManager
import net.minecraftforge.common.MinecraftForge

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 1:40 PM, 9/3/13
 */
object Commander {
  val commandManager: ServerCommandManager = MinecraftServer.getServer.getCommandManager.asInstanceOf[ServerCommandManager]

  def init() {
    commandManager.registerCommand(CommandNick)
    MinecraftForge.EVENT_BUS.register(NicknameMap)
  }
}
