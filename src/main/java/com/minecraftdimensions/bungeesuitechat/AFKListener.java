package com.minecraftdimensions.bungeesuitechat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class AFKListener implements Listener {

	BungeeSuiteChat plugin;
	String test;

	public AFKListener(BungeeSuiteChat bungeeSuiteChat) {
		plugin = bungeeSuiteChat;
	}

	@EventHandler
	public void playerCommand(PlayerCommandPreprocessEvent e) {
		Player p =e.getPlayer();
		if(plugin.afkPlayers.contains(p)){
			plugin.afkPlayers.remove(p);
			plugin.utils.afkPlayer(p);
		}
	}
	
	@EventHandler
	public void playerChat(AsyncPlayerChatEvent e) {
		Player p =e.getPlayer();
		if(plugin.afkPlayers.contains(p)){
			plugin.afkPlayers.remove(p);
			plugin.utils.afkPlayer(p);
		}
	}
	//leaving out due to inefficiency
//	@EventHandler
//	public void playerMove(PlayerMoveEvent e) {
//		Player p =e.getPlayer();
//		if(plugin.afkPlayers.contains(p)){
//			plugin.afkPlayers.remove(p);
//			plugin.utils.afkPlayer(p);
//		}
//	}
}
