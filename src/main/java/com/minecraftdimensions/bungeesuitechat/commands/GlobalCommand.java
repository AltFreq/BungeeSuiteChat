package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;
import com.minecraftdimensions.bungeesuitechat.CommandUtil;

public class GlobalCommand implements CommandExecutor {
	BungeeSuiteChat plugin;

	public static final String[] PERMISSION_NODES = {
			"bungeesuite.chat.global", "bungeesuite.chat.user",
			"bungeesuite.chat.*", "bungeesuite.mod", "bungeesuite.admin",
			"bungeesuite.*" };

	public GlobalCommand(BungeeSuiteChat bungeeSuiteChat) {
		plugin = bungeeSuiteChat;
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
					"global")) {
				plugin.utils.toggleToChannel(sender.getName(), "global");
				plugin.utils.setPlayersChannel(sender.getName(), "Global");
			}
			return true;
		}
		if (args.length > 0) {
			Player player = (Player) sender;
			String message = "";
			for (String data : args) {
				message += data + " ";
			}

			if (plugin.forcedChan) {
				if (plugin.forcedChannel.equalsIgnoreCase("global")) {
					player.chat(message.substring(0, message.length() - 1));
				} else {
					plugin.playersChannel.put(player, "Global");
					player.chat(message.substring(0, message.length() - 1));
					plugin.playersChannel.put(player, plugin.forcedChannel);
				}
			} else {
				if (!plugin.playersChannel.get(player).equalsIgnoreCase(
						"global")) {
					String prev = plugin.playersChannel.get(player);
					plugin.utils.setPlayersChannel(player.getName(), "Global");
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
