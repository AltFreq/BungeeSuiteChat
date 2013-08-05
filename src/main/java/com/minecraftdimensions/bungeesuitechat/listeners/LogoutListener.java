package com.minecraftdimensions.bungeesuitechat.listeners;


import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;


public class LogoutListener implements Listener {

	public void setFormatChat(PlayerQuitEvent e) {
		PlayerManager.unloadPlayer(e.getPlayer().getName());
	}

	

}
