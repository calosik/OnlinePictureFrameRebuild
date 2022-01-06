package com.cat.pictures.common.packet;

import com.cat.pictures.common.container.ContainerSub;
import com.cat.pictures.common.packet.CreativeCorePacket;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class GuiUpdatePacket extends CreativeCorePacket {

   NBTTagCompound value;
   private boolean isOpening;
   private int layer;


   public GuiUpdatePacket() {}

   public GuiUpdatePacket(NBTTagCompound value, boolean isOpening, int layer) {
      this.value = value;
      this.isOpening = isOpening;
      this.layer = layer;
   }

   public void writeBytes(ByteBuf bytes) {
      ByteBufUtils.writeTag(bytes, this.value);
      bytes.writeBoolean(this.isOpening);
      bytes.writeInt(this.layer);
   }

   public void readBytes(ByteBuf bytes) {
      this.value = ByteBufUtils.readTag(bytes);
      this.isOpening = bytes.readBoolean();
      this.layer = bytes.readInt();
   }

   @SideOnly(Side.CLIENT)
   public void executeClient(EntityPlayer player) {
      if(player.openContainer instanceof ContainerSub && !this.isOpening) {
         ((ContainerSub)player.openContainer).gui.getLayers().readFromNBT();
      }

   }

   public void executeServer(EntityPlayer player) {}
}
