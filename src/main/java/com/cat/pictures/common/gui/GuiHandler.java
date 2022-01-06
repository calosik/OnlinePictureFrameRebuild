package com.cat.pictures.common.gui;

import com.cat.pictures.common.container.ContainerSub;
import com.cat.pictures.common.gui.GuiContainerSub;
import com.cat.pictures.common.gui.IGuiCreator;
import cpw.mods.fml.common.network.IGuiHandler;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

   public static ArrayList openContainers = new ArrayList();


   public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      if(ID == 0) {
         Block block = world.getBlock(x, y, z);
         ContainerSub container = new ContainerSub(player, ((IGuiCreator)block).getContainer(player, (ItemStack)null, world, x, y, z));
         container.coord = new ChunkCoordinates(x, y, z);
         openContainers.add(container);
         return container;
      } else {
         return null;
      }
   }

   public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      if(ID == 0) {
         Block block = world.getBlock(x, y, z);
         if(block instanceof IGuiCreator) {
            return new GuiContainerSub(player, ((IGuiCreator)block).getGui(player, (ItemStack)null, world, x, y, z), ((IGuiCreator)block).getContainer(player, (ItemStack)null, world, x, y, z));
         }
      }

      return null;
   }

}
