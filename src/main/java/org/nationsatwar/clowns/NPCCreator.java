package org.nationsatwar.clowns;

import net.minecraftforge.fml.common.registry.EntityRegistry;

public class NPCCreator {
	
	private static int idNumber = 0;
	
	@SuppressWarnings("rawtypes")
	public static void addEntity(Class entityClass, String name) {
		
		registerEntity(entityClass, name);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void registerEntity(Class entityClass, String name) {

		EntityRegistry.registerModEntity(entityClass, name, ++idNumber, Clowns.instance, 80, 3, false);
	}
}