package net.machinemuse.numina.render

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.client.event.FOVUpdateEvent
import net.minecraft.entity.ai.attributes.IAttributeInstance
import net.minecraft.entity.SharedMonsterAttributes
import net.machinemuse.numina.basemod.NuminaConfig

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 10:07 PM, 10/17/13
 */
object FOVUpdateEventHandler {
  @SubscribeEvent def onFOVUpdate(e: FOVUpdateEvent) {
    if (NuminaConfig.useFOVFix) {
      val attributeinstance: IAttributeInstance = e.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
      e.newfov = e.fov / ((attributeinstance.getAttributeValue / e.entity.capabilities.getWalkSpeed + 1.0) / 2.0).toFloat
    }
  }

}
