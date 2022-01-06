package com.cat.pictures.common.gui;

import com.cat.pictures.common.container.ContainerSub;
import com.cat.pictures.common.container.SubContainer;
import com.cat.pictures.common.gui.SubGui;
import java.util.Objects;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiContainerSub extends GuiContainer {

   private static final ResourceLocation background = new ResourceLocation("catpictures:textures/gui/catgui.png");
   private final SubGui gui;


   GuiContainerSub(EntityPlayer player, SubGui gui, SubContainer container) {
      super(new ContainerSub(player, container));
      this.width = 372;
      this.height = 261;
      ((ContainerSub)this.inventorySlots).gui = this;
      gui.container = container;
      gui.gui = this;
      this.gui = gui;
      gui.initGui();
   }

   public SubGui getLayers() {
      return this.gui;
   }

   void removeLayer(SubGui layer) {
      ((ContainerSub)this.inventorySlots).layers.remove(layer.container);
   }

   public void drawDefaultBackground() {}

   public void drawGuiContainerForegroundLayer(int par1, int par2) {
      GL11.glPushMatrix();
      int k = this.guiLeft;
      int l = this.guiTop;
      GL11.glTranslatef((float)(-k), (float)(-l), 0.0F);
      this.drawWorldBackground(0);
      int offX = (this.width - 372) / 2 - k;
      int offY = (this.height - 261) / 2 - l;
      GL11.glTranslatef((float)k, (float)l, 0.0F);
      GL11.glTranslatef((float)offX, (float)offY, 0.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.mc.renderEngine.bindTexture(background);
      func_146110_a(0, 0, 0.0F, 0.0F, 372, 261, 372.0F, 261.0F);
      this.gui.drawForeground(this.fontRendererObj);
      GL11.glTranslatef((float)(-offX), (float)(-offY), 0.0F);
      GL11.glEnable(2896);
      GL11.glPopMatrix();
   }

   public void keyTyped(char character, int key) {
      if(this.gui != null && !this.gui.keyTyped(character, key)) {
         super.keyTyped(character, key);
      }

   }

   public void handleInput() {
      if(Mouse.isCreated()) {
         int j = Mouse.getDWheel();
         if(j != 0) {
            ((SubGui)Objects.requireNonNull(this.gui)).mouseScrolled();
         }

         ((SubGui)Objects.requireNonNull(this.gui)).mouseMove();
      }

      super.handleInput();
   }

   public void mouseClicked(int x, int y, int button) {
      super.mouseClicked(x, y, button);
      ((SubGui)Objects.requireNonNull(this.gui)).mousePressed(button);
   }

   public void mouseClickMove(int x, int y, int button, long time) {
      super.mouseClickMove(x, y, button, time);
      ((SubGui)Objects.requireNonNull(this.gui)).mouseDragged();
   }

   protected void mouseMovedOrUp(int x, int y, int button) {
      super.mouseMovedOrUp(x, y, button);
      this.onMouseReleased(button);
   }

   private void onMouseReleased(int button) {
      ((SubGui)Objects.requireNonNull(this.gui)).mouseReleased(button);
   }

   public void onGuiClosed() {
      this.gui.onGuiClose();
   }

   protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {}

}
