package com.cat.pictures.common.gui.controls;

import com.cat.pictures.common.gui.SubGui;
import com.cat.pictures.common.gui.event.GuiControlEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import javax.vecmath.Vector2d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public abstract class GuiControl {

   public static Minecraft mc = Minecraft.getMinecraft();
   public int posX;
   public int posY;
   public int height;
   int width;
   public boolean visible;
   public SubGui parent;
   public String name;


   public GuiControl(String name, int x, int y, int width, int height) {
      this.name = name;
      this.posX = x;
      this.posY = y;
      this.width = width;
      this.height = height;
      this.visible = true;
   }

   public abstract void drawControl(FontRenderer var1);

   public void renderControl(FontRenderer renderer) {
      Vector2d centerOffset = this.getCenterOffset();
      GL11.glPushMatrix();
      GL11.glTranslated((double)this.posX + centerOffset.x, (double)this.posY + centerOffset.y, 0.0D);
      GL11.glRotated(0.0D, 0.0D, 0.0D, 1.0D);
      GL11.glTranslated(-centerOffset.x, -centerOffset.y, 0.0D);
      this.drawControl(renderer);
      GL11.glPopMatrix();
   }

   public boolean isInteractable() {
      return this.visible;
   }

   boolean isMouseOver() {
      Vector2d mouse = this.parent.getMousePos();
      Vector2d pos = this.getValidPos((int)mouse.x, (int)mouse.y);
      return this.visible && this.isMouseOver((int)pos.x, (int)pos.y);
   }

   public boolean isMouseOver(int posX, int posY) {
      return posX >= this.posX && posX < this.posX + this.width && posY >= this.posY && posY < this.posY + this.height;
   }

   public boolean mouseScrolled() {
      return false;
   }

   public boolean mousePressed(int posX, int posY, int button) {
      return false;
   }

   public boolean mouseDragged() {
      return false;
   }

   public void mouseReleased(int posX, int posY, int button) {}

   public void mouseMove(int posX, int posY, int button) {}

   public boolean is(String name) {
      return this.name.equalsIgnoreCase(name);
   }

   public void onLoseFocus() {}

   public boolean onKeyPressed(char character, int key) {
      return false;
   }

   public void onGuiClose() {}

   public void raiseEvent(GuiControlEvent event) {
      this.parent.raiseEvent(event);
   }

   private Vector2d getCenterOffset() {
      return new Vector2d((double)(this.width / 2), (double)(this.height / 2));
   }

   public Vector2d getValidPos(int x, int y) {
      Vector2d pos = new Vector2d((double)x, (double)y);
      Vector2d centerOffset = this.getCenterOffset();
      return getRotationAround(pos, new Vector2d((double)this.posX + centerOffset.x, (double)this.posY + centerOffset.y));
   }

   static void playSound() {
      mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
   }

   public static Vector2d getMousePos(int width, int height) {
      Minecraft mc = Minecraft.getMinecraft();
      ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
      int i = scaledresolution.getScaledWidth();
      int j = scaledresolution.getScaledHeight();
      int x = Mouse.getEventX() * i / mc.displayWidth;
      int y = j - Mouse.getEventY() * j / mc.displayHeight - 1;
      x -= (i - width) / 2;
      y -= (j - height) / 2;
      return new Vector2d((double)x, (double)y);
   }

   private static Vector2d getRotationAround(Vector2d pos, Vector2d center) {
      Vector2d result = new Vector2d(pos);
      result.sub(center);
      Vector2d temp = new Vector2d(result);
      result.x = Math.cos(Math.toRadians(0.0D)) * temp.x - Math.sin(Math.toRadians(0.0D)) * temp.y;
      result.y = Math.sin(Math.toRadians(0.0D)) * temp.x + Math.cos(Math.toRadians(0.0D)) * temp.y;
      result.add(center);
      return result;
   }

}
