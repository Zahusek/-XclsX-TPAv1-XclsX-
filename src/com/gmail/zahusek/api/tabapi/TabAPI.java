package com.gmail.zahusek.api.tabapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.gmail.zahusek.packets.PlayerInfo;
import com.gmail.zahusek.packets.ScoreboardTeam;

public class TabAPI implements Listener {
	
	private final Plugin plg;
	
	public TabAPI(Plugin plg){
		this.plg = plg;
	}
	public void register(){
		Bukkit.getServer().getPluginManager().registerEvents(this, plg);
		new PacketEvent();
	}
	public void unregister(){
		for(Player online : Bukkit.getServer().getOnlinePlayers())
			removePlayer(online);
	}
	public void updatePlayer(Player p, List<String> l) {
		User player = User.getUser(p.getName());
		for(int i = 0; i < l.size(); i++) {
			String whole = l.get(i);
			String[] display = getDisplay(whole);
			if(i >= player.getPlayers().size()) {
				player.addPlayer();
				String name = player.getPlayer(i);
				new PlayerInfo($(name), true).sendPacket(p);
				new ScoreboardTeam(name, name, display[0], display[1], new ArrayList<String>(Arrays.asList(name)), 0, 0).sendPacket(p);
				continue;
			}
			String name = player.getPlayer(i);
			new ScoreboardTeam(name, name, display[0], display[1] , 0).sendPacket(p);
		}
	}
	public void removePlayer(Player p) {
		User player = User.getUser(p.getName());
		for(int i = 0; i < player.getPlayers().size(); i++) {
			String name = player.getPlayer(i);
			new PlayerInfo($(name), false).sendPacket(p);
			new ScoreboardTeam(name, name, "", "", new ArrayList<String>(Arrays.asList(name)), 1, 0).sendPacket(p);
		}
		player.getPlayers().clear();
		
	}
	private ChatColor[] fuqlist = { ChatColor.UNDERLINE, ChatColor.STRIKETHROUGH,
		ChatColor.MAGIC, ChatColor.ITALIC, ChatColor.BOLD};
	
	private String getFormat(String input) {
		String result = "";
		int length = input.length();
		first: for (int index = length - 1; index > -1; index--) {
			if (input.charAt(index) == '\u00A7' && index < length - 1) {
				ChatColor color = ChatColor.getByChar(input.charAt(index + 1));
				if (color == null) continue;
				for(ChatColor col : fuqlist) if(color == col) {
					result += color.toString();
					continue first;
				}
				result = color.toString() + result;
				break first;
			}
		}
		if(result.isEmpty()) result = "\u00A7r";
		return result;
	}
	private String[] getDisplay(String whole) {
		String[] result = {"", ""};
		for(int i = 0; i < whole.length() && i < 32; i++){
			char c = whole.charAt(i);
			if(i < 16){
				result[0] += c;
				continue;
			}
			String format = getFormat(whole.substring(0, 17));
			if(i > 16 && i+format.length() < 33){
				result[1] += c;
				continue;
			}
		}
		if(whole.length() < 17) return result;
		
		if(result[0].charAt(15) == '\u00A7'){
			result[0] = result[0].substring(0, 15);
		}
		String format = getFormat(whole.substring(0, 17));
		result[1] = format + result[1];
		
		return result;
	}
	private String $(String s){
		return '$' + s;
	}
}
