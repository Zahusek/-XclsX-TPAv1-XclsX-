package com.gmail.zahusek.packets;

import com.gmail.zahusek.protocols.Reflection;
import com.gmail.zahusek.protocols.Reflection.FieldAccessor;
import com.gmail.zahusek.protocols.TinyProtocol;

public class Chat extends AbstractPacket {
	
	private Class<?> packet = Reflection.getClass("{nms}.PacketPlayOutChat");
	
	private Class<Object> chatbase = Reflection.getUntypedClass("{nms}.IChatBaseComponent");
	private FieldAccessor<Object> chat = Reflection.getField(packet, chatbase, 0);
	private FieldAccessor<Boolean> mode = Reflection.getField(packet, boolean.class, 0);
	
	public Chat(Object chat, boolean mode) {
		super(TinyProtocol.getTinyProtocol());
		Object handle = Reflection.getConstructor("{nms}.PacketPlayOutChat").invoke();
		this.chat.set(handle, chat);
		this.mode.set(handle, mode);
		setPacket(handle);
	}
	
	public Chat(Object chat) {
		super(TinyProtocol.getTinyProtocol());
		Object handle = Reflection.getConstructor("{nms}.PacketPlayOutChat").invoke();
		this.chat.set(handle, chat);
		this.mode.set(handle, true);
		setPacket(handle);
	}
}
