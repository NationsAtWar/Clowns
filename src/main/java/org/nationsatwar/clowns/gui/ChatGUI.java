package org.nationsatwar.clowns.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;

import org.nationsatwar.clowns.actions.CustomAction;
import org.nationsatwar.clowns.entities.GenericNPC;
import org.nationsatwar.palette.ItemHelper;
import org.nationsatwar.palette.gui.GUIButton;
import org.nationsatwar.palette.gui.GUIHandler;
import org.nationsatwar.palette.gui.GUILabel;
import org.nationsatwar.palette.gui.GUIScreen;

public class ChatGUI extends GUIScreen {
	
	public static final int maxDialogueOptions = 4;
	
	public static GenericNPC activeNPC;
	
	private int screenID;
	
	private EditGUI editGUI;
	
	protected String dialogue = "";
	
	private GUIButton editButton;
	private List<OptionButton> dialogueOptions;
	
	public ChatGUI(int screenID) {
		
		this.screenID = screenID;
		
		editGUI = new EditGUI(this);
		dialogueOptions = new ArrayList<OptionButton>();
	}
	
	@Override
	protected void setElements() {
		
		setWindow(width / 2 - 128, 20, 256, 160);
		correctOptionsPos();
		
		if (activeNPC == null)
			return;
		
		addLabel(windowX + 20, windowY + 20, activeNPC.getName());
		
		editButton = addButton(windowX + windowWidth - 70, windowY + 20, 50, 20, "Edit");
		
		// Dialogue Text
		GUILabel dialogueLabel = addLabel(windowX + 20, windowY + 40, dialogue);
		dialogueLabel.setWordWrap(160);
		
		// Dialogue Options
		for (GUIButton dialogueOption : dialogueOptions)
			addButton(dialogueOption);
	}

	@Override
	protected void buttonClicked(GUIButton button) {
		
		if (button.equals(editButton))
			GUIHandler.openGUI(editGUI, false);
		
		for (OptionButton optionButton : dialogueOptions) {
			
			if (button.equals(optionButton)) {
				
				// Changes Screen Window
				ChatGUI chatGUI = ChatGUI.activeNPC.getDialogueWindow(optionButton.getPageNumber());
				
				if (chatGUI != null)
					GUIHandler.openGUI(chatGUI, false);
				else
					player.closeScreen();
				
				// Fires Custom Action
				CustomAction customAction = activeNPC.getCustomAction(optionButton.getAction());
				if (customAction != null)
					customAction.fireAction();
				
				// Gives item
				String itemName = optionButton.getItemName();
				
				if (itemName.isEmpty())
					continue;
				
				Item item = Item.getByNameOrId(itemName);
				ItemHelper.giveItemToPlayer(player, item, 1);
			}
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		if (activeNPC != null)
			drawEntityOnScreen(windowX + windowWidth - 50, height / 2 + 50, 50, mouseX, mouseY + 100, activeNPC);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	private void correctOptionsPos() {
		
		for (OptionButton optionButton : dialogueOptions)
			optionButton.xPosition = windowX + 15;
	}
	
	public OptionButton addDialogueOption() {
		
		int buttonID = dialogueOptions.size();
		
		if (buttonID >= maxDialogueOptions)
			return null;
		
		OptionButton optionButton = new OptionButton(windowX + 15, 90 + (buttonID * 23), 150, 20, "Option " + (buttonID + 1));
		
		dialogueOptions.add(optionButton);
		
		return optionButton;
	}
	
	public List<OptionButton> getDialogueOptions() {
		
		if (windowX != 0)
			correctOptionsPos();
		
		return dialogueOptions;
	}
	
	protected void removeDialogueOption(OptionButton optionButton) {
		
		dialogueOptions.remove(optionButton);
		
		for (int i = 0; i < dialogueOptions.size(); i++)
			if (dialogueOptions.get(i) != null)
				dialogueOptions.get(i).yPosition = (90 + (i * 23));
	}

    /**
     * Draws the entity to the screen. Args: xPos, yPos, scale, mouseX, mouseY, entityLiving
     */
    public static void drawEntityOnScreen(int xPos, int yPos, int scale, float mouseX, float mouseY, EntityLivingBase entity) {
    	
    	mouseX = mouseX * -1 + xPos - 30 * 2;
    	mouseY = mouseY * -1 + yPos + 30;
    	
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) xPos, (float) yPos, 50.0F);
        GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = entity.renderYawOffset;
        float f3 = entity.rotationYaw;
        float f4 = entity.rotationPitch;
        float f5 = entity.prevRotationYawHead;
        float f6 = entity.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(0 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        entity.renderYawOffset = (float)Math.atan((double)(0 / 40.0F)) * 20.0F;
        entity.rotationYaw = (float)Math.atan((double)(Math.max(mouseX, 0) / 40.0F)) * 40.0F;
        entity.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        entity.rotationYawHead = entity.rotationYaw;
        entity.prevRotationYawHead = entity.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        rendermanager.setRenderShadow(true);
        entity.renderYawOffset = f2;
        entity.rotationYaw = f3;
        entity.rotationPitch = f4;
        entity.prevRotationYawHead = f5;
        entity.rotationYawHead = f6;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    public String getDialogue() {
    	
    	return dialogue;
    }
    
    public void setDialogue(String dialogue) {
    	
    	this.dialogue = dialogue;
    }
    
    public int getScreenID() {
    	
    	return screenID;
    }
    
    public EditGUI getEditGUI() {
    	
    	return editGUI;
    }
}