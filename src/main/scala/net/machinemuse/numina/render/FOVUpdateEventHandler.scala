package net.machinemuse.numina.render

import net.minecraftforge.client.event.FOVUpdateEvent
import net.minecraft.entity.ai.attributes.IAttributeInstance
import net.minecraft.entity.SharedMonsterAttributes
import net.machinemuse.numina.basemod.NuminaConfig
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 10:07 PM, 10/17/13
 */
object FOVUpdateEventHandler {
  @SubscribeEvent def onFOVUpdate(e: FOVUpdateEvent) {
    if (NuminaConfig.useFOVFix) {
      val attributeinstance: IAttributeInstance = e.getEntity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
      e.setNewfov(e.getFov / ((attributeinstance.getAttributeValue / e.getEntity.capabilities.getWalkSpeed + 1.0) / 2.0).toFloat)
    }
  }
}
