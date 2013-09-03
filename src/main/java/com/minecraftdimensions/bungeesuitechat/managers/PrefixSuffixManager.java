package com.minecraftdimensions.bungeesuitechat.managers;

import java.util.HashMap;

import org.bukkit.entity.Player;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;

public class PrefixSuffixManager {
	
	public static HashMap<String, String> prefixes = new HashMap<String, String>();
	public static HashMap<String, String> suffixes = new HashMap<String, String>();
	public static boolean prefix;
	public static boolean suffix;
	


	public static void reload() {
		prefixes.clear();
		suffixes.clear();
	}


	public static String getPlayersPermGroup(Player player) {
		String group = BungeeSuiteChat.CHAT.getPrimaryGroup(player);
		if(group == null){
			return "";
		}else{
			return group;
		}

	}


	public static String getPlayersPermSuffix(Player player) {
		String suffix = BungeeSuiteChat.CHAT.getPlayerSuffix(player);
		if(suffix == null){
			return "";
		}
		return suffix;
	}


	public static String getPlayersPermGroupSuffix(Player player) {
		String group = BungeeSuiteChat.CHAT.getPrimaryGroup(player);
		String suffix = "";
		if(!group.equals("")){
			suffix = BungeeSuiteChat.CHAT.getGroupSuffix(player.getWorld(), group);
		}
		if(suffix == null){
			return "";
		}
		return suffix;
	}


	public static String getPlayersGroupPrefix(Player player) {
		String group = BungeeSuiteChat.CHAT.getPrimaryGroup(player);
		String prefix = "";
		if(!group.equals("")){
			prefix = BungeeSuiteChat.CHAT.getGroupPrefix(player.getWorld(), group);
		}
		if(prefix == null){
			return "";
		}
		return prefix;
	}


	public static CharSequence getPlayersPermPrefix(Player player) {
		String prefix = BungeeSuiteChat.CHAT.getPlayerPrefix(player);
		if(prefix == null){
			return "";
		}
		return prefix;
	}


	public static String getPlayersSuffixGroup(Player player) {
		for(String s : suffixes.keySet()){
			if(player.hasPermission("bungeesuite.chat.suffix."+s)){
				return s;
			}
		}
		return "";
	}


	public static String getPlayersPrefixGroup(Player player) {
		for(String s : prefixes.keySet()){
			if(player.hasPermission("bungeesuite.chat.prefix."+s)){
				return s;
			}
		}
		return "";
	}


	public static String getPlayersPrefix(String group) {
		String prefix = prefixes.get(group);
		if(prefix==null){
			return "";
		}
		return prefix;
	}
	public static String getPlayersSuffix(String group) {
		String suffix = suffixes.get(group);
		if(suffix==null){
			return "";
		}
		return suffix;
	}


	
	
}
