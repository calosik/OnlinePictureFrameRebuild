package com.cat.pictures.common.utils;

import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class RotationUtils {
   public static Vec3 applyVectorRotation(Vec3 vector, ForgeDirection direction) {
      return applyVectorRotation(vector, Rotation.getRotationByDirection(direction));
   }

   private static Vec3 applyVectorRotation(Vec3 vector, Rotation direction) {
      double tempX = vector.xCoord;
      double tempY = vector.yCoord;
      double tempZ = vector.zCoord;
      switch (direction) {
         case UP:
            vector.xCoord = -tempY;
            break;
         case DOWN:
            vector.yCoord = -tempX;
            break;
         case UPX:
            vector.zCoord = -tempY;
            vector.yCoord = tempZ;
            break;
         case DOWNX:
            vector.zCoord = tempY;
            vector.yCoord = -tempZ;
            break;
         case SOUTH:
            vector.xCoord = -tempZ;
            vector.zCoord = tempX;
            break;
         case NORTH:
            vector.xCoord = tempZ;
            vector.zCoord = -tempX;
            break;
         case WEST:
            vector.xCoord = -tempX;
            vector.zCoord = -tempZ;
            break;
      }
      return vector;
   }

   static void applyCubeRotation(CubeObject cube, Rotation direction, Vec3 center) {
      double minX = cube.minX;
      double minY = cube.minY;
      double minZ = cube.minZ;
      double maxX = cube.maxX;
      double maxY = cube.maxY;
      double maxZ = cube.maxZ;
      if (center != null) {
         minX -= center.xCoord;
         minY -= center.yCoord;
         minZ -= center.zCoord;
         maxX -= center.xCoord;
         maxY -= center.yCoord;
         maxZ -= center.zCoord;
      }
      Vec3 min = applyVectorRotation(Vec3.createVectorHelper(minX, minY, minZ), direction);
      Vec3 max = applyVectorRotation(Vec3.createVectorHelper(maxX, maxY, maxZ), direction);
      if (center != null) {
         min = min.addVector(center.xCoord, center.yCoord, center.zCoord);
         max = max.addVector(center.xCoord, center.yCoord, center.zCoord);
      }
      if (min.xCoord < max.xCoord) {
         cube.minX = min.xCoord;
         cube.maxX = max.xCoord;
      } else {
         cube.minX = max.xCoord;
         cube.maxX = min.xCoord;
      }
      if (min.yCoord < max.yCoord) {
         cube.minY = min.yCoord;
         cube.maxY = max.yCoord;
      } else {
         cube.minY = max.yCoord;
         cube.maxY = min.yCoord;
      }
      if (min.zCoord < max.zCoord) {
         cube.minZ = min.zCoord;
         cube.maxZ = max.zCoord;
      } else {
         cube.minZ = max.zCoord;
         cube.maxZ = min.zCoord;
      }
   }
}
