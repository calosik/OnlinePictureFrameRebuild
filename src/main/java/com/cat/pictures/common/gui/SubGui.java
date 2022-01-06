package com.cat.pictures.common.gui;

import com.cat.pictures.common.container.ContainerControl;
import com.cat.pictures.common.container.SubContainer;
import com.cat.pictures.common.event.CreativeCoreEventBus;
import com.cat.pictures.common.gui.GuiContainerSub;
import com.cat.pictures.common.gui.controls.GuiControl;
import com.cat.pictures.common.gui.event.ControlClickEvent;
import com.cat.pictures.common.gui.event.GuiControlEvent;
import com.cat.pictures.common.packet.GuiControlPacket;
import com.cat.pictures.common.packet.GuiLayerPacket;
import com.cat.pictures.common.packet.PacketHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import javax.vecmath.Vector2d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.nbt.NBTTagCompound;

@SideOnly(Side.CLIENT)
public abstract class SubGui {

   private static Minecraft mc = Minecraft.getMinecraft();
   private final CreativeCoreEventBus eventBus = new CreativeCoreEventBus();
   public GuiContainerSub gui;
   public SubContainer container;
   int width = 372;
   int height = 261;
   public ArrayList controls = new ArrayList();


   public SubGui() {
      this.eventBus.RegisterEventListener(this);
   }

   private int getLayerID() {
      return 0;
   }

   public void openNewLayer(NBTTagCompound nbt, boolean isPacket) {
      if(!isPacket) {
         PacketHandler.sendPacketToServer(new GuiLayerPacket(nbt, this.getLayerID(), false));
      }

   }

   private void closeLayer(NBTTagCompound nbt) {
      this.closeLayer(nbt, false);
   }

   public void closeLayer(NBTTagCompound nbt, boolean isPacket) {
      this.onGuiClose();
      if(!isPacket) {
         PacketHandler.sendPacketToServer(new GuiLayerPacket(nbt, this.getLayerID(), true));
      }

      this.gui.removeLayer(this);
   }

   void initGui() {
      this.createControls();

      Iterator i;
      GuiControl control;
      for(i = this.controls.iterator(); i.hasNext(); control.parent = this) {
         control = (GuiControl)i.next();
      }

      if(this.container != null) {
         for(int var3 = 0; var3 < this.container.controls.size(); ++var3) {
            ((ContainerControl)this.container.controls.get(var3)).init();
            this.controls.add(((ContainerControl)this.container.controls.get(var3)).guiControl);
         }
      }

      for(i = this.controls.iterator(); i.hasNext(); control.parent = this) {
         control = (GuiControl)i.next();
      }

   }

   protected GuiControl getControl(String name) {
      Iterator var2 = this.controls.iterator();

      GuiControl control;
      do {
         if(!var2.hasNext()) {
            return null;
         }

         control = (GuiControl)var2.next();
      } while(!control.name.equalsIgnoreCase(name));

      return control;
   }

   public abstract void createControls();

   void onGuiClose() {
      Iterator var1 = this.controls.iterator();

      while(var1.hasNext()) {
         GuiControl control = (GuiControl)var1.next();
         control.onGuiClose();
      }

      this.eventBus.removeAllEventListeners();
   }

   public void raiseEvent(GuiControlEvent event) {
      this.eventBus.raiseEvent(event);
   }

   public void readFromNBT() {}

   protected void sendPacketToServer(NBTTagCompound nbt) {
      PacketHandler.sendPacketToServer(new GuiControlPacket(this.getLayerID(), 0, nbt));
   }

   void mouseMove() {
      Vector2d mouse = this.getMousePos();
      Iterator var2 = this.controls.iterator();

      while(var2.hasNext()) {
         GuiControl control = (GuiControl)var2.next();
         Vector2d pos = control.getValidPos((int)mouse.x, (int)mouse.y);
         if(control.isInteractable()) {
            control.mouseMove((int)pos.x, (int)pos.y, 0);
         }
      }

   }

   void mouseReleased(int button) {
      Vector2d mouse = this.getMousePos();
      Iterator var3 = this.controls.iterator();

      while(var3.hasNext()) {
         GuiControl control = (GuiControl)var3.next();
         Vector2d pos = control.getValidPos((int)mouse.x, (int)mouse.y);
         if(control.isInteractable()) {
            control.mouseReleased((int)pos.x, (int)pos.y, button);
         }
      }

   }

   void mousePressed(int button) {
      Vector2d mouse = this.getMousePos();
      Iterator var3 = this.controls.iterator();

      while(var3.hasNext()) {
         GuiControl control = (GuiControl)var3.next();
         Vector2d pos = control.getValidPos((int)mouse.x, (int)mouse.y);
         if(control.isInteractable()) {
            if(control.isMouseOver((int)pos.x, (int)pos.y) && control.mousePressed((int)pos.x, (int)pos.y, button)) {
               control.raiseEvent(new ControlClickEvent(control));
               return;
            }

            control.onLoseFocus();
         }
      }

   }

   void mouseDragged() {
      Vector2d mouse = this.getMousePos();
      Iterator var2 = this.controls.iterator();

      GuiControl control;
      Vector2d pos;
      do {
         if(!var2.hasNext()) {
            return;
         }

         control = (GuiControl)var2.next();
         pos = control.getValidPos((int)mouse.x, (int)mouse.y);
      } while(!control.isInteractable() || !control.isMouseOver((int)pos.x, (int)pos.y) || !control.mouseDragged());

   }

   void mouseScrolled() {
      Vector2d mouse = this.getMousePos();
      Iterator var2 = this.controls.iterator();

      GuiControl control;
      Vector2d pos;
      do {
         if(!var2.hasNext()) {
            return;
         }

         control = (GuiControl)var2.next();
         pos = control.getValidPos((int)mouse.x, (int)mouse.y);
      } while(!control.isInteractable() || !control.isMouseOver((int)pos.x, (int)pos.y) || !control.mouseScrolled());

   }

   boolean keyTyped(char character, int key) {
      Iterator var3 = this.controls.iterator();

      GuiControl control;
      do {
         if(!var3.hasNext()) {
            if(key != 1 && key != mc.gameSettings.keyBindInventory.getKeyCode()) {
               return false;
            }

            this.closeLayer(new NBTTagCompound());
            mc.thePlayer.closeScreen();
            return true;
         }

         control = (GuiControl)var3.next();
      } while(!control.isInteractable() || !control.onKeyPressed(character, key));

      return true;
   }

   public Vector2d getMousePos() {
      return GuiControl.getMousePos(this.width, this.height);
   }

   void drawForeground(FontRenderer fontRenderer) {
      for(int i = this.controls.size() - 1; i >= 0; --i) {
         GuiControl control = (GuiControl)this.controls.get(i);
         if(control.visible && control.posY + control.height >= 0 && control.posY <= this.height) {
            control.renderControl(fontRenderer);
         }
      }

   }

}
