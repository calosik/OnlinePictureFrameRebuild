package com.cat.pictures.common.container;

import com.cat.pictures.common.container.SubContainer;
import com.cat.pictures.common.gui.controls.GuiControl;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ContainerControl {

   SubContainer parent;
   @SideOnly(Side.CLIENT)
   public GuiControl guiControl;


   @SideOnly(Side.CLIENT)
   public void init() {
      if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
         this.guiControl = this.createGuiControl();
      }

   }

   void detectChange() {}

   @SideOnly(Side.CLIENT)
   public abstract GuiControl createGuiControl();

   void onGuiClose() {}

   public abstract void readFromNBT(NBTTagCompound var1);
}
