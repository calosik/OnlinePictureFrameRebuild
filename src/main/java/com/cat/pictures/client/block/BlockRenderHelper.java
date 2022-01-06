package com.cat.pictures.client.block;

import com.cat.pictures.client.block.IBlockAccessFake;
import com.cat.pictures.client.rendering.ExtendedRenderBlocks;
import com.cat.pictures.client.rendering.RenderHelper3D;
import com.cat.pictures.common.utils.CubeObject;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class BlockRenderHelper {

   private static IBlockAccessFake fake = null;


   public static void renderCubes(ArrayList cubes, int x, int y, int z, Block block, RenderBlocks renderer, ForgeDirection direction) {
      renderCubes(cubes, x, y, z, block, renderer, direction, RenderHelper3D.renderBlocks);
   }

   private static void renderCubes(ArrayList cubes, int x, int y, int z, Block block, RenderBlocks renderer, ForgeDirection direction, ExtendedRenderBlocks extraRenderer) {
      Iterator var8 = cubes.iterator();

      while(var8.hasNext()) {
         CubeObject cube = (CubeObject)var8.next();
         if(cube.icon != null) {
            renderer.setOverrideBlockTexture(cube.icon);
         }

         if(cube.block != null) {
            if(cube.meta != -1) {
               if(fake == null) {
                  fake = new IBlockAccessFake(renderer.blockAccess);
                  extraRenderer.blockAccess = fake;
               }

               if(fake.world != renderer.blockAccess) {
                  fake.world = renderer.blockAccess;
               }

               extraRenderer.clearOverrideBlockTexture();
               extraRenderer.setRenderBounds(cube.minX, cube.minY, cube.minZ, cube.maxX, cube.maxY, cube.maxZ);
               extraRenderer.meta = cube.meta;
               fake.overrideMeta = cube.meta;
               extraRenderer.lockBlockBounds = true;
               extraRenderer.renderBlockAllFaces(cube.block, x, y, z);
               extraRenderer.lockBlockBounds = false;
               continue;
            }

            renderer.setOverrideBlockTexture(cube.block.getBlockTextureFromSide(0));
         }

         renderer.setRenderBounds(cube.minX, cube.minY, cube.minZ, cube.maxX, cube.maxY, cube.maxZ);
         if(direction != null && direction != ForgeDirection.EAST && direction != ForgeDirection.UNKNOWN) {
            RenderHelper3D.applyBlockRotation(renderer, direction);
         }

         renderer.renderStandardBlock(block, x, y, z);
         if(cube.icon != null || cube.block != null) {
            renderer.clearOverrideBlockTexture();
         }
      }

   }

   public static void renderInventoryCubes(RenderBlocks renderer, ArrayList cubes, Block parBlock) {
      Tessellator tesselator = Tessellator.instance;
      Iterator var4 = cubes.iterator();

      while(var4.hasNext()) {
         CubeObject cube = (CubeObject)var4.next();
         int metadata = 0;
         if(cube.meta != -1) {
            metadata = cube.meta;
         }

         Block block = parBlock;
         if(parBlock instanceof BlockAir) {
            block = Blocks.stone;
         }

         renderer.setRenderBounds(cube.minX, cube.minY, cube.minZ, cube.maxX, cube.maxY, cube.maxZ);
         if(cube.block != null && !(cube.block instanceof BlockAir)) {
            block = cube.block;
         }

         if(cube.icon != null) {
            renderOther(renderer, tesselator, block, cube.icon, cube.icon, cube.icon, cube.icon, cube.icon);
            renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, cube.icon);
         } else {
            int j = block.getRenderColor(metadata);
            float f1 = (float)(j >> 16 & 255) / 255.0F;
            float f2 = (float)(j >> 8 & 255) / 255.0F;
            float f3 = (float)(j & 255) / 255.0F;
            float brightness = 1.0F;
            GL11.glColor4f(f1 * 1.0F, f2 * 1.0F, f3 * 1.0F, 1.0F);
            renderOther(renderer, tesselator, block, block.getIcon(0, metadata), block.getIcon(1, metadata), block.getIcon(2, metadata), block.getIcon(3, metadata), block.getIcon(4, metadata));
            renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, metadata));
         }

         tesselator.draw();
         GL11.glTranslatef(0.5F, 0.5F, 0.5F);
      }

   }

   private static void renderOther(RenderBlocks renderer, Tessellator tesselator, Block block, IIcon icon, IIcon icon2, IIcon icon3, IIcon icon4, IIcon icon5) {
      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
      tesselator.startDrawingQuads();
      tesselator.setNormal(0.0F, -1.0F, 0.0F);
      renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, icon);
      tesselator.draw();
      tesselator.startDrawingQuads();
      tesselator.setNormal(0.0F, 1.0F, 0.0F);
      renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, icon2);
      tesselator.draw();
      tesselator.startDrawingQuads();
      tesselator.setNormal(0.0F, 0.0F, -1.0F);
      renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, icon3);
      tesselator.draw();
      tesselator.startDrawingQuads();
      tesselator.setNormal(0.0F, 0.0F, 1.0F);
      renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, icon4);
      tesselator.draw();
      tesselator.startDrawingQuads();
      tesselator.setNormal(-1.0F, 0.0F, 0.0F);
      renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, icon5);
      tesselator.draw();
      tesselator.startDrawingQuads();
      tesselator.setNormal(1.0F, 0.0F, 0.0F);
   }

}
