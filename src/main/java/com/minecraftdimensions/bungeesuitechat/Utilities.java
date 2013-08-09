package com.minecraftdimensions.bungeesuitechat;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;
import com.minecraftdimensions.bungeesuitechat.managers.PrefixSuffixManager;
import com.minecraftdimensions.bungeesuitechat.objects.BSPlayer;
import com.minecraftdimensions.bungeesuitechat.objects.Channel;
import com.minecraftdimensions.bungeesuitechat.objects.ServerData;
import com.minecraftdimensions.bungeesuitechat.tasks.PluginMessageTask;


public class Utilities {

	public static String ReplaceVariables(Player player, String format) {
		BSPlayer p = PlayerManager.getPlayer(player);
		Channel c = p.getChannel();
		format = format.replace("{channel}", c.getName());
		format = format.replace("{player}", p.getDisplayingName());
		format = format.replace("{shortname}", ServerData.getServerShortName());
		format = format.replace("{world}", player.getWorld().getName());
		format = format.replace("{server}", ServerData.getServerName());
		String prefix = "";
		String suffix = "";
		String group = "";
		if(BungeeSuiteChat.usingVault){
			group = BungeeSuiteChat.CHAT.getPrimaryGroup(player);
			if (group == null) {
				group = "";
			}
			if (BungeeSuiteChat.CHAT.getPlayerPrefix(player) != null)
				prefix = BungeeSuiteChat.CHAT.getPlayerPrefix(player);
			else if (BungeeSuiteChat.CHAT.getGroupPrefix(player.getWorld(), group) != null) {
				prefix = BungeeSuiteChat.CHAT.getGroupPrefix(player.getWorld(), group);
			}
			if (BungeeSuiteChat.CHAT.getPlayerSuffix(player) != null)
				suffix = BungeeSuiteChat.CHAT.getPlayerSuffix(player);
			else if (BungeeSuiteChat.CHAT.getGroupSuffix(player.getWorld(), group) != null) {
				suffix = BungeeSuiteChat.CHAT.getGroupSuffix(player.getWorld(), group);
			}
		}
		if(PrefixSuffixManager.playerHasPrefix(player)){
		group = PrefixSuffixManager.getPlayersPrefixGroup(player);
		prefix =PrefixSuffixManager.getPlayersPrefix(player);
		}
		if(PrefixSuffixManager.playerHasSuffix(player)){
		suffix = PrefixSuffixManager.getPlayersSuffix(player);
		}
		format = format.replace("{group}", group);
		format = format.replace("{suffix}", suffix);
		format = format.replace("{prefix}", prefix);
		format = format.replace("{message}", "%2$s");
		return colorize(format);
	}

	public static String colorize(String input) {
		return ChatColor.translateAlternateColorCodes('&', input);
	}

	public static void logChat(String chat) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("LogChat");
			out.writeUTF(chat);
		} catch (IOException s) {
			s.printStackTrace();
		}
		new PluginMessageTask(b).runTaskAsynchronously(BungeeSuiteChat.instance);	
	}

	public static String SetMessage(Player player, String message) {
		if(player.hasPermission("bungeesuite.chat.admincolor")){
			message = colorize(ServerData.getAdminColor())+message;
		}
		if(player.hasPermission("bungeesuite.chat.color")){
			message = colorize(message);
		}
		return message;
	}



	
}
