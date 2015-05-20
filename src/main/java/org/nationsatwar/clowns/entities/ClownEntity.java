package org.nationsatwar.clowns.entities;

import org.nationsatwar.clowns.gui.ChatGUI;
import org.nationsatwar.palette.gui.GUIHandler;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ClownEntity extends EntityVillager {

	public ClownEntity(World worldIn) {
		
		super(worldIn);
	}
	
	@Override
	public boolean interact(EntityPlayer player) {
		
		GUIHandler.openGUI(new ChatGUI());
		return true;
	}
}