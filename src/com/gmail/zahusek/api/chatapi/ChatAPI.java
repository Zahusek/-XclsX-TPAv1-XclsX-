package com.gmail.zahusek.api.chatapi;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.zahusek.packets.Chat;
import com.gmail.zahusek.protocols.Reflection;
import com.gmail.zahusek.protocols.Reflection.FieldAccessor;

public class ChatAPI {
	
	private Class<Object> icbcClass = Reflection.getUntypedClass("{nms}.IChatBaseComponent");
	private Class<Object> cbcClass = Reflection.getUntypedClass("{nms}.ChatBaseComponent");
	@SuppressWarnings("rawtypes")
	private FieldAccessor<List> listField = Reflection.getField(cbcClass, List.class, 0);
	
	private Class<Object> cctClass = Reflection.getUntypedClass("{nms}.ChatComponentText");
	
	private Class<Object> cmClass = Reflection.getUntypedClass("{nms}.ChatModifier");
	private FieldAccessor<Object> cmField = Reflection.getField(cbcClass, cmClass, 0);
	
	private Class<Object> ccClass = Reflection.getUntypedClass("{nms}.ChatClickable");
	private FieldAccessor<Object> ccField = Reflection.getField(cmClass, ccClass, 0);
	private Class<Object> ecaClass = Reflection.getUntypedClass("{nms}.EnumClickAction");
	private Object[] enumClick = ecaClass.getEnumConstants();
	
	private Class<Object> chClass = Reflection.getUntypedClass("{nms}.ChatHoverable");
	private FieldAccessor<Object> chField = Reflection.getField(cmClass, chClass, 0);
	private Class<Object> ehaClass = Reflection.getUntypedClass("{nms}.EnumHoverAction");
	private Object[] enumHover = ehaClass.getEnumConstants();
	
	private Class<Object> cisClass = Reflection.getUntypedClass("{obc}.inventory.CraftItemStack");
	private Class<Object> nbttcClass = Reflection.getUntypedClass("{nms}.NBTTagCompound");
	private Class<Object> nisClass = Reflection.getUntypedClass("{nms}.ItemStack");
	
	private Object i;
	private Object c;
	
	public ChatAPI(String s) {
		this.i = text(s);
		this.cmField.set(this.i, Reflection.getConstructor(this.cmClass).invoke());
		this.c = this.cmField.get(this.i);
	}
	public ChatAPI() {
		this.i = text("");
		this.cmField.set(this.i, Reflection.getConstructor(this.cmClass).invoke());
		this.c = this.cmField.get(this.i);
	}
	@SuppressWarnings("unchecked")
	public ChatAPI add(Object... ca){
		for(Object o : ca)
		if(o instanceof String)
			this.listField.get(this.i).add(new ChatAPI(((String)o)).get());
		else if(o instanceof ChatAPI)
			this.listField.get(this.i).add(((ChatAPI)o).i);
		return this;
	}
	public ChatAPI addClick(Click eca, String s) {
		this.ccField.set(this.c, Reflection.getConstructor(this.ccClass, this.ecaClass, String.class).invoke(enumClick[eca.value], ChatColor.translateAlternateColorCodes('&', s)));
		return this;
	}
	public ChatAPI addHover(String... l) {
		ItemStack is = new ItemStack(Material.CACTUS){{
			ItemMeta im = getItemMeta();
			im.setDisplayName(l[0] == null ? "" : ChatColor.translateAlternateColorCodes('&', l[0]));
			List<String> list = new ArrayList<String>();
			for(int i = 1; i < l.length; i++)
				list.add(l[i] == null ? "" : ChatColor.translateAlternateColorCodes('&', l[i]));
			im.setLore(list);
			setItemMeta(im);
		}};
		Object cis = Reflection.getMethod(this.cisClass, "asNMSCopy", ItemStack.class).invoke(this.cisClass, is);
		Object nbt = Reflection.getConstructor(this.nbttcClass).invoke();
		Reflection.getMethod(this.nisClass, "save", this.nbttcClass).invoke(cis, nbt);
		this.chField.set(this.c, Reflection.getConstructor(this.chClass, this.ehaClass, this.icbcClass).invoke(enumHover[2], text(nbt.toString())));
		return this;
	}
	
	private Object text(String s){ return Reflection.getConstructor(this.cctClass, String.class).invoke(s); }
	private Object get(){return this.i;}
	
	public void send(Player p) { new Chat(i).sendPacket(p); }
	
	public enum Click { OPEN_URL(0), RUN_COMMAND(2), SUGGEST_COMMAND(3);
		final int value;
		private Click(int value) { this.value = value; }
	}
}
