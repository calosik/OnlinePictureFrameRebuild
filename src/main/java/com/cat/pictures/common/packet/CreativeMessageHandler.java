package com.cat.pictures.common.packet;

import com.cat.pictures.common.packet.CreativeCorePacket;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class CreativeMessageHandler implements IMessage {

   public CreativeCorePacket packet = null;


   public CreativeMessageHandler() {}

   public CreativeMessageHandler(CreativeCorePacket packet) {
      this.packet = packet;
   }

   public void fromBytes(ByteBuf buf) {
      String id = ByteBufUtils.readUTF8String(buf);
      Class PacketClass = CreativeCorePacket.getClassByID(id);
      this.packet = null;

      try {
         this.packet = (CreativeCorePacket)PacketClass.getConstructor(new Class[0]).newInstance(new Object[0]);
      } catch (Exception var5) {
         ;
      }

      if(this.packet != null) {
         this.packet.readBytes(buf);
      }

   }

   public void toBytes(ByteBuf buf) {
      ByteBufUtils.writeUTF8String(buf, CreativeCorePacket.getIDByClass(this.packet));
      this.packet.writeBytes(buf);
   }
}
