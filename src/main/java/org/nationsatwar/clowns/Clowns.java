package org.nationsatwar.clowns;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import org.nationsatwar.clowns.entities.GenericNPC;
import org.nationsatwar.clowns.events.ChatCommands;
import org.nationsatwar.clowns.proxy.CommonProxy;

@Mod(modid = Clowns.MODID, 
	name = Clowns.MODNAME, 
	version = Clowns.MODVER)
public class Clowns {

    @Instance(Clowns.MODID)
    public static Clowns instance;

	@SidedProxy(clientSide = Clowns.CLIENT_PROXY_CLASS, serverSide = Clowns.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static final String MODID = "clowns";
	public static final String MODNAME = "Clowns";
	public static final String MODVER = "0.0.1";
	
	public static final String CLIENT_PROXY_CLASS = "org.nationsatwar.clowns.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "org.nationsatwar.clowns.proxy.CommonProxy";
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
		NPCHook.registerNPC(GenericNPC.class, "NPC");
	}
	
	@EventHandler
	public void commandEvent(FMLServerStartingEvent event) {
		
		event.registerServerCommand(new ChatCommands("clown"));
	}
}