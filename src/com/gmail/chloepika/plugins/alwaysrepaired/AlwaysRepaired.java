package com.gmail.chloepika.plugins.alwaysrepaired;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class AlwaysRepaired extends JavaPlugin implements Listener
{
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
	}

	public void onDisable()
	{
	}

	private void callScheduler(final Player player, final ItemStack item)
	{
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
		{
			@SuppressWarnings("deprecation")
			public void run()
			{
				item.setDurability((short) 0);
				player.updateInventory();
			}
		}, 1);
	}

	@EventHandler
	public void onEntityHit(EntityDamageByEntityEvent event)
	{
		if (event.getDamager() instanceof Player)
		{
			Player player = (Player) event.getDamager();
			if (player.hasPermission("alwaysrepaired.allow.tools") || player.hasPermission("alwaysrepaired.allow.*"))
			{
				ItemStack item = player.getItemInHand();
				if (item != null)
				{
					callScheduler(player, item);
				}
			}
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		if (player.hasPermission("alwaysrepaired.allow.tools") || player.hasPermission("alwaysrepaired.allow.*"))
		{
			ItemStack item = player.getItemInHand();
			if (item != null)
			{
				callScheduler(player, item);
			}
		}
	}

	@EventHandler
	public void onPlayerReceiveDamage(EntityDamageEvent event)
	{
		if (event.getEntity() instanceof Player)
		{
			Player player = (Player) event.getEntity();
			if (player.hasPermission("alwaysrepaired.allow.armour") || player.hasPermission("alwaysrepaired.allow.*"))
			{
				ItemStack helmet = player.getInventory().getHelmet();
				ItemStack chestplate = player.getInventory().getChestplate();
				ItemStack leggings = player.getInventory().getLeggings();
				ItemStack boots = player.getInventory().getBoots();
				if (helmet != null)
				{
					callScheduler(player, helmet);
				}
				if (chestplate != null)
				{
					callScheduler(player, chestplate);
				}
				if (leggings != null)
				{
					callScheduler(player, leggings);
				}
				if (boots != null)
				{
					callScheduler(player, boots);
				}
			}
		}
	}

	@EventHandler
	public void onBowUse(EntityShootBowEvent event)
	{
		if (event.getEntity() instanceof Player)
		{
			Player player = (Player) event.getEntity();
			if (player.hasPermission("alwaysrepaired.allow.bow") || player.hasPermission("alwaysrepaired.allow.*"))
			{
				ItemStack bow = event.getBow();
				if (bow != null)
				{
					callScheduler(player, bow);
				}
			}
		}
	}
}