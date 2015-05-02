package com.gmail.zahusek.packets;

import org.bukkit.entity.Player;

import com.gmail.zahusek.protocol.TinyProtocol;

public abstract class AbstractPacket {
	
	protected Object packet;
	protected TinyProtocol tinyprotocol;

	protected AbstractPacket(Object packet, TinyProtocol tinyprotocol) {
		this.packet = packet;
		this.tinyprotocol = tinyprotocol;
	}
	
	public Object getHandle() { return this.packet; }
	
	public void sendPacket(Player receiver) { tinyprotocol.sendPacket(receiver, getHandle()); }
	
	public void recievePacket(Player sender) { tinyprotocol.receivePacket(sender, getHandle()); }
}
