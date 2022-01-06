package com.cat.pictures.common.gui;

import com.cat.pictures.common.gui.SubGui;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public abstract class CustomGuiHandler {

   @SideOnly(Side.CLIENT)
   public abstract SubGui getGui(EntityPlayer var1, NBTTagCompound var2);
}
