package org.nationsatwar.clowns.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;

import org.nationsatwar.clowns.entities.GenericNPC;
import org.nationsatwar.palette.gui.GUIButton;
import org.nationsatwar.palette.gui.GUIHandler;
import org.nationsatwar.palette.gui.GUILabel;
import org.nationsatwar.palette.gui.GUIScreen;

public class ChatGUI extends GUIScreen {
	
	public static GenericNPC activeNPC;
	
	private EditGUI editGUI;
	
	protected GUIButton editButton;
	protected GUILabel dialogue;
	
	public ChatGUI() {
		
		editGUI = new EditGUI(this);
	}
	
	@Override
	protected void setElements() {
		
		this.setWindow(width / 2 - 100, 20, 200, 160);
		
		if (activeNPC == null)
			return;
		
		addLabel(windowX + 20, windowY + 20, activeNPC.getName());
		
		dialogue = addLabel(windowX + 20, windowY + 50, "A bunch of dialogue for you to read.");
		
		editButton = addButton(windowX + windowWidth - 70, windowY + 20, 50, 20, "Edit");
	}

	@Override
	protected void buttonClicked(GUIButton button) {
		
		if (button.equals(editButton))
			GUIHandler.openGUI(editGUI, false);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		if (activeNPC != null)
			drawEntityOnScreen(350, height / 2 + 50, 50, mouseX, mouseY + 100, activeNPC);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
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
}