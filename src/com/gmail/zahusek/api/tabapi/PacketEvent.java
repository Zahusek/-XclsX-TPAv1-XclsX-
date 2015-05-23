package com.gmail.zahusek.api.tabapi;

import net.minecraft.util.io.netty.channel.Channel;

import org.bukkit.entity.Player;

import com.gmail.zahusek.protocols.Reflection;
import com.gmail.zahusek.protocols.Reflection.FieldAccessor;
import com.gmail.zahusek.protocols.TinyProtocol.PacketListener;

public class PacketEvent extends PacketListener {
	
	private Class<?> packet = Reflection.getClass("{nms}.PacketPlayOutPlayerInfo");
	private FieldAccessor<String> name = Reflection.getField(packet, String.class, 0);

	@Override
	public Object onPacketOutAsync(Player reciever, Channel channel,
			Object packet) {
		if(!this.packet.isInstance(packet)) return packet;
		
		String name = this.name.get(packet);
		if(name.startsWith("$")) this.name.set(packet, name.substring(1));
		else return null;
		
		return super.onPacketOutAsync(reciever, channel, packet);
	}
}
