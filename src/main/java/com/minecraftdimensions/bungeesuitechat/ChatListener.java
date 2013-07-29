package com.minecraftdimensions.bungeesuitechat;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColls;
import com.massivecraft.factions.entity.UPlayer;

public class ChatListener implements PluginMessageListener, Listener {

	public static final String[] COLOR_CHAT_PERMISSION_NODES = {
			"bungeesuite.chat.color", "bungeesuite.chat.*",
			"bungeesuite.admin", "bungeesuite.*" };
	public static final String[] NICKNAME_DISPLAY_PERMISSION_NODES = {
			"bungeesuite.chat.nickname.display", "bungeesuite.chat.nickname.*",
			"bungeesuite.chat.*", "bungeesuite.admin", "bungeesuite.*" };
	public static final String[] MUTE_BYPASS_PERMISSIONS = {
			"bungeesuite.chat.bypass", "bungeesuite.chat.*",
			"bungeesuite.admin", "bungeesuite.*" };
	BungeeSuiteChat plugin;
	String test;

	public ChatListener(BungeeSuiteChat bungeeSuiteTeleports) {
		plugin = bungeeSuiteTeleports;
	}

	@EventHandler
	public void PlayerJoin(PlayerJoinEvent e) {

		// get plugin messages//
		if (plugin.firstLogin) {
			if (!plugin.createChatConfig) {
				plugin.utils.createChatConfig();
			}
			if (!plugin.columnsCreated) {
				plugin.utils.addChatColumns();
			}
			if (!plugin.tablesCreated) {
				plugin.utils.createBaseTables();
			}
			new FirstLoginTask(e.getPlayer(), plugin)
					.runTaskLaterAsynchronously(plugin, 10);
			plugin.firstLogin = false;
			return;
		}
		if (!plugin.playersChannel.containsKey(e.getPlayer())) {
			plugin.utils.getPlayersInfo(e.getPlayer().getName());
		}

	}

	@EventHandler
	public void PlayerLeave(PlayerQuitEvent e) {
		plugin.playersChannel.remove(e.getPlayer());
		if (plugin.mutedPlayers.contains(e.getPlayer().getName())) {
			plugin.mutedPlayers.remove(e.getPlayer().getName());
		}
		plugin.playersIgnores.remove(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void setFormatChat(AsyncPlayerChatEvent e) {
		if (e.isCancelled()) {
			return;
		}
		Player player = e.getPlayer();
		if (plugin.muteAll
				&& !CommandUtil.hasPermission(player, MUTE_BYPASS_PERMISSIONS)) {
			plugin.utils.getMessage(player.getName(), "GLOBAL_MUTE");
			e.setCancelled(true);
			return;
		}
		if (plugin.mutedPlayers.contains(player.getName())) {
			plugin.utils.getMessage(player.getName(), "MUTED");
			e.setCancelled(true);
			return;
		}
		String channel = plugin.playersChannel.get(player);
		if(channel==null){
			plugin.utils.getPlayersInfo(player.getName());
			e.setCancelled(true);
			return;
		}
		String format = plugin.channelFormats.get(channel);
		if(format==null){
			plugin.utils.getPlayersInfo(player.getName());
			e.setCancelled(true);
			return;
		}
		e.setFormat(format);
		if (channel.equalsIgnoreCase("local")) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (!p.getWorld().equals(player.getWorld())) {
					e.getRecipients().remove(p);
				} else if (p.getLocation().distance(player.getLocation()) > plugin.LOCAL_DISTANCE) {
					e.getRecipients().remove(p);
				} else if (plugin.playersIgnores.containsKey(p)
						&& !plugin.playersIgnores.get(p).isEmpty()
						&& plugin.playersIgnores.get(p).contains(
								e.getPlayer().getName())) {
					e.getRecipients().remove(p);
				}
			}
		} else if (channel.equalsIgnoreCase("server")) {
			for (Player data : Bukkit.getOnlinePlayers()) {
				if (!e.getRecipients().contains(data)) {
					e.getRecipients().add(data);
				}
				if (plugin.playersIgnores.containsKey(data)
						&& !plugin.playersIgnores.get(data).isEmpty()
						&& plugin.playersIgnores.get(data).contains(
								e.getPlayer().getName())) {
					e.getRecipients().remove(data);
				}

			}

		} else if (channel.equalsIgnoreCase("global")) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (!e.getRecipients().contains(p)) {
					e.getRecipients().add(p);
				}
				if (plugin.playersIgnores.containsKey(p)
						&& !plugin.playersIgnores.get(p).isEmpty()
						&& plugin.playersIgnores.get(p).contains(
								e.getPlayer().getName())) {
					e.getRecipients().remove(p);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void LogChat(AsyncPlayerChatEvent e) {
		if (e.isCancelled()) {
			return;
		}
		String channel = plugin.playersChannel.get(e.getPlayer());
		if (!channel.equalsIgnoreCase("Global")) {
			plugin.utils.sendChat(String.format(e.getFormat(), e.getPlayer()
					.getDisplayName(), e.getMessage()));
		} else {
			plugin.utils.sendGlobalMessage(e.getPlayer().getName(), String
					.format(e.getFormat(), e.getPlayer().getDisplayName(),
							e.getMessage()));
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerChat(AsyncPlayerChatEvent e) {
		if (e.isCancelled()) {
			return;
		}
		String originalFormat = e.getFormat();
		String originalMessage = e.getMessage();
		Player player = e.getPlayer();
		String channel = plugin.playersChannel.get(player);
		String format = e.getFormat();
		format = format.replace("{channel}", channel.substring(0, 1)
				.toUpperCase() + channel.substring(1, channel.length()));
		if (CommandUtil
				.hasPermission(player, NICKNAME_DISPLAY_PERMISSION_NODES)) {
			format = format.replace("{player}", player.getDisplayName());
		} else {
			format = format.replace("{player}", player.getName());
		}
		format = format.replace("{shortname}", plugin.shortForm);
		format = format.replace("{world}", player.getWorld().getName());
		format = format.replace("{server}", plugin.serverName);
		String prefix = "";
		String suffix = "";
		String group = "";
		if (plugin.usingVault) {
			group = plugin.CHAT.getPrimaryGroup(player);
			if (group == null) {
				group = "";
			}
			if (plugin.CHAT.getPlayerPrefix(player) != null)
				prefix = plugin.CHAT.getPlayerPrefix(player);
			else if (plugin.CHAT.getGroupPrefix(player.getWorld(), group) != null) {
				prefix = plugin.CHAT.getGroupPrefix(player.getWorld(), group);
			}
			if (plugin.CHAT.getPlayerSuffix(player) != null)
				suffix = plugin.CHAT.getPlayerSuffix(player);
			else if (plugin.CHAT.getGroupSuffix(player.getWorld(), group) != null) {
				suffix = plugin.CHAT.getGroupSuffix(player.getWorld(), group);
			}
		} else {
			if (!plugin.prefixes.isEmpty()) {

				for (String prefi : plugin.prefixes.keySet()) {
					if (player
							.hasPermission("bungeesuite.chat.prefix." + prefi)) {
						prefix = plugin.prefixes.get(prefi);
						break;
					}
				}
			}
			if (!plugin.suffixes.isEmpty()) {

				for (String suffi : plugin.suffixes.keySet()) {
					if (player
							.hasPermission("bungeesuite.chat.suffix." + suffi)) {
						suffix = plugin.suffixes.get(suffi);
						break;
					}
				}
			}

		}
		format = format.replace("{group}", group);
		format = format.replace("{suffix}", suffix);
		format = format.replace("{prefix}", prefix);
		format = format.replace("{message}", "%2$s");
		if (e.getPlayer().hasPermission("bungeesuite.chat.admincolor")) {
			e.setMessage(colorize(plugin.adminColor + e.getMessage()));
		}
		if (CommandUtil.hasPermission(player, COLOR_CHAT_PERMISSION_NODES)) {
			e.setMessage(colorize(e.getMessage()));
		}
		format = colorize(format);
		e.setFormat(format);
		if (plugin.factionChat) {
			if (channel.equals("Faction")) {
				UPlayer uplayer = UPlayer.get(e.getPlayer());
				uplayer.getFaction().sendMessage(
						String.format(format, e.getPlayer(), e.getMessage()));
				e.setCancelled(true);
				Bukkit.getConsoleSender().sendMessage(String.format(format, e.getPlayer(), e.getMessage()));
				plugin.utils.sendChat(String.format(format, e.getPlayer(), e.getMessage()));
			} else if (channel.equals("FactionAlly")) {
				UPlayer uplayer = UPlayer.get(e.getPlayer());
				Map<Rel, List<String>> rels = uplayer.getFaction()
						.getFactionNamesPerRelation(uplayer.getFaction());
				uplayer.getFaction().sendMessage(
						String.format(format, e.getPlayer(), e.getMessage()));
				for (String data : rels.get(Rel.ALLY)) {
					Faction f = FactionColls.get()
							.getForUniverse(uplayer.getFaction().getUniverse())
							.getByName(ChatColor.stripColor(data));
					f.sendMessage(String.format(format, e.getPlayer(),
							e.getMessage()));
				}
				Bukkit.getConsoleSender().sendMessage(String.format(format, e.getPlayer(), e.getMessage()));
				plugin.utils.sendChat(String.format(format, e.getPlayer(), e.getMessage()));
				e.setCancelled(true);
			}
			return;
		}
		Set<Player> cleanChatters = new HashSet<Player>();
		Iterator<Player> it = e.getRecipients().iterator();
		while (it.hasNext()) {
			Player p = it.next();
			if (plugin.cleanChat.contains(p)) {
				it.remove();
				cleanChatters.add(p);
			}
		}
		if (channel.equalsIgnoreCase("global")) {
			new CleanChatSyncTask(plugin, channel, cleanChatters, true,
					e.getPlayer(), originalMessage, originalFormat,
					e.isCancelled()).runTaskAsynchronously(plugin);
		} else if (!cleanChatters.isEmpty()) {
			new CleanChatSyncTask(plugin, channel, cleanChatters, false,
					e.getPlayer(), originalMessage, originalFormat,
					e.isCancelled()).runTaskAsynchronously(plugin);
		}

	}

	@Override
	public void onPluginMessageReceived(String pluginChannel, Player reciever,
			byte[] message) {
		if (!pluginChannel
				.equalsIgnoreCase(BungeeSuiteChat.INCOMING_PLUGIN_CHANNEL))
			return;

		DataInputStream in = new DataInputStream(new ByteArrayInputStream(
				message));

		String channel = null;
		try {
			channel = in.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (channel.equalsIgnoreCase("SendForcedChannel")) {
			String forcedchan = null;
			try {
				forcedchan = in.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			plugin.forcedChan = true;
			plugin.forcedChannel = forcedchan;
			return;
		}
		if (channel.equalsIgnoreCase("SendPrefix")) {
			plugin.usingVault = false;
			String prefix = null;
			try {
				prefix = in.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String prefixs[] = prefix.split("~");
			for (int i = 0; i < prefixs.length; i += 2) {
				plugin.prefixes.put(prefixs[i], prefixs[i + 1]);
			}
			return;
		}
		if (channel.equalsIgnoreCase("SendSuffix")) {
			plugin.usingVault = false;
			String suffix = null;
			try {
				suffix = in.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String suffixes[] = suffix.split("~");
			for (int i = 0; i < suffixes.length; i += 2) {
				plugin.suffixes.put(suffixes[i], suffixes[i + 1]);
			}
			return;
		}
		if (channel.equalsIgnoreCase("SendFormat")) {
			String serverName = null;
			String format = null;
			String shortform = null;
			try {
				serverName = in.readUTF();
				format = in.readUTF();
				shortform = in.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			plugin.serverName = serverName;
			String formats[] = format.split("~");
			for (int i = 0; i < formats.length; i += 2) {
				plugin.channelFormats.put(formats[i], formats[i + 1]);
			}
			plugin.shortForm = shortform;
			if (!plugin.factionChat) {
				plugin.channelFormats.remove("Faction");
				plugin.channelFormats.remove("Factions");
			}
			if (plugin.channelFormats.containsKey("Admin")) {
				plugin.registerAdminChannel();
			}
			return;
		}
		if (channel.equalsIgnoreCase("SendPlayersIgnores")) {
			String player = null;
			String ignores = null;
			try {
				player = in.readUTF();
				ignores = in.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			plugin.utils.setPlayersIgnores(player, ignores);
			return;
		}
		if (channel.equalsIgnoreCase("MuteAll")) {
			String status = null;
			try {
				status = in.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (status.equalsIgnoreCase("enable")) {
				plugin.muteAll = true;
			} else {
				plugin.muteAll = false;
			}
			return;
		}
		if (channel.equalsIgnoreCase("LocalRadius")) {
			int radius = 100;
			String adminColor = null;
			String cleanChatRegex = null;
			try {
				radius = in.readInt();
				adminColor = in.readUTF();
				cleanChatRegex = in.readUTF();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (radius == 0) {
				radius = 100;
			}
			plugin.LOCAL_DISTANCE = radius;
			plugin.adminColor = adminColor;
			plugin.cleanChatRegex = cleanChatRegex;
			return;
		}
		String player = null;
		try {
			player = in.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (channel.equalsIgnoreCase("SendPlayersInfo")) {
			String chan = null;
			boolean muted = false;
			boolean cleanchat = false;
			try {
				chan = in.readUTF();
				muted = in.readBoolean();
				cleanchat = in.readBoolean();
			} catch (IOException e) {
				e.printStackTrace();
			}
			plugin.utils.setPlayersChannel(player, chan);
			if (muted) {
				plugin.utils.setMutePlayer(player);
			}
			if (cleanchat) {
				plugin.cleanChat.add(Bukkit.getPlayer(player));
			}
			return;
		}
		if (channel.equalsIgnoreCase("PlayersNextChannel")) {
			String chan = null;
			try {
				chan = in.readUTF();
			} catch (IOException e) {
				e.printStackTrace();
			}
			plugin.utils.setPlayersChannel(player, chan);
			return;
		}
		if (channel.equalsIgnoreCase("NicknamedPlayer")) {
			String nick = null;
			boolean preface = false;
			try {
				nick = in.readUTF();
				preface = in.readBoolean();
			} catch (IOException e) {
				e.printStackTrace();
			}
			plugin.utils.setPlayersNickname(player, nick, preface);
			return;
		}
		if (channel.equalsIgnoreCase("Mute")) {
			plugin.utils.setMutePlayer(player);
			return;
		}
		if (channel.equalsIgnoreCase("UnMute")) {
			plugin.utils.setUnMutePlayer(player);
			return;
		}
		if (channel.equalsIgnoreCase("AddIgnorePlayer")) {
			String pl = null;
			try {
				pl = in.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			plugin.utils.addIgnorePlayer(player, pl);
			return;
		}
		if (channel.equalsIgnoreCase("RemoveIgnorePlayer")) {
			String pl = null;
			try {
				pl = in.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			plugin.utils.removeIgnorePlayer(player, pl);
			return;
		}
	}

	public String colorize(String input) {
		return ChatColor.translateAlternateColorCodes('&', input);
	}

	public String cleanChat(String input) {
		input = input.replaceAll(plugin.cleanChatRegex, "");
		return input;
	}
}
