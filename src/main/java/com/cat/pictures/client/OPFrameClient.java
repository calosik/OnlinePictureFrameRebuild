package com.cat.pictures.client;

import com.cat.pictures.client.PicBlockRenderer;
import com.cat.pictures.client.PicTileRenderer;
import com.cat.pictures.common.tileentity.TileEntityPicFrame;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class OPFrameClient {

   public static int modelID;


   public static void initClient() {
      modelID = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(modelID, new PicBlockRenderer());
      ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPicFrame.class, new PicTileRenderer());
   }
}
