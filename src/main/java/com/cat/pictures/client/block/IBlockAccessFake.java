package com.cat.pictures.client.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class IBlockAccessFake implements IBlockAccess {

   public IBlockAccess world;
   public int overrideMeta = -1;


   public IBlockAccessFake(IBlockAccess world) {
      this.world = world;
   }

   public Block getBlock(int x, int y, int z) {
      return this.world.getBlock(x, y, z);
   }

   public TileEntity getTileEntity(int x, int y, int z) {
      return this.world.getTileEntity(x, y, z);
   }

   @SideOnly(Side.CLIENT)
   public int getLightBrightnessForSkyBlocks(int x, int y, int z, int side) {
      return this.world.getLightBrightnessForSkyBlocks(x, y, z, side);
   }

   public int getBlockMetadata(int x, int y, int z) {
      return this.overrideMeta != -1?this.overrideMeta:this.world.getBlockMetadata(x, y, z);
   }

   public int isBlockProvidingPowerTo(int x, int y, int z, int side) {
      return this.world.isBlockProvidingPowerTo(x, y, z, side);
   }

   public boolean isAirBlock(int x, int y, int z) {
      return this.world.isAirBlock(x, y, z);
   }

   @SideOnly(Side.CLIENT)
   public BiomeGenBase getBiomeGenForCoords(int x, int z) {
      return this.world.getBiomeGenForCoords(x, z);
   }

   @SideOnly(Side.CLIENT)
   public int getHeight() {
      return this.world.getHeight();
   }

   @SideOnly(Side.CLIENT)
   public boolean extendedLevelsInChunkCache() {
      return this.world.extendedLevelsInChunkCache();
   }

   public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default) {
      return this.world.isSideSolid(x, y, z, side, _default);
   }
}
