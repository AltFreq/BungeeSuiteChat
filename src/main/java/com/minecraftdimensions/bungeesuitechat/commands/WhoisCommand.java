package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;
import com.minecraftdimensions.bungeesuitechat.CommandUtil;


public class WhoisCommand implements CommandExecutor {

	BungeeSuiteChat plugin;
	
	private static final String[] PERMISSION_NODES = { "bungeesuite.chat.whois", "bungeesuite.chat.*",
		"bungeesuite.mod", "bungeesuite.admin", "bungeesuite.*" };

	public WhoisCommand(BungeeSuiteChat bungeeSuiteChat){
		plugin = bungeeSuiteChat;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!CommandUtil.hasPermission(sender, PERMISSION_NODES)) {
			plugin.utils.getMessage(sender.getName(), "NO_PERMISSION");
			return true;
		}
		if(args.length>0){
			plugin.utils.whoisPlayer(sender.getName(),args[0]);
			return true;
		}
		return false;
	}

}
