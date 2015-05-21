package org.nationsatwar.clowns.entities;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.nationsatwar.clowns.gui.ChatGUI;
import org.nationsatwar.palette.gui.GUIHandler;

public class GenericNPC extends EntityVillager {
	
	private String name;
	
	Map<Integer, ChatGUI> dialogueWindows = new HashMap<Integer, ChatGUI>();

	public GenericNPC(World worldIn) {
		
		super(worldIn);
		
		name = "Villager";
		setCustomNameTag("Villager");
		
		dialogueWindows.put(0, new ChatGUI());
	}
	
	@Override
	public boolean interact(EntityPlayer player) {
		
		System.out.println("Interact");
		ChatGUI.activeNPC = this;
		GUIHandler.openGUI(dialogueWindows.get(0), false);
		return true;
	}
	
	public String getName() {
		
		return name;
	}
}