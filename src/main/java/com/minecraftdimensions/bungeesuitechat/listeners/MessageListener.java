package com.minecraftdimensions.bungeesuitechat.listeners;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;
import com.minecraftdimensions.bungeesuitechat.managers.ChannelManager;
import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;
import com.minecraftdimensions.bungeesuitechat.managers.PrefixSuffixManager;
import com.minecraftdimensions.bungeesuitechat.objects.BSPlayer;
import com.minecraftdimensions.bungeesuitechat.objects.ServerData;

public class MessageListener implements PluginMessageListener {

	@Override
	public void onPluginMessageReceived(String pluginChannel, Player reciever,byte[] message) {
		if (!pluginChannel.equalsIgnoreCase(BungeeSuiteChat.INCOMING_PLUGIN_CHANNEL))
			return;
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(
				message));
		String channel = null;
		try {
			channel = in.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(channel.equals("SendGlobalChat")){
			try {
				ChannelManager.getGlobalChat(in.readUTF(),in.readUTF());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		if(channel.equals("SendAdminChat")){
			try {
				ChannelManager.getAdminlChat(in.readUTF());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		if(channel.equals("SendPlayer")){
			try {
				PlayerManager.addPlayer(new BSPlayer(in.readUTF()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		if(channel.equals("SendChannel")){
			try {
				ChannelManager.addChannel(in.readUTF());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		if(channel.equals("SendServerData")){
			try {
				ServerData.deserialise(in.readUTF());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		if(channel.equals("PrefixesAndSuffixes")){
			String prefix[] = null;
			String suffix[] = null;
			try {
				prefix = in.readUTF().split("%");
				suffix = in.readUTF().split("%");
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(prefix);
			System.out.println(suffix);
			for(int i=0;i<prefix.length;i+=2){
				PrefixSuffixManager.prefixes.put(prefix[i], prefix[i+1]);
				System.out.println(prefix[i]+" , "+ prefix[i+1]);
			}
			for(int i=0;i<suffix.length;i+=2){
				PrefixSuffixManager.suffixes.put(suffix[i], suffix[i+1]);
				System.out.println(suffix[i]+" , "+ suffix[i+1]);
			}
		}
		if(channel.equals("SendPlayersIgnores")){
			String player = null;
			String ignoresString[] = null ;
			try {
				player = in.readUTF();
				ignoresString = in.readUTF().split("%");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			final ArrayList<String> ignores = new ArrayList<String>();
			for(String s : ignoresString){
				ignores.add(s);
			}
			final String name = player;
			BSPlayer p = PlayerManager.getPlayer(player);
			if(p!=null){
			p.setIgnores(ignores);
			}else{
				Bukkit.getScheduler().runTaskLaterAsynchronously(BungeeSuiteChat.instance, new Runnable(){

					@Override
					public void run() {
						PlayerManager.getPlayer(name).setIgnores(ignores);
					}
					
				}, 10L);
			}
			return;
		}
		if(channel.equals("ReloadChat")){
			ChannelManager.reload();
			return;
		}


	}

}
