package org.nationsatwar.clowns.gui;

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

		addButton = addButton(windowX + 20, windowY + 20, 80, 20, "Add Window");
		deleteButton = addButton(windowX + 20, windowY + 20, 80, 20, "Delete Window");
		
		// Edit Dialogue
		addLabel(windowX + 20, windowY + 20, "Dialogue");
		dialogue = addTextField(windowX + 20, windowY + 45, 150, 20, "");
		dialogue.setMaxStringLength(10000);
		dialogue.setText(chatGUI.dialogue);
		dialogue.setCursorPositionEnd();
		
		// Edit Dialogue Options
		for (GUIButton dialogueOption : chatGUI.getDialogueOptions())
			addButton(dialogueOption);
		
		addDialogueOption = createDialogueOption("New Dialogue Option");
	}
	
	@Override
	protected void buttonClicked(GUIButton button) {
		
		if (button.equals(returnButton))
			GUIHandler.openGUI(chatGUI, false);
		
		if (button.equals(nameButton))
			GUIHandler.openGUI(chatGUI, false);
		
		if (button.equals(addButton))
			GUIHandler.openGUI(chatGUI, false);
		
		if (button.equals(deleteButton))
			GUIHandler.openGUI(chatGUI, false);
		
		if (button.equals(addDialogueOption)) {
			
			chatGUI.addDialogueOption();
			super.initGui();
		}
		
		for (OptionButton optionButton : chatGUI.getDialogueOptions()) {
			
			if (button.equals(optionButton)) {
				
				activeOption = optionButton;
				GUIHandler.openGUI(new OptionGUI(this), false);
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
			ChatGUI.drawEntityOnScreen(windowX + windowWidth - 50, height / 2 + 50, 50, mouseX, mouseY + 100, ChatGUI.activeNPC);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	protected GUIButton createDialogueOption(String text) {
		
		int buttonID = chatGUI.getDialogueOptions().size();
		
		if (buttonID >= ChatGUI.maxDialogueOptions)
			return null;
		
		GUIButton dialogueOption = addButton(windowX + 20, 90 + (buttonID * 23), 150, 20, text);
		
		return dialogueOption;
	}
}