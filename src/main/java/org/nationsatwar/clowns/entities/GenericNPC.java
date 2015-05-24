package org.nationsatwar.clowns.entities;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;

import org.nationsatwar.clowns.gui.ChatGUI;
import org.nationsatwar.palette.gui.GUIHandler;

public class GenericNPC extends EntityVillager {
	
	private String name;
	
	private Map<Integer, ChatGUI> dialogueWindows = new HashMap<Integer, ChatGUI>();

	public GenericNPC(World worldIn) {
		
		super(worldIn);
		
		name = "Generic NPC";
		setCustomNameTag(name);
	}

	public GenericNPC(World worldIn, String name) {
		
		super(worldIn);
		
		this.name = name;
		setCustomNameTag(name);
		
		dialogueWindows.put(1, new ChatGUI());
	}
	
	@Override
	public boolean interact(EntityPlayer player) {
		
		ChatGUI.activeNPC = this;
		GUIHandler.openGUI(dialogueWindows.get(1), false);
		return true;
	}
	
	@Override
	protected String getLivingSound() {
		
		return "step.snow";
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		
		super.writeToNBT(tagCompound);
		
		// Save all additional information here
		try {
			
			tagCompound.setString("NPC_Name", name);
			tagCompound.setString("dialogue", dialogueWindows.get(1).getDialogue());
			
		} catch (Throwable throwable) {
			
			CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Saving entity NBT");
			CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being saved");
			this.addEntityCrashInfo(crashreportcategory);
			throw new ReportedException(crashreport);
        }
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		
		super.readFromNBT(tagCompound);
		
		dialogueWindows.put(1, new ChatGUI());
		
		// Load all additional information here
		try {
			
			name = tagCompound.getString("NPC_Name");
			dialogueWindows.get(1).setDialogue(tagCompound.getString("dialogue"));
			
		} catch (Throwable throwable) {
			
			CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Loading entity NBT");
			CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being loaded");
			this.addEntityCrashInfo(crashreportcategory);
			throw new ReportedException(crashreport);
		}
	}
	
	public String getName() {
		
		return name;
	}
}