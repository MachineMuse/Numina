package net.machinemuse.numina.item

import net.machinemuse.numina.basemod.NuminaConfig

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:50 PM, 11/7/13
 */
object ItemIDAllocator {
  def getIDFor(name:String, defaultID:Int) = NuminaConfig.config.getItem(name, defaultID).getInt(defaultID)


}
