package com.gmail.zahusek;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.zahusek.api.chatapi.ChatAPI;
import com.gmail.zahusek.api.chatapi.ChatAPI.Click;
import com.gmail.zahusek.protocols.TinyProtocol;

public class Main extends JavaPlugin {

	protected TinyProtocol protocol;

	public static void main(String[] args) {
	}

	@Override
	public void onEnable() {
		protocol = new TinyProtocol(this);
	}

	@Override
	public void onDisable() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			ChatAPI api = new ChatAPI();
			api.add("Witaj ", new ChatAPI("§2ZaHa").addHover("§6Twoje §lUUID: ", player.getUniqueId().toString()), " na Serwerze srajkraft. ",
					"Zapoznaj sie z naszym aktualnym ", new ChatAPI("§6Regulaminem").addHover("§bKliknij", "§bby zapoznac sie z regulaminem").addClick(Click.RUN_COMMAND, "/rules"));
			api.send(player);
		}
		return false;
	}
}
