package org.nationsatwar.clowns.gui;

import org.nationsatwar.palette.gui.GUIButton;
import org.nationsatwar.palette.gui.GUIScreen;

public class EditGUI extends GUIScreen {
	
	private ChatGUI chatGUI;
	
	private GUIButton dialogue;
	//private GUITextField editDialogue;
	
	public EditGUI(ChatGUI chatGUI) {
		
		this.chatGUI = chatGUI;
	}

	@Override
	protected void setElements() {
		
		this.setWindow(width / 2 - 100, 20, 200, 160);
		
		dialogue = addButton(windowX + 20, windowY + 50, 100, 20, chatGUI.dialogue.getText());
		
		addLabel(20, 20, "Heey");
	}

	@Override
	protected void buttonClicked(GUIButton button) {
		
		if (button.equals(dialogue))
			return;
	}
}