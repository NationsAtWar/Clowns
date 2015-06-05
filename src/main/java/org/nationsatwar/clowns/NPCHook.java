package org.nationsatwar.clowns;

import net.minecraftforge.fml.common.registry.EntityRegistry;

public class NPCHook {
	
	private static int idNumber = 0;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void registerNPC(Class npcClass, String name) {

		EntityRegistry.registerModEntity(npcClass, name, ++idNumber, Clowns.instance, 80, 3, false);
	}
}