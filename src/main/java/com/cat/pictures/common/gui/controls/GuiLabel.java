package com.cat.pictures.common.gui.controls;

import com.cat.pictures.common.gui.controls.GuiControl;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

public class GuiLabel extends GuiControl {

   private String title;


   public GuiLabel(String title, int x, int y) {
      this(mc.fontRenderer, title, x, y);
   }

   private GuiLabel(FontRenderer font, String title, int x, int y) {
      super(title, x, y, font.getStringWidth(title), font.FONT_HEIGHT);
      this.title = title;
   }

   public void drawControl(FontRenderer renderer) {
      if(this.shouldDrawTitle()) {
         GL11.glDisable(2896);
         renderer.drawStringWithShadow(this.title, 0, this.height / 4, 14737632);
      }

   }

   private boolean shouldDrawTitle() {
      return true;
   }
}
