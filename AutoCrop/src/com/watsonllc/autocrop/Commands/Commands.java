package com.watsonllc.autocrop.Commands;

import com.watsonllc.autocrop.AutoCrop;
import com.watsonllc.autocrop.Commands.player.ACCommand;

public class Commands {
	public static void setup() {
		AutoCrop.instance.getCommand("autocrop").setExecutor(new ACCommand());
	}
}
