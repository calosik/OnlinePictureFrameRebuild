package com.cat.pictures.common.packet;

import com.cat.pictures.common.container.ContainerControl;
import com.cat.pictures.common.container.ContainerSub;
import com.cat.pictures.common.container.SubContainer;
import com.cat.pictures.common.packet.CreativeCorePacket;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerControlUpdatePacket extends CreativeCorePacket {

   private NBTTagCompound value;
   private int id;
   private int layer;


   public ContainerControlUpdatePacket() {}

   public ContainerControlUpdatePacket(int layer, int id, NBTTagCompound value) {
      this.value = value;
      this.id = id;
      this.layer = layer;
   }

   public void writeBytes(ByteBuf bytes) {
      ByteBufUtils.writeTag(bytes, this.value);
      bytes.writeInt(this.id);
      bytes.writeInt(this.layer);
   }

   public void readBytes(ByteBuf bytes) {
      this.value = ByteBufUtils.readTag(bytes);
      this.id = bytes.readInt();
      this.layer = bytes.readInt();
   }

   @SideOnly(Side.CLIENT)
   public void executeClient(EntityPlayer player) {
      if(player.openContainer instanceof ContainerSub && ((SubContainer)((ContainerSub)player.openContainer).layers.get(this.layer)).controls.size() > this.id) {
         ((ContainerControl)((SubContainer)((ContainerSub)player.openContainer).layers.get(this.layer)).controls.get(this.id)).readFromNBT(this.value);
      }

   }

   public void executeServer(EntityPlayer player) {
      if(player.openContainer instanceof ContainerSub && ((SubContainer)((ContainerSub)player.openContainer).layers.get(this.layer)).controls.size() > this.id) {
         ((ContainerControl)((SubContainer)((ContainerSub)player.openContainer).layers.get(this.layer)).controls.get(this.id)).readFromNBT(this.value);
      }

   }
}
