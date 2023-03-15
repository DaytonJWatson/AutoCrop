package com.watsonllc.autocrop.Listeners;

import org.bukkit.plugin.PluginManager;

import com.watsonllc.autocrop.AutoCrop;
import com.watsonllc.autocrop.Listeners.player.PlayerBreak;

public class Listeners {
	public static void setup() {
		PluginManager pm = AutoCrop.instance.getServer().getPluginManager();
		
		pm.registerEvents(new PlayerBreak(), AutoCrop.instance);
	}
}
