package com.cat.pictures.client.rendering;

import com.cat.pictures.client.rendering.ExtendedRenderBlocks;
import com.cat.pictures.common.utils.RotationUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHelper3D {

   public static RenderBlocks renderer = RenderBlocks.getInstance();
   public static ExtendedRenderBlocks renderBlocks = new ExtendedRenderBlocks(renderer);


   public static void applyBlockRotation(RenderBlocks renderer, ForgeDirection direction) {
      double minX = renderer.renderMinX - 0.5D;
      double minY = renderer.renderMinY - 0.5D;
      double minZ = renderer.renderMinZ - 0.5D;
      double maxX = renderer.renderMaxX - 0.5D;
      double maxY = renderer.renderMaxY - 0.5D;
      double maxZ = renderer.renderMaxZ - 0.5D;
      Vec3 min = RotationUtils.applyVectorRotation(Vec3.createVectorHelper(minX, minY, minZ), direction);
      Vec3 max = RotationUtils.applyVectorRotation(Vec3.createVectorHelper(maxX, maxY, maxZ), direction);
      min = min.addVector(0.5D, 0.5D, 0.5D);
      max = max.addVector(0.5D, 0.5D, 0.5D);
      if(min.xCoord < max.xCoord) {
         renderer.renderMinX = min.xCoord;
         renderer.renderMaxX = max.xCoord;
      } else {
         renderer.renderMinX = max.xCoord;
         renderer.renderMaxX = min.xCoord;
      }

      if(min.yCoord < max.yCoord) {
         renderer.renderMinY = min.yCoord;
         renderer.renderMaxY = max.yCoord;
      } else {
         renderer.renderMinY = max.yCoord;
         renderer.renderMaxY = min.yCoord;
      }

      if(min.zCoord < max.zCoord) {
         renderer.renderMinZ = min.zCoord;
         renderer.renderMaxZ = max.zCoord;
      } else {
         renderer.renderMinZ = max.zCoord;
         renderer.renderMaxZ = min.zCoord;
      }

   }

   public static void applyDirection(ForgeDirection direction) {
      int rotation = 0;
      switch (direction) {
         case EAST:
            rotation = 0;
            break;
         case NORTH:
            rotation = 90;
            break;
         case SOUTH:
            rotation = 270;
            break;
         case WEST:
            rotation = 180;
            break;
         case UP:
            GL11.glRotated(90.0D, 1.0D, 0.0D, 0.0D);
            GL11.glRotated(-90.0D, 0.0D, 0.0D, 1.0D);
            break;
         case DOWN:
            GL11.glRotated(-90.0D, 1.0D, 0.0D, 0.0D);
            GL11.glRotated(-90.0D, 0.0D, 0.0D, 1.0D);
            break;
	default:
		break;
      }
      GL11.glRotated(rotation, 0.0D, 1.0D, 0.0D);
   }
}
