package com.minecraftdimensions.bungeesuitechat;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class CleanChatSyncTask extends BukkitRunnable {

	private final Player player;
	private String message;
	private final Set<Player> players;
	private BungeeSuiteChat plugin;
	private boolean global = false;
	private final String channel;
	private String format;
	private Boolean isCancelled;
	public static final String[] NICKNAME_DISPLAY_BYPASS_PERMISSION_NODES = {
			"bungeesuite.chat.clean.display", "bungeesuite.chat.clean.*",
			"bungeesuite.chat.*", "bungeesuite.admin", "bungeesuite.*" };
	public static final String[] COLOR_CHAT_BYPASS_PERMISSION_NODES = {
			"bungeesuite.chat.clean.color", "bungeesuite.chat.clean.*",
			"bungeesuite.chat.*", "bungeesuite.admin", "bungeesuite.*" };
	public static final String[] DECAP_CHAT_BYPASS_PERMISSION_NODES = {
			"bungeesuite.chat.clean.capitals", "bungeesuite.chat.clean.*",
			"bungeesuite.chat.*", "bungeesuite.admin", "bungeesuite.*" };

	public CleanChatSyncTask(BungeeSuiteChat plugin, String channel,
			Set<Player> players, boolean global,Player player, String message, String format, boolean isCancelled) {
		this.plugin = plugin;
		this.player = player;
		this.message = message;
		this.players = players;
		this.global = global;
		this.channel = channel;
		this.format = format;
		this.isCancelled = isCancelled;
	}

	public void run() {
		if (!isCancelled) {
			format = format.replace("{channel}", channel.substring(0, 1)
					.toUpperCase() + channel.substring(1, channel.length()));
			if (CommandUtil.hasPermission(player,
					NICKNAME_DISPLAY_BYPASS_PERMISSION_NODES)) {
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
			if (!CommandUtil.hasPermission(player,
					DECAP_CHAT_BYPASS_PERMISSION_NODES)) {
				message=deCapitalize(message);
			}
			if (player.hasPermission("bungeesuite.chat.admincolor")) {
				message =colorize(plugin.adminColor + message);
			} else if (CommandUtil.hasPermission(player,
					COLOR_CHAT_BYPASS_PERMISSION_NODES)) {
				message = colorize(message);
			} else {
				message = (ChatColor.stripColor(message));
			}
			format = cleanChat(format);
			format = colorize(format);

			for (Player data : players) {
				data.sendMessage(String.format(format, player.getName(), message));
			}
			if (global) {
				plugin.utils.sendGlobalCleanMessage(player, String.format(
						format, player.getName(),
						message));
			}
		}
	}

	public String deCapitalize(String input) {
		String words[] = input.split(" ");
		if (words.length > 0) {
			int count = 0;
			for (String word : words) {
				if (count == 0) {
					words[count] = WordUtils.capitalizeFully(word);
					count++;
				} else {
					if (word.length() > 2) {
						words[count] = word.toLowerCase();
						count++;
					} else if (word.equals("i")) {
						words[count] = WordUtils.capitalize(word);
						count++;
					} else if (word.equalsIgnoreCase("im")
							|| word.equals("i'm")) {
						words[count] = WordUtils.capitalize(word);
						count++;
					} else if (!word.startsWith(":") && !word.equals("XD")) {
						words[count] = word.toLowerCase();
						count++;
					}
				}
			}
		}
		String output = StringUtils.join(words, " ");
		return output;

	}

	public String colorize(String input) {
		return ChatColor.translateAlternateColorCodes('&', input);
	}

	public String cleanChat(String input) {
		input = input.replaceAll(plugin.cleanChatRegex, "");
		return input;
	}
}