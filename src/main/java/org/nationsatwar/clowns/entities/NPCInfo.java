package org.nationsatwar.clowns.entities;

import java.util.HashMap;
import java.util.Map;

import org.nationsatwar.clowns.gui.ChatGUI;

public class NPCInfo {
	
	public String npcName;
	
	public Map<Integer, DialogueInfo> dialogueInfo = new HashMap<Integer, DialogueInfo>();
	
	public NPCInfo(GenericNPC npc) {
		
		npcName = npc.getName();
		
		for (int screenID : npc.dialogueWindows.keySet()) {
			
			ChatGUI dialogueWindow = npc.dialogueWindows.get(screenID);
			
			DialogueInfo dialogue = new DialogueInfo(dialogueWindow);
			dialogueInfo.put(screenID, dialogue);
		}
	}
	
	public void loadNPCInfo(GenericNPC npc) {
		
		npc.setName(npcName);
		
		for (int screenID : dialogueInfo.keySet()) {
			
			if (screenID != 1)
				npc.addDialogueWindow(screenID);
			
			DialogueInfo dialogue = dialogueInfo.get(screenID);
			
			npc.getDialogueWindow(screenID).setDialogue(dialogue.dialogue);
		}
	}
}