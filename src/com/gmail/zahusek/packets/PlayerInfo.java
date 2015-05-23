package com.gmail.zahusek.packets;

import com.gmail.zahusek.protocols.Reflection;
import com.gmail.zahusek.protocols.Reflection.FieldAccessor;
import com.gmail.zahusek.protocols.TinyProtocol;

public class PlayerInfo extends AbstractPacket {
	
	private Class<?> packet = Reflection.getClass("{nms}.PacketPlayOutPlayerInfo");
	private FieldAccessor<String> name = Reflection.getField(packet, String.class, 0);
	private FieldAccessor<Boolean> online = Reflection.getField(packet, boolean.class, 0);
	private FieldAccessor<Integer> ping = Reflection.getField(packet, int.class, 0);
	
	public PlayerInfo(String name, boolean online, int ping) {
		super(TinyProtocol.getTinyProtocol());
		Object handle = Reflection.getConstructor("{nms}.PacketPlayOutPlayerInfo").invoke();
		this.name.set(handle, name);
		this.online.set(handle, online);
		this.ping.set(handle, ping);
		setPacket(handle);
	}
	
	public PlayerInfo(String name, boolean online) {
		super(TinyProtocol.getTinyProtocol());
		Object handle = Reflection.getConstructor("{nms}.PacketPlayOutPlayerInfo").invoke();
		this.name.set(handle, name);
		this.online.set(handle, online);
		this.ping.set(handle, 9999);
		setPacket(handle);
	}
}
