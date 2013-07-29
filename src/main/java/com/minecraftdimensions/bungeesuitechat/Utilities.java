package com.minecraftdimensions.bungeesuitechat;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utilities {
	BungeeSuiteChat plugin;

	public static final String[] GLOBAL_PERMISSION_NODES = {
			"bungeesuite.chat.global", "bungeesuite.chat.user",
			"bungeesuite.chat.*", "bungeesuite.mod", "bungeesuite.admin",
			"bungeesuite.*" };
	public static final String[] SERVER_PERMISSION_NODES = {
			"bungeesuite.chat.server", "bungeesuite.chat.user",
			"bungeesuite.chat.*", "bungeesuite.mod", "bungeesuite.admin",
			"bungeesuite.*" };
	public static final String[] LOCAL_PERMISSION_NODES = {
			"bungeesuite.chat.local", "bungeesuite.chat.user",
			"bungeesuite.chat.*", "bungeesuite.mod", "bungeesuite.admin",
			"bungeesuite.*" };
	public static final String[] NICKNAME_DISPLAY_PERMISSION_NODES = {
			"bungeesuite.chat.nickname.display", "bungeesuite.chat.nickname.*",
			"bungeesuite.chat.*", "bungeesuite.admin", "bungeesuite.*" };
	public static final String[] NICKNAME_PREFACE_PERMISSION_NODES = {
			"bungeesuite.chat.nickname.preface", "bungeesuite.chat.nickname.*",
			"bungeesuite.chat.*", "bungeesuite.admin", "bungeesuite.*" };

	public Utilities(BungeeSuiteChat bungeeSuiteChat) {
		plugin = bungeeSuiteChat;
	}

	public void getDefaultPrefixAndSuffix() {
		if (!plugin.gotPrefixSuffix) {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
			try {
				out.writeUTF("GetDefaultPrefixAndSuffix");
			} catch (IOException e) {
				e.printStackTrace();
			}
			new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
					.runTaskLaterAsynchronously(plugin, 4);
		}

	}

	public void addChatColumns() {
		if (!plugin.columnsCreated) {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
			try {
				out.writeUTF("AddColumns");
				out.writeUTF("BungeePlayers");
				out.writeUTF("ALTER TABLE BungeePlayers ADD nickname VARCHAR(20),ADD channel VARCHAR(50),ADD muted tinyint DEFAULT 0,ADD chat_spying tinyint DEFAULT 0");
			} catch (IOException e) {
				e.printStackTrace();
			}
			new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
					.runTaskLaterAsynchronously(plugin, 2);
		}
		addBasicFormat();
	}

	public void addBasicFormat() {
		if (!plugin.columnsCreated) {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
			try {
				out.writeUTF("AddColumns");
				out.writeUTF("BungeePlayers");
				out.writeUTF("ALTER TABLE BungeePlayers ADD clean_chat tinyint DEFAULT 0");
			} catch (IOException e) {
				e.printStackTrace();
			}
			new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
					.runTaskLaterAsynchronously(plugin, 2);
			plugin.columnsCreated = true;
		}

	}

	public void createBaseTables() {
		if (!plugin.tablesCreated) {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
			try {
				out.writeUTF("CreateTable");
				out.writeUTF("BungeeChatIgnores");
				out.writeUTF("CREATE TABLE BungeeChatIgnores (player VARCHAR(100), ignoring VARCHAR(100), FOREIGN KEY (player) REFERENCES BungeePlayers (playername), FOREIGN KEY (ignoring) REFERENCES BungeePlayers (playername))");
			} catch (IOException e) {
				e.printStackTrace();
			}
			new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
					.runTaskLaterAsynchronously(plugin, 2);
			plugin.tablesCreated = true;
		}
	}

	public void createChatConfig() {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("CreateChatConfig");
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);
	}

	public void getChannelFormats() {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("GetChannelFormats");
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void getMessage(String sender, String message) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("GetServerMessage");
			out.writeUTF(sender);
			out.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void sendPrivateMessage(String sender, String player, String message) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("SendPrivateMessage");
			out.writeUTF(sender);
			out.writeUTF(player);
			out.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void muteAll(String sender) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("MuteAll");
			out.writeUTF(sender);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);
	}

	public void mutePlayer(String sender, String player) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("MutePlayer");
			out.writeUTF(sender);
			out.writeUTF(player);

		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void nicknamePlayer(String sender, String player, String nickname) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("NicknamePlayer");
			out.writeUTF(sender);
			out.writeUTF(player);
			out.writeUTF(nickname);

		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void replyToPlayer(String sender, String message) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("ReplyToPlayer");
			out.writeUTF(sender);
			out.writeUTF(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void ignorePlayer(String sender, String player) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("IgnorePlayer");
			out.writeUTF(sender);
			out.writeUTF(player);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);
		plugin.playersIgnores.get(Bukkit.getPlayer(sender)).add(player);
	}

	public void addIgnorePlayer(String sender, String player) {
		plugin.playersIgnores.get(Bukkit.getPlayer(sender)).add(player);
	}

	public void removeIgnorePlayer(String sender, String player) {
		plugin.playersIgnores.get(Bukkit.getPlayer(sender)).remove(player);
	}

	public void toggleChannel(Player player) {
		String currentChannel = plugin.playersChannel.get(player);
		if (!plugin.forcedChan) {
			if (currentChannel.equalsIgnoreCase("server")
					&& CommandUtil.hasPermission(player,
							GLOBAL_PERMISSION_NODES)) {
				changePlayersChannel(player.getName(),
						getChannelString("global"));
				plugin.playersChannel.put(player, getChannelString("global"));
			} else if (currentChannel.equalsIgnoreCase("global")
					&& CommandUtil
							.hasPermission(player, LOCAL_PERMISSION_NODES)) {
				changePlayersChannel(player.getName(),
						getChannelString("local"));
				plugin.playersChannel.put(player, getChannelString("local"));
			}
		} else {
			getPlayersNextChannel(player.getName());
		}
	}

	public void toggleToChannel(String sender, String channel) {
		channel = getChannelString(channel);
		// check for other channel permission when avail TODO
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("ToggleToChannel");
			out.writeUTF(sender);
			out.writeUTF(channel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	private String getChannelString(String channel) {
		if (plugin.channelFormats.containsKey(channel)) {
			return channel;
		} else {
			for (String data : plugin.channelFormats.keySet()) {
				if (data.toLowerCase().startsWith(channel.toLowerCase())) {
					return data;
				}
			}
			return channel;
		}
	}

	private void getPlayersNextChannel(String player) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("GetPlayersNextChannel");
			out.writeUTF(player);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void changePlayersChannel(String player, String channel) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("ChannelChange");
			out.writeUTF(player);
			out.writeUTF(channel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);
	}

	public void unignorePlayer(String sender, String player) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("UnIgnorePlayer");
			out.writeUTF(sender);
			out.writeUTF(player);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void unMuteAll(String sender) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("UnMuteAll");
			out.writeUTF(sender);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void unMutePlayer(String sender, String player) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("UnMutePlayer");
			out.writeUTF(sender);
			out.writeUTF(player);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void sendLocalMessageToSpies(String sender, String format,
			String world) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("SendLocalMessageToSpies");
			out.writeUTF(sender);
			out.writeUTF(format);
			out.writeUTF(world);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void sendServerMessageToSpies(String sender, String format) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("SendServerMessageToSpies");
			out.writeUTF(sender);
			out.writeUTF(format);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void sendGlobalMessage(String sender, String format) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("SendGlobalMessage");
			out.writeUTF(sender);
			out.writeUTF(format);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void getPlayersInfo(String player) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("GetPlayersInfo");
			out.writeUTF(player);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskLaterAsynchronously(plugin, 2);

	}

	public void setPlayersChannel(String player, String chan) {
		String channel = getChannelString(chan);
		plugin.playersChannel.put(Bukkit.getPlayer(player), channel);

	}

	public void setUnMutePlayer(String player) {
		if (plugin.mutedPlayers.contains(player)) {
			plugin.mutedPlayers.remove(player);
		}

	}

	public void setMutePlayer(String player) {
		if (!plugin.mutedPlayers.contains(player)) {
			plugin.mutedPlayers.add(player);
		}

	}

	public void setPlayersNickname(String player, String nick, boolean preface) {
		Player p = Bukkit.getPlayer(player);
		if (p != null) {
			if (CommandUtil.hasPermission(p, NICKNAME_DISPLAY_PERMISSION_NODES)) {
				if (preface
						&& CommandUtil.hasPermission(p,
								NICKNAME_PREFACE_PERMISSION_NODES)) {
					String prefix = null;
					if (plugin.usingVault) {
						String group = plugin.CHAT.getPlayerGroups(p)[0];
						prefix = plugin.CHAT.getPlayerPrefix(p);
						if (prefix == null) {
							prefix = plugin.CHAT.getGroupPrefix(p.getWorld(),
									group);
							if (prefix == null) {
								prefix = "";
							}
						}
					} else if (!plugin.prefixes.isEmpty()) {
						for (String prefi : plugin.prefixes.keySet()) {
							if (p.hasPermission("bungeesuite.chat.prefix."
									+ prefi)) {
								prefix = 
										plugin.prefixes.get(prefi);
								break;
							}
						}
					}else{
						prefix ="";
					}
					prefix += nick;
					nick = prefix;
				}
				p.setDisplayName(colorize(nick));
				if (nick.length() > 15) {
					p.setPlayerListName(colorize(nick).substring(0, 15));
				} else {
					p.setPlayerListName(colorize(nick));
				}
				ByteArrayOutputStream b = new ByteArrayOutputStream();
				DataOutputStream out = new DataOutputStream(b);
				try {
					out.writeUTF("NicknamePermission");
					out.writeUTF(player);
					out.writeUTF(nick);
				} catch (IOException e) {
					e.printStackTrace();
				}
				new PluginMessageTask(this.plugin,
						Bukkit.getOnlinePlayers()[0], b)
						.runTaskAsynchronously(plugin);
			}
		}
	}

	public String colorize(String input) {
		return ChatColor.translateAlternateColorCodes('&', input);
	}

	public void listPlayersIgnores(Player player) {
		String message = ChatColor.BLUE + "You are currently ignoring: ";
		for (String data : plugin.playersIgnores.get(player)) {
			message += data + ", ";
		}
		player.sendMessage(message);
	}

	public void setPlayersIgnores(String player, String ignores) {
		Player p = Bukkit.getPlayer(player);
		ArrayList<String> playersIgnores = new ArrayList<String>();
		for (String data : ignores.split("~")) {
			if (!data.equalsIgnoreCase("")) {
				playersIgnores.add(data);
			}
		}
		plugin.playersIgnores.put(p, playersIgnores);
	}

	public void localSpyMessage(String receivers, String format) {
		String players[] = receivers.split("~");
		for (String data : players) {
			if (!data.equals(players[0])) {
				Player bp = Bukkit.getPlayer(data);
				if (!bp.getWorld().getName().equalsIgnoreCase(players[0])) {
					bp.sendMessage(format);
				}

			}
		}

	}

	public void chatSpy(String name) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("ChatSpy");
			out.writeUTF(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void getLocalRadius() {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("GetLocalRadius");
			out.writeUTF(Bukkit.getServerName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void cleanchat(Player player) {

		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("CleanChat");
			out.writeUTF(player.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);
		if (plugin.cleanChat.contains(player)) {
			plugin.cleanChat.remove(player);
		} else {
			plugin.cleanChat.add(player);
		}

	}

	public void getForcedServerChannel() {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("GetForcedServerChannel");
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void reloadChat(Player sender) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("ReloadChat");
			out.writeUTF(sender.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void whoisPlayer(String sender, String player) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("WhoIsPlayer");
			out.writeUTF(sender);
			out.writeUTF(player);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void sendGlobalCleanMessage(Player player, String format) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("SendCleanGlobalMessage");
			out.writeUTF(player.getName());
			out.writeUTF(format);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void sendChat(String format) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("SendChat");
			out.writeUTF(format);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}

	public void tempMutePlayer(String sender, String player, int time) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("TempMute");
			out.writeUTF(sender);
			out.writeUTF(player);
			out.writeInt(time);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new PluginMessageTask(this.plugin, Bukkit.getOnlinePlayers()[0], b)
				.runTaskAsynchronously(plugin);

	}
}
