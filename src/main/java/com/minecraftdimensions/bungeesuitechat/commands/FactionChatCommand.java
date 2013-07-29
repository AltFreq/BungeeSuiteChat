package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;
import com.minecraftdimensions.bungeesuitechat.CommandUtil;

public class FactionChatCommand implements CommandExecutor {
	BungeeSuiteChat plugin;

	public static final String[] PERMISSION_NODES = {
			"bungeesuite.chat.faction", "bungeesuite.chat.user",
			"bungeesuite.chat.*", "bungeesuite.mod", "bungeesuite.admin",
			"bungeesuite.*" };

	public FactionChatCommand(BungeeSuiteChat bungeeSuiteChat) {
		plugin = bungeeSuiteChat;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!CommandUtil.hasPermission(sender, PERMISSION_NODES)) {
			plugin.utils.getMessage(sender.getName(), "NO_PERMISSION");
			return true;
		}
		if (plugin.playersChannel.get((Player)sender).equalsIgnoreCase(
				"FactionAlly")) {
			plugin.utils.setPlayersChannel(sender.getName(), "Faction");
			plugin.utils.getMessage(sender.getName(),"FACTION_TOGGLE");
		}else if(plugin.playersChannel.get((Player)sender).equalsIgnoreCase(
				"Faction")){
			plugin.utils.toggleChannel((Player)sender);
		}else{
			plugin.utils.setPlayersChannel(sender.getName(), "FactionAlly");
			plugin.utils.getMessage(sender.getName(),"FACTION_ALLY_TOGGLE");
		}
		return true;
		
	}
}
