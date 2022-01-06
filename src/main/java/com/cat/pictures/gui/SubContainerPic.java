package com.cat.pictures.gui;

import com.cat.pictures.common.container.SubContainer;
import com.cat.pictures.common.tileentity.TileEntityPicFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class SubContainerPic extends SubContainer {

   private final TileEntityPicFrame frame;


   public SubContainerPic(TileEntityPicFrame frame, EntityPlayer player) {
      super(player);
      this.frame = frame;
   }

   public void createControls() {}

   public void onGuiPacket(int controlID, NBTTagCompound nbt, EntityPlayer player) {
      if(controlID == 0 && player != null) {
         this.frame.url = nbt.getString("url");
         this.frame.sizeX = nbt.getFloat("x");
         this.frame.sizeY = nbt.getFloat("y");
         this.frame.renderDistance = nbt.getInteger("render");
         this.frame.posX = nbt.getByte("posX");
         this.frame.posY = nbt.getByte("posY");
         this.frame.transparency = nbt.getFloat("transparency");
         this.frame.visibleFrame = nbt.getBoolean("visibleFrame");
         this.frame.flipped = nbt.getBoolean("flipped");
         this.frame.headPos = nbt.getByte("headPos");
         this.frame.updateBlock();
      }

   }
}
