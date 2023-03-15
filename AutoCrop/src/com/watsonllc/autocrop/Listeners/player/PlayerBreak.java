package com.watsonllc.autocrop.Listeners.player;

import java.util.Map;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.watsonllc.autocrop.Config.Config;

public class PlayerBreak implements Listener {
	Boolean enabled;
	int resetAge;
	int maxAge;
	Block block;
	BlockData data;
	Material crop;
	Player player;
	Inventory inv;
	Location loc;
	Location blockLoc;
	Random random;

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		random = new Random();
		enabled = Config.getBoolean("enabled");
		resetAge = Config.getInt("resetAgeTo");
		block = event.getBlock();
		data = block.getBlockData();
		crop = event.getBlock().getType();
		player = event.getPlayer();
		inv = player.getInventory();
		loc = player.getLocation();
		blockLoc = block.getLocation();

		
		// cocoa beans
		if(enabled && isCropOther() && getAge() >= maxAge) {
			event.setCancelled(true);
			if(resetAge >= maxAge) {
				reAge(maxAge);
			} else {
				reAge(resetAge);
			}
			dropCropOther(3);
		}
		
		// everything else
		if (enabled && isCrop() && isFarmlandBelow() && getAge() >= maxAge) {
			event.setCancelled(true);
			if(resetAge >= maxAge) {
				reAge(maxAge);
			} else {
				reAge(resetAge);
			}
			dropCrop(fortuneAmount());
			seedHandler();
		} else {
			return;
		}
	}

	private int fortuneLevel() {
		ItemStack item = player.getInventory().getItemInMainHand();
		Map<Enchantment, Integer> enchantments = item.getEnchantments();

		if (enchantments.containsKey(Enchantment.LOOT_BONUS_BLOCKS)) {
			int level = enchantments.get(Enchantment.LOOT_BONUS_BLOCKS);
			return level;
		} else
			return 0;
	}

	private int fortuneAmount() {
		int amount = 0;
		if (fortuneLevel() == 0) {
			amount = 1;
		} else if (fortuneLevel() == 1) {
			amount = random.nextInt(2) + 1;
		} else if (fortuneLevel() == 2) {
			amount = random.nextInt(3) + 1;
		} else if (fortuneLevel() == 3) {
			amount = random.nextInt(4) + 1;
		}

		if (Config.getBoolean("useFortune")) {
			return amount;
		} else {
			return 1;
		}
	}
	
	private void dropCropOther(int amount) {
		amount = random.nextInt(amount) + 1;
		ItemStack cropType = new ItemStack(getNewType());
		cropType.setAmount(amount);
		
		dropCrop(amount);
	}

	private void dropCrop(int amount) {
		ItemStack cropType = new ItemStack(getNewType());
		cropType.setAmount(amount);

		if (Config.getBoolean("dropToInventory")) {
			if (inv.firstEmpty() == -1) {
				player.getWorld().dropItem(blockLoc, cropType);
			} else {
				inv.addItem(cropType);
			}
		} else {
			player.getWorld().dropItem(blockLoc, cropType);
		}
	}
	
	// drops beetroot and wheat seeds
	private void seedHandler() {
		if (crop == Material.WHEAT) {
			ItemStack seeds = new ItemStack(Material.WHEAT_SEEDS);
			seeds.setAmount(1);
			player.getWorld().dropItem(blockLoc, seeds);
		} else if(crop == Material.BEETROOTS) {
			ItemStack seeds = new ItemStack(Material.BEETROOT_SEEDS);
			seeds.setAmount(1);
			player.getWorld().dropItem(blockLoc, seeds);
		} else {
			return;
		}
	}

	private void reAge(int age) {
		BlockData data = block.getBlockData();
		((Ageable) data).setAge(age);
		block.setBlockData(data);
	}

	private int getAge() {
		return ((Ageable) data).getAge();
	}
	
	private int getMaxAge() {
		return ((Ageable) data).getMaximumAge();
	}

	private boolean isFarmlandBelow() {
		World world = block.getWorld();
		int x = block.getX();
		int y = block.getY() - 1;
		int z = block.getZ();
		Block blockBelow = world.getBlockAt(x, y, z);
		if (blockBelow.getType() == Material.FARMLAND)
			return true;
		return false;
	}

	private Material getNewType() {
		switch (block.getType()) {
		case GLOW_BERRIES:
			return Material.GLOW_BERRIES;
		case COCOA:
			return Material.COCOA_BEANS;
		case BEETROOTS:
			return Material.BEETROOT;
		case POTATOES:
			return Material.POTATO;
		case CARROTS:
			return Material.CARROT;
		case WHEAT:
			return Material.WHEAT;
		default:
			return null;
		}
	}
	
	private boolean isCropOther() {
		switch(block.getType()) {
		case COCOA:
			maxAge = getMaxAge();
			return true;
		default:
			return false;
		}
	}

	private boolean isCrop() {
		switch (block.getType()) {
		case BEETROOTS:
			maxAge = getMaxAge();
			return true;
		case GLOW_BERRIES:
			maxAge = getMaxAge();
			return true;
		case POTATOES:
			maxAge = getMaxAge();
			return true;
		case CARROTS:
			maxAge = getMaxAge();
			return true;
		case WHEAT:
			maxAge = getMaxAge();
			return true;
		default:
			return false;
		}
	}
}