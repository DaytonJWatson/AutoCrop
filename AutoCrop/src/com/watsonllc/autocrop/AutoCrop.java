package com.watsonllc.autocrop;


import org.bukkit.plugin.java.JavaPlugin;

import com.watsonllc.autocrop.Commands.Commands;
import com.watsonllc.autocrop.Config.Config;
import com.watsonllc.autocrop.Listeners.Listeners;

public class AutoCrop extends JavaPlugin {
	public static AutoCrop instance;
	
	@Override
	public void onEnable() {
		instance = this;
		
		Listeners.setup();
		Commands.setup();
		Config.setup();
	}
	
}
