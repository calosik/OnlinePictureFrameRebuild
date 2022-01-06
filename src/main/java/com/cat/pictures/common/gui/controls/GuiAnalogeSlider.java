package com.cat.pictures.common.gui.controls;

import com.cat.pictures.client.rendering.RenderHelper2D;
import com.cat.pictures.common.gui.controls.GuiControl;
import com.cat.pictures.common.gui.event.ControlChangedEvent;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector4d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiAnalogeSlider extends GuiControl {

   private static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
   private final float maxValue;
   private final float minValue;
   public float value;
   private boolean grabbedSlider;


   public GuiAnalogeSlider(String name, int x, int y, int width, int height, float value, float minValue, float maxValue) {
      super(name, x, y, width, height);
      this.value = value;
      this.minValue = minValue;
      this.maxValue = maxValue;
   }

   public void drawControl(FontRenderer renderer) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      mc.getTextureManager().bindTexture(buttonTextures);
      GL11.glEnable(3042);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      GL11.glBlendFunc(770, 771);
      Vector4d black = new Vector4d(0.0D, 0.0D, 0.0D, 255.0D);
      RenderHelper2D.drawGradientRect(0, 0, this.width, this.height, black, black);
      Vector4d color = new Vector4d(60.0D, 60.0D, 60.0D, 255.0D);
      RenderHelper2D.drawGradientRect(1, 1, this.width - 1, this.height - 1, color, color);
      boolean sliderWidth = true;
      float percent = (this.value - this.minValue) / (this.maxValue - this.minValue);
      int posX = 1 + (int)((float)(this.width - 5) * percent);
      Vector4d white = new Vector4d(255.0D, 255.0D, 255.0D, 255.0D);
      RenderHelper2D.drawGradientRect(posX, 1, posX + 4, this.height - 1, white, white);
      String text = this.value + "";
      renderer.drawStringWithShadow(text, this.width / 2 - renderer.getStringWidth(text) / 2, (this.height - 8) / 2, 14737632);
   }

   public boolean mousePressed(int posX, int posY, int button) {
      if(button == 0) {
         Minecraft mc = Minecraft.getMinecraft();
         mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
         return this.grabbedSlider = true;
      } else {
         return false;
      }
   }

   public void setValue(float value) {
      this.value = Math.max(this.minValue, value);
      this.value = Math.min(this.maxValue, this.value);
      this.raiseEvent(new ControlChangedEvent(this));
   }

   public void mouseMove(int posX, int posY, int button) {
      Vector2d mouse = this.parent.getMousePos();
      if(this.grabbedSlider) {
         if(mouse.x < (double)this.posX) {
            this.value = this.minValue;
         } else if(mouse.x > (double)(this.posX + this.width)) {
            this.value = this.maxValue;
         } else {
            this.value = this.minValue + (this.maxValue - this.minValue) * ((float)(mouse.x - (double)this.posX) / (float)this.width);
         }

         this.setValue(this.value);
      }

   }

   public void mouseReleased(int posX, int posY, int button) {
      if(this.grabbedSlider) {
         this.grabbedSlider = false;
      }

   }

}
