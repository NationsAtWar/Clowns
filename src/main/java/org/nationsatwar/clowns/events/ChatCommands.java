package org.nationsatwar.clowns.events;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import org.nationsatwar.clowns.entities.GenericNPC;
import org.nationsatwar.palette.CommandEvent;
import org.nationsatwar.palette.WorldLocation;

public class ChatCommands extends CommandEvent {
	
	public ChatCommands(String command) {
		
		super(command);
	}
	
	@Override
	public void execute(ICommandSender sender, String[] args)
			throws CommandException {
		
		if (args.length == 0) {
			
			sender.addChatMessage(new ChatComponentText("Enter a valid command."));
			return;
		}
		
		String command = args[0].toLowerCase();
		
		if (command.equals("spawn")) {
			
			if (!(sender instanceof EntityPlayer))
				return;
			
			EntityPlayer player = (EntityPlayer) sender;
			
			String npcName = (args.length > 1 ? combineArgs(args, 1) : "Generic NPC");
			GenericNPC npc = new GenericNPC(player.worldObj, npcName);
			
			WorldLocation location = new WorldLocation(player);
			npc.spawnNPC(location);
		}
	}
}