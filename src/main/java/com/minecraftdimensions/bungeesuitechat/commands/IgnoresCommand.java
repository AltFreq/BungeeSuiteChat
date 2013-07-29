package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;
import com.minecraftdimensions.bungeesuitechat.CommandUtil;

public class IgnoresCommand implements CommandExecutor {

	BungeeSuiteChat plugin;

	private static final String[] PERMISSION_NODES = {
			"bungeesuite.chat.ignore.list", "bungeesuite.chat.user",
			"bungeesuite.chat.*", "bungeesuite.mod", "bungeesuite.admin",
			"bungeesuite.*" };

	public IgnoresCommand(BungeeSuiteChat bungeeSuiteChat) {
		plugin = bungeeSuiteChat;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!CommandUtil.hasPermission(sender, PERMISSION_NODES)) {
			plugin.utils.getMessage(sender.getName(), "NO_PERMISSION");
			return true;
		}
			plugin.utils.listPlayersIgnores((Player)sender);
			return true;
	}

}
