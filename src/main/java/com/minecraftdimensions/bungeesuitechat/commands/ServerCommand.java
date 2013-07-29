package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;
import com.minecraftdimensions.bungeesuitechat.CommandUtil;


public class ServerCommand implements CommandExecutor {

	BungeeSuiteChat plugin;
	
	private static final String[] PERMISSION_NODES = { "bungeesuite.chat.server", "bungeesuite.chat.user", "bungeesuite.chat.*",
		"bungeesuite.mod", "bungeesuite.admin", "bungeesuite.*" };

	public ServerCommand(BungeeSuiteChat bungeeSuiteTeleports){
		plugin = bungeeSuiteTeleports;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!CommandUtil.hasPermission(sender, PERMISSION_NODES)) {
			plugin.utils.getMessage(sender.getName(), "NO_PERMISSION");
			return true;
		}
		if (args.length == 0) {
			if (plugin.forcedChan) {
				return false;
			}
			if (!plugin.playersChannel.get((Player)sender).equalsIgnoreCase(
					"server")) {
				plugin.utils.toggleToChannel(sender.getName(), "Server");
				plugin.utils.setPlayersChannel(sender.getName(), "Server");
			}
			return true;
		}
		if (args.length > 0) {
			 Player player = (Player) sender;
			String message = "";
			for (String data : args) {
				message += data + " ";
			}
			
			if(plugin.forcedChan){
				if(plugin.forcedChannel.equalsIgnoreCase("server")){
					 player.chat(message.substring(0, message.length() - 1));
				}else{
					 plugin.playersChannel.put(player, "Server");
					 player.chat(message.substring(0, message.length() - 1));
					 plugin.playersChannel.put(player, plugin.forcedChannel);
				}
			}else{
				if(!plugin.playersChannel.get(player).equalsIgnoreCase("server")){
					String prev = plugin.playersChannel.get(player);
					plugin.utils.setPlayersChannel(player.getName(), "Server");
					player.chat(message.substring(0, message.length() - 1));
					plugin.utils.setPlayersChannel(player.getName(), prev);
				}else{
				player.chat(message.substring(0, message.length() - 1));
				}
			}
			return true;
		}
		return false;
	}

}
