package com.minecraftdimensions.bungeesuitechat;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class CommandUtil {

	public static boolean hasPermission(CommandSender sender, String[] permissions) {
		for (String perm : permissions)
			if (sender.hasPermission(perm))
				return true;
		
		return false;
	}
	public static boolean hasPermission(String sender, String[] permissions) {
		for (String perm : permissions)
			if (Bukkit.getPlayer(sender)!=null && Bukkit.getPlayer(sender).hasPermission(perm))
				return true;
		
		return false;
	}
}
