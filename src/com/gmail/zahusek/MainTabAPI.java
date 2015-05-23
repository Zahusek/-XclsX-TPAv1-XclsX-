package com.gmail.zahusek;

import java.util.Arrays;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.zahusek.api.tabapi.TabAPI;
import com.gmail.zahusek.protocols.TinyProtocol;

public class Main extends JavaPlugin implements Listener {

	protected TinyProtocol protocol;
	private TabAPI tabapi = new TabAPI(this);

	public static void main(String[] args) {
	}

	@Override
	public void onEnable() {
		protocol = new TinyProtocol(this);
		tabapi.register();
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()){
					tabapi.updatePlayer(p, Arrays.asList(
								"§c§l§m#@#",
								"§6Welcome",
								"§c§l§m#@#",
								"      §9§lTIME:",
								String.format("§d%1$tH§5§l:§d%1$tM§5§l:§d%1$tS", Calendar.getInstance()),
								"§c§l§m#@#"));
				}
				
			}
		}.runTaskTimer(this, 0, 10);
		
	}

	@Override
	public void onDisable() {
		tabapi.unregister();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
		}
		return false;
	}
}
