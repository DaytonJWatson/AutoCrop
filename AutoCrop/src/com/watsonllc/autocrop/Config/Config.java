package com.watsonllc.autocrop.Config;

import com.watsonllc.autocrop.AutoCrop;

public class Config {
	public static void setup() {
		create();
	}
	
	public static void create() {
		AutoCrop.instance.getConfig().options().copyDefaults(true);
		AutoCrop.instance.saveDefaultConfig();
	}
	
	public static void save() {
		AutoCrop.instance.saveConfig();
	}
	
	public static void reload() {
		AutoCrop.instance.reloadConfig();
	}
	
	public static Boolean getBoolean(String path) {
		return AutoCrop.instance.getConfig().getBoolean(path);
	}
	
	public static void set(String path, Object object) {
		AutoCrop.instance.getConfig().set(path, object);
		save();
	}

	public static int getInt(String path) {
		return AutoCrop.instance.getConfig().getInt(path);
	}
}
