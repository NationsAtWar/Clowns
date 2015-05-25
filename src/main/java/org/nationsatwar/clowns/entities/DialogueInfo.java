package org.nationsatwar.clowns.entities;

import org.nationsatwar.clowns.gui.ChatGUI;

public class DialogueInfo {
	
	public String dialogue;
	
	public DialogueInfo(ChatGUI chatGUI) {
		
		dialogue = chatGUI.getDialogue();
	}
	
	public void loadDialogueInfo(ChatGUI chatGUI) {
		
		chatGUI.setDialogue(dialogue);
	}
}