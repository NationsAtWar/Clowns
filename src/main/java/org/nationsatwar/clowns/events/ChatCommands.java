package org.nationsatwar.clowns.events;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import org.nationsatwar.clowns.entities.GenericNPC;
import org.nationsatwar.clowns.entities.NPCInfo;
import org.nationsatwar.palette.CommandEvent;
import org.nationsatwar.palette.database.JSONUtil;

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
			
			// Spawn specific NPC if specified
			if (args.length > 1) {
				
				String name = combineArgs(args, 1);
				
				if (!(sender instanceof EntityPlayer))
					return;
				
				EntityPlayer player = (EntityPlayer) sender;
				
				GenericNPC npc = new GenericNPC(player.worldObj, "");
				
				npc.setPositionAndUpdate(sender.getPosition().getX(), sender.getPosition().getY(), sender.getPosition().getZ());
				sender.getEntityWorld().spawnEntityInWorld(npc);
				
				NPCInfo npcInfo = new NPCInfo(npc);
				npcInfo = (NPCInfo) JSONUtil.loadObject("clowns", name, npcInfo);
				
				if (npcInfo != null)
					npcInfo.loadNPCInfo(npc);
				
			} else { // Else spawn a Generic NPC
				
				GenericNPC npc = new GenericNPC(sender.getEntityWorld(), "Generic NPC");
				npc.setPositionAndUpdate(sender.getPosition().getX(), sender.getPosition().getY(), sender.getPosition().getZ());
				
				sender.getEntityWorld().spawnEntityInWorld(npc);
			}
		}
	}
}