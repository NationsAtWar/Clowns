package org.nationsatwar.clowns.events;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import org.nationsatwar.clowns.entities.GenericNPC;

public class ChatCommands implements ICommand {
	
	private List<String> aliases;
	
	public ChatCommands() {
		
		aliases = new ArrayList<String>();
		aliases.add("clown");
	}

	@Override
	public int compareTo(Object o) {
		
		return 0;
	}

	@Override
	public String getName() {
		
		return "clown";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		
		return "clown <command>";
	}

	@Override
	public List<String> getAliases() {
		
		return aliases;
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
			
			GenericNPC npc = new GenericNPC(sender.getEntityWorld(), "Generic NPC2");
			npc.setPositionAndUpdate(sender.getPosition().getX(), sender.getPosition().getY(), sender.getPosition().getZ());
			
			sender.getEntityWorld().spawnEntityInWorld(npc);
		}
	}
	
	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		
		return true;
	}
	
	@Override
	public List<?> addTabCompletionOptions(ICommandSender sender, String[] args,
			BlockPos pos) {
		
		return null;
	}
	
	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		
		return false;
	}
}