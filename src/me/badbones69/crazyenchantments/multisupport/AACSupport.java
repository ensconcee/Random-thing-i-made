package me.badbones69.crazyenchantments.multisupport;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.konsolas.aac.api.PlayerViolationEvent;

public class AACSupport implements Listener{
	
	private static ArrayList<UUID> exempted = new ArrayList<UUID>();
	private static Plugin plugin = Bukkit.getPluginManager().getPlugin("CrazyEnchantments");
	
	public static void exemptPlayer(Player player){
		if(player != null) {
			if(player.getUniqueId() != null) {
				if(!exempted.contains(player.getUniqueId())){
					exempted.add(player.getUniqueId());
				}
			}
		}
	}
	
	public static void unexemptPlayer(Player player){
		if(player != null) { 
			if(player.getUniqueId() != null) { 
				exempted.remove(player.getUniqueId()); 
			} 
		} 
	}
	
	public static Boolean isExempted(Player player){
		if(player != null) { 
			if(player.getUniqueId() != null) { 
				return exempted.contains(player.getUniqueId()); 
			} 
		} 
		return false; 
	}
	
	public static void exemptPlayerTime(Player player){
		exemptPlayer(player);
		new BukkitRunnable(){
			@Override
			public void run() {
				unexemptPlayer(player);
			}
		}.runTaskLaterAsynchronously(plugin, 100);
	}
	
	@EventHandler
	public void onViolation(PlayerViolationEvent e){
		if(isExempted(e.getPlayer())){
			e.setCancelled(true);
		}
	}
	
}