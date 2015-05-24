package org.nationsatwar.clowns.gui;

import org.nationsatwar.palette.RegExHelper;
import org.nationsatwar.palette.gui.GUIButton;
import org.nationsatwar.palette.gui.GUILabel;
import org.nationsatwar.palette.gui.GUIScreen;
import org.nationsatwar.palette.gui.GUITextField;

public class RenameNPCGUI extends GUIScreen {
	
	private GUITextField renameTextField;
	private GUIButton confirmButton;
	private GUILabel errorLabel;
	
	@Override
	public void setElements() {

		setWindow((width - 140) / 2, 64, 140, 84);
		
		confirmButton = addButton(windowX + 15, windowY + windowHeight - 20, 50, 20, "Confirm");
		addButton(windowX + windowWidth - 65, windowY + windowHeight - 20, 50, 20, "Cancel");

		addLabel(windowX + (windowWidth / 2), windowY + 15, "Rename NPC:").setCentered(true);
		
		errorLabel = addLabel(windowX + (windowWidth / 2), windowY + 48, "");
		errorLabel.setFontColor(FontColor.RED);
		errorLabel.setCentered(true);
		
		renameTextField = addTextField(windowX + 20, windowY + 30, 100, 20, "");
		renameTextField.setRegEx(RegExHelper.LETTERS_AND_NUMBERS);
		renameTextField.setMaxStringLength(20);
	}
	
	@Override
	protected void buttonClicked(GUIButton button) {
		
		if (button.equals(confirmButton))
			return;
	}
}