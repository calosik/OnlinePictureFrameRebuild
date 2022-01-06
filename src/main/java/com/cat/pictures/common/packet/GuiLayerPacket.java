package com.cat.pictures.common.packet;

import com.cat.pictures.common.container.ContainerSub;
import com.cat.pictures.common.container.SubContainer;
import com.cat.pictures.common.packet.CreativeCorePacket;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class GuiLayerPacket extends CreativeCorePacket {

   public NBTTagCompound nbt;
   private int layer;
   private boolean closed;


   public GuiLayerPacket() {}

   public GuiLayerPacket(NBTTagCompound nbt, int layer, boolean closed) {
      this.nbt = nbt;
      this.layer = layer;
      this.closed = closed;
   }

   public void writeBytes(ByteBuf buf) {
      ByteBufUtils.writeTag(buf, this.nbt);
      buf.writeInt(this.layer);
      buf.writeBoolean(this.closed);
   }

   public void readBytes(ByteBuf buf) {
      this.nbt = ByteBufUtils.readTag(buf);
      this.layer = buf.readInt();
      this.closed = buf.readBoolean();
   }

   @SideOnly(Side.CLIENT)
   public void executeClient(EntityPlayer player) {
      if(player.openContainer instanceof ContainerSub) {
         if(this.closed) {
            ((ContainerSub)player.openContainer).gui.getLayers().closeLayer(this.nbt, true);
         } else {
            ((ContainerSub)player.openContainer).gui.getLayers().openNewLayer(this.nbt, true);
         }
      }

   }

   public void executeServer(EntityPlayer player) {
      if(player.openContainer instanceof ContainerSub) {
         if(this.closed) {
            ((SubContainer)((ContainerSub)player.openContainer).layers.get(this.layer)).closeLayer(this.nbt, true);
         } else {
            ((SubContainer)((ContainerSub)player.openContainer).layers.get(this.layer)).openNewLayer(this.nbt, true);
         }
      }

   }
}
