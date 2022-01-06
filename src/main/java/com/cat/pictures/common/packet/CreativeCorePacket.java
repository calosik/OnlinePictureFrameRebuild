package com.cat.pictures.common.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public abstract class CreativeCorePacket {

   private static final HashMap packets = new HashMap();


   public static void registerPacket(Class PacketClass, String id) {
      packets.put(id, PacketClass);
   }

   static Class getClassByID(String id) {
      return (Class)packets.get(id);
   }

   private static String getIDByClass(Class packet) {
      Iterator var1 = packets.entrySet().iterator();

      Entry entry;
      do {
         if(!var1.hasNext()) {
            return "";
         }

         entry = (Entry)var1.next();
      } while(entry.getValue() != packet);

      return (String)entry.getKey();
   }

   static String getIDByClass(CreativeCorePacket packet) {
      return getIDByClass(packet.getClass());
   }

   public abstract void writeBytes(ByteBuf var1);

   public abstract void readBytes(ByteBuf var1);

   @SideOnly(Side.CLIENT)
   public abstract void executeClient(EntityPlayer var1);

   public abstract void executeServer(EntityPlayer var1);

   public static void writeNBT(ByteBuf buf, NBTTagCompound nbt) {
      ByteBufUtils.writeTag(buf, nbt);
   }

   public static NBTTagCompound readNBT(ByteBuf buf) {
      return ByteBufUtils.readTag(buf);
   }

}
