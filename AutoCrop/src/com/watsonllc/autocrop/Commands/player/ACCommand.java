package com.watsonllc.autocrop.Commands.player;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.watsonllc.autocrop.Config.Config;
import com.watsonllc.autocrop.Utilities.Utils;

public class ACCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("autocrop") && player.hasPermission("autocrop.use")) {
			
			if(args.length == 0) {
				player.sendMessage(Utils.chat(Utils.chat("&7+============ &6AutoCrop &7============+")));
				player.sendMessage(Utils.chat("&7Version: &61.1.0"));
				player.sendMessage(Utils.chat("&7enabled: &6" + Config.getBoolean("enabled")));
				player.sendMessage(Utils.chat("&7dropToInventory: &6" + Config.getBoolean("dropToInventory")));
				player.sendMessage(Utils.chat("&7useFortune: &6" + Config.getBoolean("useFortune")));
				player.sendMessage(Utils.chat("&7resetAgeTo: &6" + Config.getInt("resetAgeTo")));
				player.sendMessage(Utils.chat(""));
				player.sendMessage(Utils.chat("&7/ac enabled"));
				player.sendMessage(Utils.chat("&7/ac droptoinventory"));
				player.sendMessage(Utils.chat("&7/ac usefortune"));
				player.sendMessage(Utils.chat("&7/ac resetageto <0-7>"));
			} else if(args.length == 1) {
				Config.reload();
				player.sendMessage(Utils.chat("&aConfiguration updated!"));
				if(args[0].equalsIgnoreCase("enabled")) {
					if(Config.getBoolean("enabled") == true) {
						Config.set("enabled", false);
					} else Config.set("enabled", true);
				} else if(args[0].equalsIgnoreCase("droptoinventory")) {
					if(Config.getBoolean("dropToInventory") == true) {
						Config.set("dropToInventory", false);
					} else Config.set("dropToInventory", true);
				} else if(args[0].equalsIgnoreCase("usefortune")) {
					if(Config.getBoolean("useFortune") == true) {
						Config.set("useFortune", false);
					} else Config.set("useFortune", true);
				}
			} else if(args.length == 2) {
				if(args[0].equalsIgnoreCase("resetageto")) {
					Config.set("resetAgeTo", Integer.parseInt(args[1]));
					player.sendMessage(Utils.chat("&7Crop reset age: &6" + args[1]));
				}
			} else {
				player.sendMessage(Utils.chat("&cInvalid arguments!"));
			}
		}
		return false;
	}

}
