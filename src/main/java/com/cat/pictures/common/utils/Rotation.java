package com.cat.pictures.common.utils;

import net.minecraftforge.common.util.ForgeDirection;

public enum Rotation {
   EAST, WEST, UP, UPX, DOWN, DOWNX, SOUTH, NORTH, UNKOWN;

   public static Rotation getRotationByDirection(ForgeDirection direction) {
      switch (direction) {
         case DOWN:
            return DOWN;
         case EAST:
            return EAST;
         case NORTH:
            return NORTH;
         case SOUTH:
            return SOUTH;
         case UP:
            return UP;
         case WEST:
            return WEST;
      }
      return UNKOWN;
   }
}
