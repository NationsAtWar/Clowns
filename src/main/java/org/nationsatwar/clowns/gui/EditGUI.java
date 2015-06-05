package org.nationsatwar.clowns.gui;

import org.nationsatwar.clowns.entities.GenericNPC;
import org.nationsatwar.palette.gui.GUIButton;
import org.nationsatwar.palette.gui.GUIHandler;
import org.nationsatwar.palette.gui.GUIScreen;
import org.nationsatwar.palette.gui.GUITextField;

public class EditGUI extends GUIScreen {
	
	private ChatGUI chatGUI;
	
	private GUIButton returnButton;
	private GUIButton nameButton;

	private GUIButton addButton;
	private GUIButton deleteButton;

	private GUIButton prevButton;
	private GUIButton nextButton;
	
	private GUIButton addDialogueOption;
	private GUITextField dialogue;
	
	protected OptionButton activeOption;
	
	public EditGUI(ChatGUI chatGUI) {
		
		this.chatGUI = chatGUI;
	}
	
	@Override
	protected void setElements() {
		
		setWindow(width / 2 - 128, 20, 256, 160);
		
		returnButton = addButton(windowX + windowWidth - 70, windowY + 20, 50, 20, "Return");
		nameButton = addButton(windowX + 90, windowY + 20, 80, 20, ChatGUI.activeNPC.getName());

		addButton = addButton(windowX + windowWidth - 90, windowY + 116, 78, 20, "Add Screen");
		if (chatGUI.getScreenID() > 1)
			deleteButton = addButton(windowX + windowWidth - 90, windowY + 139, 78, 20, "Delete Screen");
		
		if (chatGUI.getScreenID() > 1)
			prevButton = addButton(windowX + windowWidth - 90, windowY + 94, 20, 20, "<");
		if (ChatGUI.activeNPC.getNextDialogueWindowID(chatGUI.getScreenID()) > chatGUI.getScreenID())
			nextButton = addButton(windowX + windowWidth - 32, windowY + 94, 20, 20, ">");
		
		// Edit Dialogue
		addLabel(windowX + 20, windowY + 20, "Screen: " + chatGUI.getScreenID());
		dialogue = addTextField(windowX + 15, windowY + 45, 150, 20, "");
		dialogue.setMaxStringLength(10000);
		dialogue.setText(chatGUI.dialogue);
		dialogue.setCursorPositionEnd();
		
		// Edit Dialogue Options
		for (GUIButton dialogueOption : chatGUI.getDialogueOptions()) {
			
			addButton(dialogueOption);
			dialogueOption.xPosition = windowX + 15;
		}
		
		addDialogueOption = createDialogueOption("New Dialogue Option");
	}
	
	@Override
	protected void buttonClicked(GUIButton button) {
		
		if (button.equals(returnButton))
			GUIHandler.openGUI(chatGUI, false);
		
		if (button.equals(nameButton))
			GUIHandler.openGUI(new RenameNPCGUI(this), true);
		
		if (button.equals(addButton)) {
			
			ChatGUI newWindow = ChatGUI.activeNPC.addDialogueWindow();
			GUIHandler.openGUI(newWindow, false);
			GUIHandler.openGUI(newWindow.getEditGUI(), false);
		}
		
		if (button.equals(deleteButton)) {
			
			if (chatGUI.getScreenID() == 1)
				return;
			
			ChatGUI.activeNPC.deleteDialogueWindow(chatGUI.getScreenID());
			
			GenericNPC npc = ChatGUI.activeNPC;
			int prevID = npc.getPreviousDialogueWindowID(chatGUI.getScreenID());
			ChatGUI chatGUI = npc.getDialogueWindow(prevID);
			
			if (chatGUI != null)
				GUIHandler.openGUI(chatGUI.getEditGUI(), false);
		}
		
		if (button.equals(prevButton)) {
			
			GenericNPC npc = ChatGUI.activeNPC;
			int prevID = npc.getPreviousDialogueWindowID(chatGUI.getScreenID());
			ChatGUI chatGUI = npc.getDialogueWindow(prevID);
			
			if (chatGUI != null)
				GUIHandler.openGUI(chatGUI.getEditGUI(), false);
		}
		
		if (button.equals(nextButton)) {
			
			GenericNPC npc = ChatGUI.activeNPC;
			int nextID = npc.getNextDialogueWindowID(chatGUI.getScreenID());
			ChatGUI chatGUI = npc.getDialogueWindow(nextID);
			
			if (chatGUI != null)
				GUIHandler.openGUI(chatGUI.getEditGUI(), false);
		}
		
		if (button.equals(addDialogueOption)) {
			
			chatGUI.addDialogueOption();
			player.closeScreen();
			GUIHandler.openGUI(this, false);
		}
		
		for (OptionButton optionButton : chatGUI.getDialogueOptions()) {
			
			if (button.equals(optionButton)) {
				
				activeOption = optionButton;
				GUIHandler.openGUI(new OptionGUI(chatGUI, this), false);
			}
		}
		
		this.onGuiClosed();
	}
	
	@Override
	public void onGuiClosed() {
		
		chatGUI.dialogue = dialogue.getText();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		if (ChatGUI.activeNPC != null)
			ChatGUI.drawEntityOnScreen(windowX + windowWidth - 50, windowY + 110, 30, mouseX, mouseY + 100, ChatGUI.activeNPC);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	protected GUIButton createDialogueOption(String text) {
		
		int buttonID = chatGUI.getDialogueOptions().size();
		
		if (buttonID >= ChatGUI.maxDialogueOptions)
			return null;
		
		GUIButton dialogueOption = addButton(windowX + 15, 90 + (buttonID * 23), 150, 20, text);
		
		return dialogueOption;
	}
}