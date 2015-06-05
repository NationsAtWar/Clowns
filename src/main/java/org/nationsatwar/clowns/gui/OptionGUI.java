package org.nationsatwar.clowns.gui;

import java.io.IOException;

import org.nationsatwar.palette.RegExHelper;
import org.nationsatwar.palette.gui.GUIButton;
import org.nationsatwar.palette.gui.GUIHandler;
import org.nationsatwar.palette.gui.GUIScreen;
import org.nationsatwar.palette.gui.GUITextField;

public class OptionGUI extends GUIScreen {
	
	private OptionButton optionButton;

	private ChatGUI chatGUI;
	private EditGUI editGUI;
	
	private GUIButton deleteButton;
	private GUIButton returnButton;
	
	private GUIButton actionButton;
	
	private GUITextField textField;
	private GUITextField pageField;
	private GUITextField itemField;
	
	public OptionGUI(ChatGUI chatGUI, EditGUI editGUI) {
		
		this.chatGUI = chatGUI;
		this.editGUI = editGUI;
	}
	
	@Override
	protected void setElements() {
		
		setWindow(width / 2 - 128, 20, 256, 160);
		
		optionButton = editGUI.activeOption;
		
		// Option Text
		addLabel(windowX + 20, windowY + 25, "Option Text");
		textField = addTextField(windowX + 15, windowY + 45, 150, 20, optionButton.displayString);
		
		// Miscellaneous Buttons
		returnButton = addButton(windowX + windowWidth - 70, windowY + 20, 50, 20, "Return");
		deleteButton = addButton(windowX + windowWidth - 70, windowY + 40, 50, 20, "Delete");
		
		// Screen Number
		addLabel(windowX + 20, windowY + 80, "Change to Window (0 Exits)");
		pageField = addTextField(windowX + 160, windowY + 75, 20, 20, optionButton.getPageNumber() + "");
		pageField.setRegEx(RegExHelper.NUMBERS);
		
		// Action Button
		addLabel(windowX + 150, windowY + 115, "Custom Action");
		actionButton = addButton(windowX + 130, windowY + 135, 100, 20, optionButton.getAction());
		
		// Give Item
		addLabel(windowX + 20, windowY + 115, "Give Item");
		itemField = addTextField(windowX + 15, windowY + 135, 80, 20, optionButton.getItemName());
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		
		super.keyTyped(typedChar, keyCode);
		
		optionButton.displayString = textField.getText();
		optionButton.setItemName(itemField.getText());
		
		try {
			
			if (!pageField.getText().isEmpty())
				optionButton.setPageNumber(Integer.parseInt(pageField.getText()));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void buttonClicked(GUIButton button) {
		
		if (button.equals(deleteButton)) {
			
			chatGUI.removeDialogueOption(optionButton);
			GUIHandler.openGUI(editGUI, false);
		}
		
		if (button.equals(returnButton))
			GUIHandler.openGUI(editGUI, false);
		
		if (button.equals(actionButton)) {
			
			String currentAction = optionButton.getAction();
			String nextAction = ChatGUI.activeNPC.getNextCustomActionName(currentAction);
			
			optionButton.setAction(nextAction);
			
			GUIHandler.openGUI(this, false);
		}
	}
}