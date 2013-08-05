package com.minecraftdimensions.bungeesuitechat.managers;

import org.bukkit.entity.Player;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;

public class PermissionsManager {

	public static void addAllPermissions(Player player) {
		player.addAttachment(BungeeSuiteChat.instance, "bungeesuite.chat.*", true);
	}
	public static void addModPermissions(Player player) {
		player.addAttachment(BungeeSuiteChat.instance, "bungeesuite.chat.mod.*", true);
	}
	public static void addUserPermissions(Player player) {
		player.addAttachment(BungeeSuiteChat.instance, "bungeesuite.chat.user.*", true);
	}
}
