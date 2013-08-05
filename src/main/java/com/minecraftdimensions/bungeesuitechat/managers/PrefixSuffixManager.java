package com.minecraftdimensions.bungeesuitechat.managers;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class PrefixSuffixManager {
	
	public static HashMap<String, String> prefixes = new HashMap<String, String>();
	public static HashMap<String, String> suffixes = new HashMap<String, String>();
	
	public static boolean groupHasPrefix(String group){
		return prefixes.containsKey(group);
	}
	
	public static boolean groupHasSuffix(String group){
		return suffixes.containsKey(group);
	}

	
	public static String getPlayersPrefix(Player player){
		for(String s: prefixes.keySet()){
			if(player.hasPermission("bungeesuite.chat.prefix."+s)){
				return prefixes.get(s);
			}
		}
		return null;
	}
	
	public static String getPlayersPrefixGroup(Player player){
		for(String s: prefixes.keySet()){
			if(player.hasPermission("bungeesuite.chat.prefix."+s)){
				return s;
			}
		}
		return null;
	}
	
	public static String getPlayersSuffixGroup(Player player){
		for(String s: suffixes.keySet()){
			if(player.hasPermission("bungeesuite.chat.suffix."+s)){
				return s;
			}
		}
		return null;
	}
	
	public static String getPlayersSuffix(Player player){
		for(String s: suffixes.keySet()){
			if(player.hasPermission("bungeesuite.chat.suffix."+s)){
				return suffixes.get(s);
			}
		}
		return null;
	}
	
	public static boolean playerHasPrefix(Player player){
		return groupHasPrefix(getPlayersPrefixGroup(player));
	}
	public static boolean playerHasSuffix(Player player){
		return groupHasSuffix(getPlayersSuffixGroup(player));
	}

	public static void reload() {
		prefixes.clear();
		suffixes.clear();
	}
	
	
}
