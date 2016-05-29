/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;
import com.minecraftdimensions.bungeesuitechat.objects.BSPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author erai
 */
public class RealnameCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 1) {
                        if(sender instanceof Player) {
                                if (!sender.hasPermission("bungeesuite.chat.command.realname")) {
                                        sender.sendMessage(command.getPermissionMessage());
                                        return true;
                                }else{
                                            BSPlayer p = PlayerManager.getsimilarNickPlayer(args[0]);
                                            if(p == null)
                                                    PlayerManager.realnamePlayer(sender.getName(), args[0]);
                                            else
                                                    sender.sendMessage(ChatColor.GRAY + p.getNickname() + ChatColor.RESET + ChatColor.GRAY + " is " + p.getName());
                                return true;
                                }
                        } else {
                                BSPlayer p = PlayerManager.getsimilarNickPlayer(args[0]);
                                if(p == null)
                                        sender.sendMessage(ChatColor.GRAY + args[0] + ChatColor.RESET + ChatColor.GRAY + " was not found!");
                                else
                                        sender.sendMessage(ChatColor.GRAY + p.getNickname() + ChatColor.RESET + ChatColor.GRAY + " is " + p.getName());
                                return true;
                        }
		}
		return false;
	}

}
