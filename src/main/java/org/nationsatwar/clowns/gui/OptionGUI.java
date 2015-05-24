package org.nationsatwar.clowns.gui;

import java.io.IOException;

import org.nationsatwar.palette.RegExHelper;
import org.nationsatwar.palette.gui.GUIButton;
import org.nationsatwar.palette.gui.GUIHandler;
import org.nationsatwar.palette.gui.GUIScreen;
import org.nationsatwar.palette.gui.GUITextField;

public class OptionGUI extends GUIScreen {
	
	private OptionButton optionButton;
	
	private EditGUI editGUI;
	
	private GUIButton deleteButton;
	private GUIButton returnButton;
	
	private GUIButton actionButton;
	private GUITextField actionField;
	
	private GUITextField textField;
	private GUITextField pageField;
	private GUITextField itemField;
	
	public OptionGUI(EditGUI editGUI) {
		
		this.editGUI = editGUI;
	}
	
	@Override
	protected void setElements() {
		
		setWindow(width / 2 - 128, 20, 256, 160);
		
		optionButton = editGUI.activeOption;
		
		deleteButton = addButton(windowX + 20, windowY + 20, 50, 20, "Delete");
		returnButton = addButton(windowX + 20, windowY + 50, 50, 20, "Return");
		
		actionButton = addButton(windowX + 20, windowY + 80, 50, 20, "Action");
		actionField = addTextField(windowX + 90, windowY + 80, 50, 20, "");
		
		textField = addTextField(windowX + 20, windowY + 110, 50, 20, optionButton.displayString);
		pageField = addTextField(windowX + 20, windowY + 140, 50, 20, "0");
		pageField.setRegEx(RegExHelper.NUMBERS);
		itemField = addTextField(windowX + 20, windowY + 170, 50, 20, "Item");
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		
		super.keyTyped(typedChar, keyCode);
		
		optionButton.displayString = textField.getText();
		
		if (actionField.getText().equals(""))
			return;
		
		if (pageField.getText().equals(""))
			return;
		
		if (itemField.getText().equals(""))
			return;
	}
	
	@Override
	protected void buttonClicked(GUIButton button) {
		
		if (button.equals(deleteButton))
			return;
		
		if (button.equals(returnButton))
			GUIHandler.openGUI(editGUI, false);
		
		if (button.equals(actionButton))
			return;
	}
}