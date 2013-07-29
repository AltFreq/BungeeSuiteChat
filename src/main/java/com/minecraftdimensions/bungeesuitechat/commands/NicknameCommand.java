package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;
import com.minecraftdimensions.bungeesuitechat.CommandUtil;

public class NicknameCommand implements CommandExecutor {

	BungeeSuiteChat plugin;

	private static final String[] PERMISSION_NODES = {
			"bungeesuite.chat.nickname","bungeesuite.chat.nickname.*", "bungeesuite.chat.*",
			"bungeesuite.admin", "bungeesuite.*" };
	private static final String[] PERMISSION_ADMIN_NODES = {
		"bungeesuite.chat.nickname.other", "bungeesuite.chat.nickname.*", "bungeesuite.chat.*",
		"bungeesuite.admin", "bungeesuite.*" };

	public NicknameCommand(BungeeSuiteChat bungeeSuiteTeleports) {
		plugin = bungeeSuiteTeleports;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!CommandUtil.hasPermission(sender, PERMISSION_NODES)) {
			plugin.utils.getMessage(sender.getName(), "NO_PERMISSION");
			return true;
		}
		if (args.length == 1) {
			plugin.utils.nicknamePlayer(sender.getName(), sender.getName(),
					args[0]);
			return true;
		}
		if (args.length == 2) {
			if (!CommandUtil.hasPermission(sender, PERMISSION_ADMIN_NODES) && !args[0].equalsIgnoreCase(sender.getName())) {
				plugin.utils.getMessage(sender.getName(), "NO_PERMISSION");
				return true;
			}
			plugin.utils.nicknamePlayer(sender.getName(), args[0], args[1]);
			return true;
		}
		return false;
	}

}
