package net.machinemuse.numina.basemod

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

}
object NuminaProxyServer extends NuminaProxy {

}