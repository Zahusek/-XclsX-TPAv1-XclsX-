package com.gmail.zahusek;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.zahusek.api.ServerStatusAPI;

public class Main extends JavaPlugin {

	TinyProtocol protocol;

	private ServerStatusAPI ssa = new ServerStatusAPI();

	public static void main(String[] args) {
		Class<?> s = String.class;
		System.out.println(s);
	}

	@Override
	public void onEnable() {
		System.out.println(Bukkit.getServerIcon().toString());
		protocol = new TinyProtocol(this);

		protocol.registerListener(ssa);

		ssa.setProtocolVersion(-1);
		ssa.setProtocolName("                                                                                                    §c»█▓☼▓█«     ");
		ssa.setOnline(0);
		ssa.setMaxOnline(0);
		ssa.setMotd("§6Motd");
		ssa.setGameProfiles(
				"§eWitaj ^^ !",
				"§a§l§nServerStatusAPI -",
				"§eNowa biblioteka pozwalajaca edytowac status serwera §l(Y)",
				"§eMianowicie:",
				"§6- §bZmiana status Online oraz Max Online ",
				"§6- §bZmiana nazwy protokoku wersji, lecz kosztem powyższej opcji :/",
				"§6- §bDostosowanie \"PlayerList'y\" pod swoje upodobania",
				"§6- §6No i oczywiscie Motd, ale to już jest domyslnie :D");
	}

	@Override
	public void onDisable() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			//Player player = (Player) sender;
		}
		return false;
	}
}
