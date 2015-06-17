package org.nationsatwar.clowns.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;

import org.nationsatwar.clowns.actions.CustomAction;
import org.nationsatwar.clowns.actions.NothingAction;
import org.nationsatwar.clowns.gui.ChatGUI;
import org.nationsatwar.clowns.gui.OptionButton;
import org.nationsatwar.palette.WorldLocation;
import org.nationsatwar.palette.database.JSONUtil;
import org.nationsatwar.palette.gui.GUIHandler;

public class GenericNPC extends EntityVillager {
	
	private static final String NBT_NAME = "NPC_Name";
	private static final String NBT_WINDOWIDS = "Window_IDs";
	
	private String name;
	
	protected Map<Integer, ChatGUI> dialogueWindows = new HashMap<Integer, ChatGUI>();
	private Map<String, CustomAction> customActions = new HashMap<String, CustomAction>();
	
	public GenericNPC(World worldIn) {
		
		super(worldIn);
		
		name = "Generic NPC";
		setCustomNameTag(name);
	}

	public GenericNPC(World worldIn, String name) {
		
		super(worldIn);
		
		this.name = name;
		setCustomNameTag(name);
		
		dialogueWindows.put(1, new ChatGUI(1));
		initializeCustomActions();
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
			
			tagCompound.setString(NBT_NAME, name);
			List<Integer> windowIDs = new ArrayList<Integer>();
			
			for (int windowID : dialogueWindows.keySet()) {
				
				windowIDs.add(windowID);
				
				ChatGUI chatGUI = dialogueWindows.get(windowID);
				String windowName = getWindowName(windowID);
				
				tagCompound.setString(windowName + "_Dialogue", chatGUI.getDialogue());
				
				int optionIndex = 1;
				tagCompound.setInteger(windowName + "_OptionAmount", chatGUI.getDialogueOptions().size());
				
				for (OptionButton dialogueOption : chatGUI.getDialogueOptions()) {
					
					String optionName = windowName + "_Option_" + optionIndex;
					optionIndex++;
					
					tagCompound.setString(optionName + "_Text", dialogueOption.displayString);
					tagCompound.setString(optionName + "_Action", dialogueOption.getAction());
					tagCompound.setString(optionName + "_Item", dialogueOption.getItemName());
					tagCompound.setInteger(optionName + "_Page", dialogueOption.getPageNumber());
				}
			}
			
			int[] windowIDsArray = new int[windowIDs.size()];
			for(int i = 0; i < windowIDs.size(); i++)
				windowIDsArray[i] = windowIDs.get(i);
			
			tagCompound.setIntArray(NBT_WINDOWIDS, windowIDsArray); 
			
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
		
		initializeCustomActions();
		
		// Load all additional information here
		try {
			
			name = tagCompound.getString(NBT_NAME);
			int[] windowIDs = tagCompound.getIntArray(NBT_WINDOWIDS);
			
			for (int windowID : windowIDs) {
				
				ChatGUI chatGUI = new ChatGUI(windowID);
				dialogueWindows.put(windowID, chatGUI);
				
				String windowName = getWindowName(windowID);
				
				chatGUI.setDialogue(tagCompound.getString(windowName + "_Dialogue"));
				
				int optionAmount = tagCompound.getInteger(windowName + "_OptionAmount");
				
				for (int i = 1; i <= optionAmount; i++) {
					
					String optionName = windowName + "_Option_" + i;
					OptionButton optionButton = chatGUI.addDialogueOption();
					
					optionButton.displayString = tagCompound.getString(optionName + "_Text");
					optionButton.setAction(tagCompound.getString(optionName + "_Action"));
					optionButton.setItemName(tagCompound.getString(optionName + "_Item"));
					optionButton.setPageNumber(tagCompound.getInteger(optionName + "_Page"));
				}
			}
			
		} catch (Throwable throwable) {
			
			CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Loading entity NBT");
			CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being loaded");
			this.addEntityCrashInfo(crashreportcategory);
			throw new ReportedException(crashreport);
		}
	}
	
	private static final String getWindowName(int windowID) {
		
		return "Window_" + windowID;
	}
	
	protected void initializeCustomActions() {

		customActions.put("Nothing", new NothingAction(this));
	}
	
	protected void addCustomAction(String name, CustomAction action) {
		
		customActions.put(name, action);
	}
	
	public String getName() {
		
		return name;
	}
	
	public void setName(String name) {
		
		this.name = name;
		this.setCustomNameTag(name);
	}
	
	public ChatGUI addDialogueWindow() {
		
		int screenID = getNextScreenID();
		ChatGUI newChatGUI = new ChatGUI(screenID);
		
		dialogueWindows.put(screenID, newChatGUI);
		return newChatGUI;
	}
	
	protected ChatGUI addDialogueWindow(int screenID) {
		
		ChatGUI newChatGUI = new ChatGUI(screenID);
		
		dialogueWindows.put(screenID, newChatGUI);
		return newChatGUI;
	}
	
	public int getNextScreenID() {
		
		int nextID = 1;
		
		while (true) {
			
			if (dialogueWindows.containsKey(nextID))
				nextID++;
			else
				return nextID;
		}
	}
	
	public int numberOfDialogueWindows() {
		
		return dialogueWindows.size();
	}
	
	public int getPreviousDialogueWindowID(int currentWindowID) {
		
		int previousWindowID = currentWindowID;
		int closestID = 0;
		
		for (int windowID : dialogueWindows.keySet()) {
			
			int difference = currentWindowID - windowID;
			
			if (difference <= 0)
				continue;
			
			if (closestID == 0) {
				closestID = difference;
				previousWindowID = windowID;
			}
			
			if (closestID != 0 && difference < closestID) {
				closestID = difference;
				previousWindowID = windowID;
			}
		}
		
		return previousWindowID;
	}
	
	public int getNextDialogueWindowID(int currentWindowID) {
		
		int nextWindowID = currentWindowID;
		int closestID = 0;
		
		for (int windowID : dialogueWindows.keySet()) {
			
			int difference = windowID - currentWindowID;
			
			if (difference <= 0)
				continue;
			
			if (closestID == 0) {
				closestID = difference;
				nextWindowID = windowID;
			}
			
			if (closestID != 0 && difference < closestID) {
				closestID = difference;
				nextWindowID = windowID;
			}
		}
		
		return nextWindowID;
	}
	
	public ChatGUI getDialogueWindow(int windowID) {
		
		return dialogueWindows.get(windowID);
	}
	
	public void deleteDialogueWindow(int windowID) {
		
		dialogueWindows.remove(windowID);
	}
	
	public CustomAction getCustomAction(String customActionName) {
		
		return customActions.get(customActionName);
	}
	
	public String getNextCustomActionName(String currentActionName) {
		
		String firstActionName = "";
		boolean foundCurrentActionName = false;
		
		for (String customActionName : customActions.keySet()) {
			
			if (firstActionName.isEmpty())
				firstActionName = customActionName;
			
			if (foundCurrentActionName)
				return customActionName;
			
			if (customActionName.equals(currentActionName))
				foundCurrentActionName = true;
		}
		
		return firstActionName;
	}
	
	public void spawnNPC(WorldLocation location) {
		
		setPositionAndUpdate(location.getPosX(), location.getPosY(), location.getPosZ());
		location.getWorldObject().spawnEntityInWorld(this);
		
		NPCInfo npcInfo = new NPCInfo(this);
		npcInfo = (NPCInfo) JSONUtil.loadObject("clowns", name, npcInfo);
		
		if (npcInfo != null)
			npcInfo.loadNPCInfo(this);
	}
}