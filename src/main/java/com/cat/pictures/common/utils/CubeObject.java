package com.cat.pictures.common.utils;

import com.cat.pictures.common.utils.Rotation;
import com.cat.pictures.common.utils.RotationUtils;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class CubeObject {

   public IIcon icon;
   public Block block;
   public int meta;
   public double minX;
   public double minY;
   public double minZ;
   public double maxX;
   public double maxY;
   public double maxZ;


   public CubeObject(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      this.meta = -1;
      this.minX = minX;
      this.minY = minY;
      this.minZ = minZ;
      this.maxX = maxX;
      this.maxY = maxY;
      this.maxZ = maxZ;
   }

   public CubeObject() {
      this(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
   }

   private CubeObject(CubeObject cube) {
      this(cube.minX, cube.minY, cube.minZ, cube.maxX, cube.maxY, cube.maxZ, cube);
   }

   private CubeObject(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, CubeObject cube) {
      this(minX, minY, minZ, maxX, maxY, maxZ);
      this.block = cube.block;
      this.icon = cube.icon;
   }

   public CubeObject(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, IIcon icon) {
      this(minX, minY, minZ, maxX, maxY, maxZ);
      this.icon = icon;
   }

   public CubeObject(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Block block) {
      this(minX, minY, minZ, maxX, maxY, maxZ);
      this.block = block;
   }

   public CubeObject(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Block block, int meta) {
      this(minX, minY, minZ, maxX, maxY, maxZ);
      this.block = block;
      this.meta = meta;
   }

   public String toString() {
      return "cube[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
   }

   public AxisAlignedBB getAxis() {
      return AxisAlignedBB.getBoundingBox(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
   }

   public static CubeObject rotateCube(CubeObject cube, ForgeDirection direction) {
      return rotateCube(cube, direction, Vec3.createVectorHelper(0.5D, 0.5D, 0.5D));
   }

   private static CubeObject rotateCube(CubeObject cube, ForgeDirection direction, Vec3 center) {
      return rotateCube(cube, Rotation.getRotationByDirection(direction), center);
   }

   private static CubeObject rotateCube(CubeObject cube, Rotation direction, Vec3 center) {
      CubeObject rotateCube = new CubeObject(cube);
      RotationUtils.applyCubeRotation(rotateCube, direction, center);
      return rotateCube;
   }
}
