package com.cat.pictures.common.packet;

import com.cat.pictures.common.container.ContainerSub;
import com.cat.pictures.common.container.SubContainer;
import com.cat.pictures.common.packet.GuiUpdatePacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class GuiControlPacket extends GuiUpdatePacket {

   private int control;
   private int layer;


   public GuiControlPacket() {}

   public GuiControlPacket(int layer, int control, NBTTagCompound value) {
      super(value, false, layer);
      this.control = control;
      this.layer = layer;
   }

   public void writeBytes(ByteBuf bytes) {
      super.writeBytes(bytes);
      bytes.writeInt(this.control);
   }

   public void readBytes(ByteBuf bytes) {
      super.readBytes(bytes);
      this.control = bytes.readInt();
   }

   public void executeClient(EntityPlayer player) {}

   public void executeServer(EntityPlayer player) {
      if(player.openContainer instanceof ContainerSub) {
         ((SubContainer)((ContainerSub)player.openContainer).layers.get(this.layer)).onGuiPacket(this.control, this.value, player);
      }

   }
}
