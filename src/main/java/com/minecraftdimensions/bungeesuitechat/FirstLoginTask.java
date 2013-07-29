package com.minecraftdimensions.bungeesuitechat;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class FirstLoginTask extends BukkitRunnable {
    
	Player player;
    BungeeSuiteChat plugin;

    public FirstLoginTask(Player player, BungeeSuiteChat plugin) {
		this.player = player;
		this.plugin = plugin;
	}

	public void run() {
		if (!plugin.getChannelFormats) {
			plugin.utils.getChannelFormats();
		}
		if (!plugin.localRadiusRetrieved) {
			plugin.utils.getLocalRadius();
			plugin.utils.getForcedServerChannel();
		}
		if(!plugin.playersChannel.containsKey(player)){
			plugin.utils.getPlayersInfo(player.getName());
		}
		if(plugin.forcedChan){
			plugin.utils.setPlayersChannel(player.getName(), plugin.forcedChannel);
		}
    }

}