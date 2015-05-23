package com.gmail.zahusek.packets;

import java.util.Collection;
import java.util.List;

import com.gmail.zahusek.protocols.Reflection;
import com.gmail.zahusek.protocols.Reflection.FieldAccessor;
import com.gmail.zahusek.protocols.TinyProtocol;

public class ScoreboardTeam extends AbstractPacket {
	
	private Class<?> packet = Reflection.getClass("{nms}.PacketPlayOutScoreboardTeam");
	private FieldAccessor<String> name = Reflection.getField(packet, String.class, 0);
	private FieldAccessor<String> displayname = Reflection.getField(packet, String.class, 1);
	private FieldAccessor<String> prefix = Reflection.getField(packet, String.class, 2);
	private FieldAccessor<String> suffix = Reflection.getField(packet, String.class, 3);
	@SuppressWarnings("rawtypes")
	private FieldAccessor<Collection> players = Reflection.getField(packet, Collection.class, 0);
	private FieldAccessor<Integer> variant = Reflection.getField(packet, int.class, 0);
	private FieldAccessor<Integer> accessories = Reflection.getField(packet, int.class, 1);
	
	//generally
	public ScoreboardTeam(String name, String displayname, String prefix, String suffix, List<String> players, int variant, int accessories) {
		super(TinyProtocol.getTinyProtocol());
		Object handle = Reflection.getConstructor("{nms}.PacketPlayOutScoreboardTeam").invoke();
		this.name.set(handle, name);
		this.displayname.set(handle, displayname);
		this.prefix.set(handle, prefix);
		this.suffix.set(handle, suffix);
		this.players.set(handle, players);
		this.variant.set(handle, variant);
		this.accessories.set(handle, accessories);
		setPacket(handle);
	}
	//constructor to add and leave team
	public ScoreboardTeam(String name, List<String> players, int variant) {
		super(TinyProtocol.getTinyProtocol());
		Object handle = Reflection.getConstructor("{nms}.PacketPlayOutScoreboardTeam").invoke();
		this.name.set(handle, name);
		this.players.set(handle, players);
		this.variant.set(handle, variant);
		setPacket(handle);
	}
	//create and remove team
	public ScoreboardTeam(String name, int variant) {
		super(TinyProtocol.getTinyProtocol());
		Object handle = Reflection.getConstructor("{nms}.PacketPlayOutScoreboardTeam").invoke();
		this.name.set(handle, name);
		this.variant.set(handle, variant);
		setPacket(handle);
	}
	//update team (suffix, prefix and displayname)
	public ScoreboardTeam(String name, String displayname, String prefix, String suffix, int accessories) {
		super(TinyProtocol.getTinyProtocol());
		Object handle = Reflection.getConstructor("{nms}.PacketPlayOutScoreboardTeam").invoke();
		this.name.set(handle, name);
		this.displayname.set(handle, displayname);
		this.prefix.set(handle, prefix);
		this.suffix.set(handle, suffix);
		this.variant.set(handle, 2);
		this.accessories.set(handle, accessories);
		setPacket(handle);
	}
}
