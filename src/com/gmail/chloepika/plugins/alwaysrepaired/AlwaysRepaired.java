package com.gmail.chloepika.plugins.alwaysrepaired;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class AlwaysRepaired extends JavaPlugin implements Listener
{
	private static final List<Integer> resetItems = new ArrayList<Integer> ();

	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		{
			resetItems.add(256);
			resetItems.add(257);
			resetItems.add(258);
			resetItems.add(261);
			resetItems.add(267);
			resetItems.add(268);
			resetItems.add(269);
			resetItems.add(270);
			resetItems.add(271);
			resetItems.add(272);
			resetItems.add(273);
			resetItems.add(274);
			resetItems.add(275);
			resetItems.add(276);
			resetItems.add(277);
			resetItems.add(278);
			resetItems.add(279);
			resetItems.add(283);
			resetItems.add(284);
			resetItems.add(285);
			resetItems.add(286);
			resetItems.add(290);
			resetItems.add(291);
			resetItems.add(292);
			resetItems.add(293);
			resetItems.add(294);
			resetItems.add(298);
			resetItems.add(299);
			resetItems.add(300);
			resetItems.add(301);
			resetItems.add(302);
			resetItems.add(303);
			resetItems.add(304);
			resetItems.add(305);
			resetItems.add(306);
			resetItems.add(307);
			resetItems.add(308);
			resetItems.add(309);
			resetItems.add(310);
			resetItems.add(311);
			resetItems.add(312);
			resetItems.add(313);
			resetItems.add(314);
			resetItems.add(315);
			resetItems.add(316);
			resetItems.add(317);
			resetItems.add(346);
			resetItems.add(359);
			resetItems.add(398);
		}
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
				if (resetItems.contains(item.getTypeId()))
				{
					item.setDurability((short) 0);
					player.updateInventory();
				}
			}
		}, 1);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockTiled(PlayerInteractEvent event)
	{
		if (!event.isCancelled())
		{
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK && 
					event.getClickedBlock().getType() == Material.DIRT && 
					(event.getPlayer().hasPermission("alwaysrepaired.allow.*") || event.getPlayer().hasPermission("alwaysrepaired.allow.tools")))
			{
				ItemStack item = event.getItem();
				if (item != null)
				{
					callScheduler(event.getPlayer(), item);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockIgnite(BlockIgniteEvent event)
	{
		if (!event.isCancelled())
		{
			if (event.getCause() == IgniteCause.FLINT_AND_STEEL)
			{
				Player player = (Player) event.getPlayer();
				if (player.hasPermission("alwaysrepaired.allow.flintandsteel") || player.hasPermission("alwaysrepaired.allow.*"))
				{
					ItemStack item = player.getItemInHand();
					callScheduler(player, item);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityHit(EntityDamageByEntityEvent event)
	{
		if (!event.isCancelled())
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
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event)
	{
		if (!event.isCancelled())
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
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerReceiveDamage(EntityDamageEvent event)
	{
		if (!event.isCancelled())
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
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBowUse(EntityShootBowEvent event)
	{
		if (!event.isCancelled())
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
}