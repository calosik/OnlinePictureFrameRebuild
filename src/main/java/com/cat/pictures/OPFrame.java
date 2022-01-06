package com.cat.pictures;

import com.cat.pictures.client.OPFrameClient;
import com.cat.pictures.common.block.BlockPicFrame;
import com.cat.pictures.common.event.TickHandler;
import com.cat.pictures.common.gui.GuiHandler;
import com.cat.pictures.common.packet.CreativeCorePacket;
import com.cat.pictures.common.packet.CreativeMessageHandler;
import com.cat.pictures.common.packet.GuiControlPacket;
import com.cat.pictures.common.packet.GuiLayerPacket;
import com.cat.pictures.common.packet.GuiUpdatePacket;
import com.cat.pictures.common.packet.PacketReciever;
import com.cat.pictures.common.tileentity.TileEntityPicFrame;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(
   modid = "catpictures",
   name = "CatPictures",
   version = "1.2"
)
public class OPFrame {

   @Instance("catpictures")
   public static OPFrame instance = new OPFrame();
   public static final boolean IS_SERVER = false;
   public static SimpleNetworkWrapper network;
   private static TickHandler tickHandler = new TickHandler();


   @EventHandler
   public void init(FMLInitializationEvent evt) {
      network = NetworkRegistry.INSTANCE.newSimpleChannel("CatPicturesPacket");
      network.registerMessage(PacketReciever.class, CreativeMessageHandler.class, 0, Side.CLIENT);
      network.registerMessage(PacketReciever.class, CreativeMessageHandler.class, 0, Side.SERVER);
      NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
      CreativeCorePacket.registerPacket(GuiUpdatePacket.class, "guiupdatepacket");
      CreativeCorePacket.registerPacket(GuiControlPacket.class, "guicontrolpacket");
      CreativeCorePacket.registerPacket(GuiLayerPacket.class, "guilayerpacket");
      FMLCommonHandler.instance().bus().register(tickHandler);
      GameRegistry.registerBlock(new BlockPicFrame(), "catFrame");
      GameRegistry.registerTileEntity(TileEntityPicFrame.class, "CATFrameTileEntity");
      if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
         this.initClient();
      }

   }

   @SideOnly(Side.CLIENT)
   public void initClient() {
      OPFrameClient.initClient();
   }

}
