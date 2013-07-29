package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;
import com.minecraftdimensions.bungeesuitechat.CommandUtil;

public class ToggleCommand implements CommandExecutor {

	BungeeSuiteChat plugin;

	private static final String[] PERMISSION_NODES = {
			"bungeesuite.chat.toggle", "bungeesuite.chat.user",
			"bungeesuite.chat.*", "bungeesuite.mod", "bungeesuite.admin",
			"bungeesuite.*" };

	public ToggleCommand(BungeeSuiteChat bungeeSuiteTeleports) {
		plugin = bungeeSuiteTeleports;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!CommandUtil.hasPermission(sender, PERMISSION_NODES)) {
			plugin.utils.getMessage(sender.getName(), "NO_PERMISSION");
			return true;
		}
		if(plugin.forcedChan){
			plugin.utils.toggleChannel((Player)sender);
			return true;
		}
		if(args.length==0){
			plugin.utils.toggleChannel((Player)sender);
			return true;
		}
		if(args.length==1){
		plugin.utils.toggleToChannel(sender.getName(), args[0]);
		return true;
		}
		return true;
	}

}
