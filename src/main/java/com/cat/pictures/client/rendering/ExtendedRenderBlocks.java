package com.cat.pictures.client.rendering;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class ExtendedRenderBlocks extends RenderBlocks {

   public int meta;


   public IIcon getBlockIcon(Block par1Block, IBlockAccess par2IBlockAccess, int par3, int par4, int par5, int par6) {
      return this.getIconSafe(par1Block.getIcon(par6, this.meta));
   }

   public ExtendedRenderBlocks(RenderBlocks renderer) {
      this.blockAccess = renderer.blockAccess;
      this.overrideBlockTexture = renderer.overrideBlockTexture;
      this.flipTexture = renderer.flipTexture;
      this.field_152631_f = renderer.field_152631_f;
      this.renderAllFaces = renderer.renderAllFaces;
      this.useInventoryTint = renderer.useInventoryTint;
      this.renderFromInside = renderer.renderFromInside;
      this.enableAO = renderer.enableAO;
   }

   public boolean renderStandardBlock(Block block, int x, int y, int z) {
      int l = block.colorMultiplier(this.blockAccess, x, y, z);
      float f = (float)(l >> 16 & 255) / 255.0F;
      float f1 = (float)(l >> 8 & 255) / 255.0F;
      float f2 = (float)(l & 255) / 255.0F;
      if(EntityRenderer.anaglyphEnable) {
         float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
         float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
         float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
         f = f3;
         f1 = f4;
         f2 = f5;
      }

      return Minecraft.isAmbientOcclusionEnabled() && block.getLightValue() == 0?(this.partialRenderBounds?this.renderStandardBlockWithAmbientOcclusionPartial(block, x, y, z, f, f1, f2):this.renderStandardBlockWithAmbientOcclusion(block, x, y, z, f, f1, f2)):this.renderStandardBlockWithColorMultiplier(block, x, y, z, f, f1, f2);
   }
}
