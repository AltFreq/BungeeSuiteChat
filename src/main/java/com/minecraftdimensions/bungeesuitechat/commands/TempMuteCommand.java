package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;
import com.minecraftdimensions.bungeesuitechat.CommandUtil;


public class TempMuteCommand implements CommandExecutor {

	BungeeSuiteChat plugin;
	
	private static final String[] PERMISSION_NODES = { "bungeesuite.chat.tempmute", "bungeesuite.chat.*",
		"bungeesuite.mod", "bungeesuite.admin", "bungeesuite.*" };

	public TempMuteCommand(BungeeSuiteChat bungeeSuiteTeleports){
		plugin = bungeeSuiteTeleports;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!CommandUtil.hasPermission(sender, PERMISSION_NODES)) {
			plugin.utils.getMessage(sender.getName(), "NO_PERMISSION");
			return true;
		}
		if(args.length>1){
			int time;
			try{
			time = Integer.parseInt(args[1]);
			}catch(Exception e){
				return false;
			}
			plugin.utils.tempMutePlayer(sender.getName(),args[0],time);
			return true;
		}
		return false;
	}

}
