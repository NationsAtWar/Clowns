package org.nationsatwar.clowns.gui;

import org.nationsatwar.palette.gui.GUIButton;

public class OptionButton extends GUIButton {
	
	private String itemName = "";
	private String action = "";
	
	private int pageNumber = 0;

	public OptionButton(int buttonId, int x, int y, int widthIn,
			int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}
	
	public OptionButton(int x, int y, int widthIn, int heightIn, String buttonText) {
		super(0, x, y, widthIn, heightIn, buttonText);
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}
	
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
}