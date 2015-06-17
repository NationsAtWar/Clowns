package org.nationsatwar.clowns.actions;

import net.minecraft.entity.player.EntityPlayer;

import org.nationsatwar.clowns.entities.GenericNPC;

public abstract class CustomAction {
	
	GenericNPC npc;
	
	public CustomAction(GenericNPC npc) {
		
		this.npc = npc;
	}
	
	public abstract void fireAction(EntityPlayer player);
}