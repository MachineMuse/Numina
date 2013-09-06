package net.machinemuse.numina.basemod

import net.minecraftforge.common.MinecraftForge
import net.machinemuse.numina.mouse.MouseEventHandler
import net.machinemuse.numina.render.RenderGameOverlayEventHandler

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:57 AM, 9/3/13
 */
trait NuminaProxy {
  def PreInit() = {}

  def Init() = {}

  def PostInit() = {}
}

object NuminaProxyClient extends NuminaProxy {

  override def Init() = {
    MinecraftForge.EVENT_BUS.register(MouseEventHandler)
    MinecraftForge.EVENT_BUS.register(RenderGameOverlayEventHandler)
  }
}

object NuminaProxyServer extends NuminaProxy {

}