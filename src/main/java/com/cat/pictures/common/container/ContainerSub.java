package com.cat.pictures.common.container;

import com.cat.pictures.common.container.SubContainer;
import com.cat.pictures.common.gui.GuiContainerSub;
import com.cat.pictures.common.gui.GuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;

public class ContainerSub extends Container {

   public ArrayList layers = new ArrayList();
   @SideOnly(Side.CLIENT)
   public GuiContainerSub gui;
   public ChunkCoordinates coord = null;


   public ContainerSub(EntityPlayer player, SubContainer subContainer) {
      subContainer.container = this;
      subContainer.initContainer();
      this.layers.add(subContainer);
      subContainer.onGuiOpened();
   }

   public ItemStack transferStackInSlot(EntityPlayer player, int index) {
	    {
			ItemStack itemstack = null;
	        Slot slot = (Slot)this.inventorySlots.get(index);
	        int playerInv = -1;
	        for (int i = 0; i < inventorySlots.size(); i++) {
				if(inventorySlots.get(i) instanceof Slot && ((Slot) inventorySlots.get(i)).inventory instanceof InventoryPlayer && playerInv == -1)
					playerInv = i;
	        }
	        if (slot != null && slot.getHasStack() && playerInv != -1)
	        {
	            ItemStack itemstack1 = slot.getStack();
	            itemstack = itemstack1.copy();

	            if (index >= playerInv)
	            {
	                if(!this.mergeItemStack(itemstack1, 0, playerInv, false))
	            		return null;
	            }
	            else if (!this.mergeItemStack(itemstack1, playerInv, inventorySlots.size(), false))
	            {
	                return null;
	            }

	            if (itemstack1.stackSize == 0)
	            {
	                slot.putStack((ItemStack)null);
	            }
	            else
	            {
	                slot.onSlotChanged();
	            }

	            if (itemstack1.stackSize == itemstack.stackSize)
	            {
	                return null;
	            }

	            slot.onPickupFromSlot(player, itemstack1);
	        }

	        return itemstack;
	    }
   }

   public boolean canInteractWith(EntityPlayer player) {
      return true;
   }

   public void detectAndSendChanges() {
      super.detectAndSendChanges();
      Iterator var1 = this.layers.iterator();

      while(var1.hasNext()) {
         SubContainer layer = (SubContainer)var1.next();
         layer.onUpdate();
      }

   }

   public void onContainerClosed(EntityPlayer player) {
      super.onContainerClosed(player);
      Iterator var2 = this.layers.iterator();

      while(var2.hasNext()) {
         SubContainer layer = (SubContainer)var2.next();
         layer.onGuiClosed();
      }

      GuiHandler.openContainers.remove(this);
   }

   protected boolean mergeItemStack(ItemStack stack, int p_75135_2_, int p_75135_3_, boolean p_75135_4_) {
      return p_75135_4_;
   }
}
