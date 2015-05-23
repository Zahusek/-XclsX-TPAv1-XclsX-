package com.gmail.zahusek.packets;

import org.bukkit.entity.Player;

import com.gmail.zahusek.protocols.TinyProtocol;

public abstract class AbstractPacket {
	
	protected Object packet;
	protected TinyProtocol tinyprotocol;

	protected AbstractPacket(TinyProtocol tinyprotocol) {
		this.tinyprotocol = tinyprotocol;
	}
	public void setPacket(Object p) {
		this.packet = p;
	}
	
	public void sendPacket(Player receiver) { tinyprotocol.sendPacket(receiver, this.packet); }
	
	public void recievePacket(Player sender) { tinyprotocol.receivePacket(sender, this.packet); }
}
