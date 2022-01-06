package com.cat.pictures.client.rendering;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import javax.vecmath.Vector4d;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHelper2D {

   public static void drawGradientRect(int p_73733_1_, int p_73733_2_, int p_73733_3_, int p_73733_4_, Vector4d color1, Vector4d color2) {
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glDisable(3008);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      GL11.glShadeModel(7425);
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.setColorRGBA((int)color1.x, (int)color1.y, (int)color1.z, (int)color1.w);
      tessellator.addVertex((double)p_73733_3_, (double)p_73733_2_, 0.0D);
      tessellator.addVertex((double)p_73733_1_, (double)p_73733_2_, 0.0D);
      tessellator.setColorRGBA((int)color2.x, (int)color2.y, (int)color2.z, (int)color2.w);
      tessellator.addVertex((double)p_73733_1_, (double)p_73733_4_, 0.0D);
      tessellator.addVertex((double)p_73733_3_, (double)p_73733_4_, 0.0D);
      tessellator.draw();
      GL11.glShadeModel(7424);
      GL11.glDisable(3042);
      GL11.glEnable(3008);
      GL11.glEnable(3553);
   }
}
