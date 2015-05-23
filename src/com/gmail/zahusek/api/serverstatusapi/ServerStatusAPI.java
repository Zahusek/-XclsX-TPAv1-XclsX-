package com.gmail.zahusek.api.serverstatusapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.io.netty.channel.Channel;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.zahusek.protocols.Reflection;
import com.gmail.zahusek.protocols.Reflection.ConstructorInvoker;
import com.gmail.zahusek.protocols.Reflection.FieldAccessor;
import com.gmail.zahusek.protocols.TinyProtocol.PacketListener;

public class ServerStatusAPI extends PacketListener {

	private Class<?> serverInfoClass = Reflection
			.getClass("{nms}.PacketStatusOutServerInfo");
	private Class<Object> serverPingClass = Reflection
			.getUntypedClass("{nms}.ServerPing");
	private FieldAccessor<Object> serverPing = Reflection.getField(
			serverInfoClass, serverPingClass, 0);

	private Class<Object> getplayerClass = Reflection
			.getUntypedClass("{nms}.ServerPingPlayerSample");
	private FieldAccessor<Object> player = Reflection.getField(serverPingClass,
			getplayerClass, 0);

	private Class<Object> getMotdClass = Reflection
			.getUntypedClass("{nms}.IChatBaseComponent");
	private Class<Object> setMotdClass = Reflection
			.getUntypedClass("{nms}.ChatComponentText");
	private FieldAccessor<Object> motdClass = Reflection.getField(
			serverPingClass, getMotdClass, 0);
	private ConstructorInvoker motdInvoker = Reflection.getConstructor(
			setMotdClass, String.class);

	private Class<Object> getServerClass = Reflection
			.getUntypedClass("{nms}.ServerPingServerData");
	private FieldAccessor<Object> serverClass = Reflection.getField(
			serverPingClass, getServerClass, 0);
	private ConstructorInvoker serverInvoker = Reflection.getConstructor(
			getServerClass, String.class, int.class);

	private String motd = Bukkit.getServer().getMotd(), protocolName;
	private Integer online, max, protocolVersion;
	private List<String> gameprofiles = new ArrayList<String>();

	public void setMotd(String motd) {
		this.motd = motd;
	}

	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public void setMaxOnline(int maxonline) {
		this.max = maxonline;
	}

	public void setProtocolVersion(int protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	public void setGameProfiles(List<String> gameprofiles) {
		this.gameprofiles = gameprofiles;
	}

	public void setGameProfiles(String... gameprofiles) {
		this.gameprofiles = new ArrayList<String>(Arrays.asList(gameprofiles));
	}

	@Override
	public Object onPacketOutAsync(Player reciever, Channel channel,
			Object packet) {
		if (this.serverInfoClass.isInstance(packet)) {
			
			Object ping = this.serverPing.get(packet);
			Object playersample = this.player.get(ping);
			Object serverdata = this.serverClass.get(ping);

			Object icbc = Reflection.getMethod(ping.getClass(), "a").invoke(
					ping);
			
			Class<?> cs = Reflection.getClass("{nms}.ChatSerializer");
			String motd = (String) Reflection.getMethod(cs, "a",
					Reflection.getClass("{nms}.IChatBaseComponent")).invoke(cs,
					icbc);
			
			int max = (int) Reflection.getMethod(playersample.getClass(), "a")
					.invoke(playersample);
			int online = (int) Reflection.getMethod(playersample.getClass(),
					"b").invoke(playersample);
			String protocolName = (String) Reflection.getMethod(
					serverdata.getClass(), "a").invoke(serverdata);
			int protocolVersion = (int) Reflection.getMethod(
					serverdata.getClass(), "b").invoke(serverdata);
			GameProfile[] profiles = (GameProfile[]) Reflection.getMethod(
					playersample.getClass(), "c").invoke(playersample);

			Reflection.getField(playersample.getClass(), "a", int.class).set(
					playersample, this.max == null ? max : this.max);
			Reflection.getField(playersample.getClass(), "b", int.class).set(
					playersample, this.online == null ? online : this.online);
			
			GameProfile[] gp = new GameProfile[this.gameprofiles.size()];
			for (int i = 0; i < this.gameprofiles.size(); i++)
				gp[i] = new GameProfile("-1", this.gameprofiles.get(i));

			Reflection.getField(playersample.getClass(), "c",
					GameProfile[].class).set(playersample,
					this.gameprofiles == null ? profiles : gp);

			this.motdClass.set(ping, this.motdInvoker
					.invoke(this.motd == null ? motd : this.motd));
			this.serverClass.set(ping, this.serverInvoker.invoke(
					this.protocolName == null ? protocolName
							: this.protocolName,
					this.protocolVersion == null ? protocolVersion
							: this.protocolVersion));
			return packet;
		}
		return packet;
	}
}
