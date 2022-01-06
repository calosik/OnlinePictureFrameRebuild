package com.cat.pictures.common.gui;

import com.cat.pictures.common.container.SubContainer;
import com.cat.pictures.common.gui.SubGui;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IGuiCreator {

   @SideOnly(Side.CLIENT)
   SubGui getGui(EntityPlayer var1, ItemStack var2, World var3, int var4, int var5, int var6);

   SubContainer getContainer(EntityPlayer var1, ItemStack var2, World var3, int var4, int var5, int var6);
}
