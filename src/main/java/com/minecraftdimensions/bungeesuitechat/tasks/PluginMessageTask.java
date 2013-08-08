package com.minecraftdimensions.bungeesuitechat.tasks;

import java.io.ByteArrayOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;


public class PluginMessageTask extends BukkitRunnable {
    private final ByteArrayOutputStream bytes;
    
    public PluginMessageTask(ByteArrayOutputStream bytes) {
        this.bytes = bytes;
    }

    public void run() {
        Bukkit.getOnlinePlayers()[0].sendPluginMessage(BungeeSuiteChat.instance, BungeeSuiteChat.OUTGOING_PLUGIN_CHANNEL, bytes.toByteArray());
    }

}