package com.cat.pictures.common.packet;

import com.cat.pictures.common.packet.CreativeMessageHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PacketReciever implements IMessageHandler<CreativeMessageHandler, IMessage> {
   @SideOnly(Side.CLIENT)
   public void executeClient(IMessage message) {
      if (message instanceof CreativeMessageHandler && ((CreativeMessageHandler)message).packet != null)
         ((CreativeMessageHandler)message).packet.executeClient((EntityPlayer)(Minecraft.getMinecraft()).thePlayer);
   }

   public CreativeMessageHandler onMessage(CreativeMessageHandler message, MessageContext ctx) {
      if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
         executeClient(message);
      } else if (message != null && message.packet != null) {
         message.packet.executeServer((EntityPlayer)(ctx.getServerHandler()).playerEntity);
      }
      return null;
   }
}
