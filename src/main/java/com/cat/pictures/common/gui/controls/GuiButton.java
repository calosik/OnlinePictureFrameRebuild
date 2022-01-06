package com.cat.pictures.common.gui.controls;

import com.cat.pictures.common.gui.controls.GuiControl;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiButton extends GuiControl {

   private static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
   String caption;


   public GuiButton(String name, String caption, int x, int y, int width) {
      super(name, x, y, width, 21);
      this.caption = caption;
   }

   public void drawControl(FontRenderer renderer) {
      Gui.drawRect(0, -1, this.width, this.height, 905969664);
      int l = 14737632;
      if(this.isMouseOver()) {
         Gui.drawRect(0, -1, this.width, this.height, 579863552);
         l = 12737632;
      }

      GL11.glEnable(3042);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      GL11.glBlendFunc(770, 771);
      renderer.drawString(this.caption, this.width / 2 - renderer.getStringWidth(this.caption) / 2, (this.height - 8) / 2, l);
   }

   public boolean mousePressed(int posX, int posY, int button) {
      playSound();
      return true;
   }

}
