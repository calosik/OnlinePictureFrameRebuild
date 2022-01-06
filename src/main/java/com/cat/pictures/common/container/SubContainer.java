package com.cat.pictures.common.container;

import com.cat.pictures.common.container.ContainerControl;
import com.cat.pictures.common.container.ContainerSub;
import com.cat.pictures.common.event.CreativeCoreEventBus;
import com.cat.pictures.common.packet.GuiLayerPacket;
import com.cat.pictures.common.packet.GuiUpdatePacket;
import com.cat.pictures.common.packet.PacketHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public abstract class SubContainer {

   public EntityPlayer player;
   public ContainerSub container;
   private final CreativeCoreEventBus eventBus;
   public ArrayList controls = new ArrayList();


   public SubContainer(EntityPlayer player) {
      this.player = player;
      this.eventBus = new CreativeCoreEventBus();
      this.eventBus.RegisterEventListener(this);
   }

   private int getLayerID() {
      return this.container.layers.indexOf(this);
   }

   public void closeLayer(NBTTagCompound nbt, boolean isPacket) {
      if(!isPacket) {
         PacketHandler.sendPacketToServer(new GuiLayerPacket(nbt, this.getLayerID(), true));
      }

      this.onGuiClosed();
      this.container.layers.remove(this);
   }

   public void openNewLayer(NBTTagCompound nbt, boolean isPacket) {
      if(!isPacket) {
         PacketHandler.sendPacketToServer(new GuiLayerPacket(nbt, this.getLayerID(), false));
      }

   }

   void initContainer() {
      this.createControls();
      this.refreshControls();
   }

   private void refreshControls() {
      ContainerControl control;
      for(Iterator var1 = this.controls.iterator(); var1.hasNext(); control.parent = this) {
         control = (ContainerControl)var1.next();
      }

   }

   public abstract void createControls();

   public abstract void onGuiPacket(int var1, NBTTagCompound var2, EntityPlayer var3);

   void onGuiClosed() {
      Iterator var1 = this.controls.iterator();

      while(var1.hasNext()) {
         ContainerControl control = (ContainerControl)var1.next();
         control.onGuiClose();
      }

      this.eventBus.removeAllEventListeners();
   }

   void onGuiOpened() {
      if(FMLCommonHandler.instance().getEffectiveSide().isServer()) {
         PacketHandler.sendPacketToPlayer(new GuiUpdatePacket(new NBTTagCompound(), true, this.getLayerID()), (EntityPlayerMP)this.player);
      }

   }

   void onUpdate() {
      Iterator var1 = this.controls.iterator();

      while(var1.hasNext()) {
         ContainerControl control = (ContainerControl)var1.next();
         control.detectChange();
      }

   }
}
