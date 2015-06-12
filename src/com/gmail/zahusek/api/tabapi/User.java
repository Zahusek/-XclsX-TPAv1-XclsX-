package com.gmail.zahusek.api.tabapi;

import java.util.ArrayList;

public class User {
	public static final ArrayList<User> list = new ArrayList<User>();

	private String name;
	private ArrayList<String> players;
	private int x, z, s;
	private char[] colors = { 'a', 'b', 'c', 'd', 'e', 'f', 'r', '0', '1', '2',
			'3', '4', '5', '6', '7', '8', '9' };

	public User(String name) {
		this.name = name;
		this.players = new ArrayList<String>();
		this.x = 0;
		this.z = 0;
		this.s = 0;
		list.add(this);
	}
	public String getName() {
		return this.name;
	}
	public ArrayList<String> getPlayers() {
		return this.players;
	}
	public String getPlayer(int i) {
		return this.players.get(i);
	}
	public int getLastSize() {
		return this.s;
	}
	public void setLastSize(int s) {
		this.s = s;
	}
	public void addPlayer() {
		String fake = "";
		fake = "\u00A7" + this.colors[this.x] + "\u00A7" + this.colors[this.z];
		this.z++;
		if (this.z >= 17) {
			this.z = 0;
			this.x++;
		}
		if (this.x >= 17)
			fake = "Error 404";
		this.players.add(fake);
	}
	private static User get(String name) {
		for(User to : list) if(to.getName().equals(name)) return to;
		return null;
	}
	public static User getUser(String name) {
		return get(name) == null ? new User(name) : get(name);
		
	}
}
