package net.machinemuse.numina.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.machinemuse.numina.basemod.NuminaConfig;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraftforge.client.event.FOVUpdateEvent;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 10:07 PM, 10/17/13
 * <p>
 * Ported to Java by lehjr on 10/10/16.
 */
public class FOVUpdateEventHandler {

	@SubscribeEvent
	public void onFOVUpdate(FOVUpdateEvent e) {
		if (NuminaConfig.useFOVFix()) {
			IAttributeInstance attributeinstance = e.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
			e.newfov = (float) (e.fov / ((attributeinstance.getAttributeValue() / e.entity.capabilities.getWalkSpeed() + 1.0) / 2.0));
			if (e.entity.isSprinting()) {
				e.newfov += 0.15F;
			}
		}
	}

}


