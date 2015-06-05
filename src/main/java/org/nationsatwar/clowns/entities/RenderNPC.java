package org.nationsatwar.clowns.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.nationsatwar.clowns.Clowns;

public class RenderNPC extends RenderLiving {
	
	// This class is incomplete, but is the basis for custom rendering
	// Just ignore me for right now
	
	private static final ResourceLocation npcTextures = new ResourceLocation(Clowns.MODID + 
			":textures/entity/BlahBlah.png");

	public RenderNPC(RenderManager renderManager, ModelBase modelBase,
			float shadowSize) {
		super(renderManager, modelBase, shadowSize);
	}

	protected ResourceLocation getEntityTexture(GenericNPC entity) {
		
		return npcTextures;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		
		return getEntityTexture((GenericNPC) entity);
	}
}