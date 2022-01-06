package com.cat.pictures.common.gui.controls;

import com.cat.pictures.client.rendering.RenderHelper2D;
import com.cat.pictures.common.gui.controls.GuiControl;
import javax.vecmath.Vector4d;
import net.minecraft.client.gui.FontRenderer;

public class GuiCheckBox extends GuiControl {

   private static final int checkBoxWidth = 7;
   public boolean value;
   private final String title;


   public GuiCheckBox(String name, String title, int x, int y, boolean value) {
      super(name, x, y, 7 + mc.fontRenderer.getStringWidth(title) + 3, 15);
      this.value = value;
      this.title = title;
   }

   public GuiCheckBox(String name, int x, int y, boolean value) {
      this(name, name, x, y, value);
   }

   public void drawControl(FontRenderer renderer) {
      boolean yoffset = true;
      Vector4d black = new Vector4d(0.0D, 0.0D, 0.0D, 255.0D);
      RenderHelper2D.drawGradientRect(0, 3, 7, 10, black, black);
      Vector4d color = new Vector4d(140.0D, 140.0D, 140.0D, 255.0D);
      RenderHelper2D.drawGradientRect(1, 4, 6, 9, color, color);
      if(this.value) {
         renderer.drawString("x", 1, 2, 14737632);
      }

      renderer.drawStringWithShadow(this.title, 10, 3, 14737632);
   }

   public boolean mousePressed(int posX, int posY, int button) {
      playSound();
      this.value = !this.value;
      return true;
   }
}
