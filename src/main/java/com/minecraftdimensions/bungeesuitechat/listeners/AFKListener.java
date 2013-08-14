package com.minecraftdimensions.bungeesuitechat.listeners;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;
import com.minecraftdimensions.bungeesuitechat.objects.BSPlayer;

public class AFKListener implements Listener {

	@EventHandler
	public void playerCommand(PlayerCommandPreprocessEvent e) {
		BSPlayer p =PlayerManager.getPlayer(e.getPlayer());
		if(p.isAFK()){
			PlayerManager.setPlayerAFK(e.getPlayer());
		}
	}
	
	@EventHandler
	public void playerChat(AsyncPlayerChatEvent e) {
		BSPlayer p =PlayerManager.getPlayer(e.getPlayer());
		if(p==null){
			e.setCancelled(true);
			return;
		}
		if(p.isAFK()){
			PlayerManager.setPlayerAFK(e.getPlayer());
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
