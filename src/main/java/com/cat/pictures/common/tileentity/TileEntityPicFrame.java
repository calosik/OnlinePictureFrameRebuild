package com.cat.pictures.common.tileentity;

import com.cat.pictures.client.DownloadThread;
import com.cat.pictures.client.PictureTexture;
import com.cat.pictures.common.utils.CubeObject;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPicFrame extends TileEntity {

   @SideOnly(Side.CLIENT)
   public DownloadThread downloader;
   @SideOnly(Side.CLIENT)
   public PictureTexture texture;
   @SideOnly(Side.CLIENT)
   public boolean failed;
   public int renderDistance = 32;
   public String url = "";
   public String owner = "";
   public float sizeX = 1.0F;
   public float sizeY = 1.0F;
   public boolean flipped;
   public byte headPos = 0;
   public byte posX = 0;
   public byte posY = 0;
   public boolean visibleFrame = true;
   public float transparency = 1.0F;


   public TileEntityPicFrame() {
      if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
         this.initClient();
      }

   }

   @SideOnly(Side.CLIENT)
   public void initClient() {
      this.texture = null;
      this.failed = false;
   }

   @SideOnly(Side.CLIENT)
   public boolean shouldLoadTexture() {
      return !this.isTextureLoaded() && !this.failed;
   }

   @SideOnly(Side.CLIENT)
   public void loadTexture() {
      if(this.shouldLoadTexture()) {
         if(this.downloader == null && DownloadThread.activeDownloads < 5) {
            PictureTexture loadedTexture = (PictureTexture)DownloadThread.loadedImages.get(this.url);
            if(loadedTexture == null) {
               Object var2 = DownloadThread.LOCK;
               synchronized(DownloadThread.LOCK) {
                  if(!DownloadThread.loadingImages.contains(this.url)) {
                     this.downloader = new DownloadThread("http://smrproject.ru/cat/" + this.url);
                     return;
                  }
               }
            } else {
               this.texture = loadedTexture;
            }
         }

         if(this.downloader != null && this.downloader.hasFinished()) {
            if(this.downloader.hasFailed()) {
               this.failed = true;
            } else {
               this.texture = DownloadThread.loadImage(this.downloader);
            }

            this.downloader = null;
         }
      }

   }

   @SideOnly(Side.CLIENT)
   public boolean isTextureLoaded() {
      return this.texture != null;
   }

   @SideOnly(Side.CLIENT)
   public double getMaxRenderDistanceSquared() {
      return Math.pow((double)this.renderDistance, 2.0D);
   }

   public AxisAlignedBB getBoundingBox() {
      CubeObject cube = new CubeObject(0.0D, 0.0D, 0.0D, 0.05D, 1.0D, 1.0D);
      float sizeX = this.sizeX;
      float sizeY = this.sizeY;
      double offsetX = 0.0D;
      double offsetY = 0.0D;
      if(this.posX == 1) {
         offsetX += (double)(-sizeX + 1.0F) / 2.0D;
      } else if(this.posX == 2) {
         offsetX += (double)(-sizeX + 1.0F);
      }

      if(this.posY == 1) {
         offsetY += (double)(-sizeY + 1.0F) / 2.0D;
      } else if(this.posY == 2) {
         offsetY += (double)(-sizeY + 1.0F);
      }

      ForgeDirection direction = ForgeDirection.getOrientation(this.getBlockMetadata());
      if(direction == ForgeDirection.UP) {
         cube.minZ -= (double)(sizeX - 1.0F);
         cube.minY -= (double)(sizeY - 1.0F);
         cube.minZ -= offsetX;
         cube.maxZ -= offsetX;
         cube.minY -= offsetY;
         cube.maxY -= offsetY;
      } else {
         cube.maxZ += (double)(sizeX - 1.0F);
         cube.maxY += (double)(sizeY - 1.0F);
         cube.minZ += offsetX;
         cube.maxZ += offsetX;
         cube.minY += offsetY;
         cube.maxY += offsetY;
      }

      cube = new CubeObject(Math.min(cube.minX, cube.maxX), Math.min(cube.minY, cube.maxY), Math.min(cube.minZ, cube.maxZ), Math.max(cube.minX, cube.maxX), Math.max(cube.minY, cube.maxY), Math.max(cube.minZ, cube.maxZ));
      return CubeObject.rotateCube(cube, direction).getAxis().getOffsetBoundingBox((double)this.xCoord, (double)this.yCoord, (double)this.zCoord);
   }

   @SideOnly(Side.CLIENT)
   public AxisAlignedBB getRenderBoundingBox() {
      return this.getBoundingBox();
   }

   public void updateEntity() {
      if(this.worldObj.isRemote && this.texture != null) {
         this.texture.tick();
      }

   }

   public void writeToNBT(NBTTagCompound nbt) {
      super.writeToNBT(nbt);
      nbt.setString("url", this.url);
      if(!nbt.hasKey("owner")) {
         nbt.setString("owner", this.owner);
      }

      nbt.setFloat("sizeX", this.sizeX);
      nbt.setFloat("sizeY", this.sizeY);
      nbt.setInteger("render", this.renderDistance);
      nbt.setByte("offsetX", this.posX);
      nbt.setByte("offsetY", this.posY);
      nbt.setFloat("transparency", this.transparency);
      nbt.setBoolean("visibleFrame", this.visibleFrame);
      nbt.setBoolean("flipped", this.flipped);
      nbt.setByte("headPos", this.headPos);
   }

   public void readFromNBT(NBTTagCompound nbt) {
      super.readFromNBT(nbt);
      this.url = nbt.getString("url");
      this.owner = nbt.getString("owner");
      this.sizeX = nbt.getFloat("sizeX");
      this.sizeY = nbt.getFloat("sizeY");
      this.renderDistance = nbt.getInteger("render");
      this.posX = nbt.getByte("offsetX");
      this.posY = nbt.getByte("offsetY");
      this.transparency = nbt.hasKey("transparency")?nbt.getFloat("transparency"):1.0F;
      this.visibleFrame = nbt.getBoolean("visibleFrame");
      this.flipped = nbt.getBoolean("flipped");
      this.headPos = nbt.getByte("headPos");
   }

   public Packet getDescriptionPacket() {
      NBTTagCompound nbt = new NBTTagCompound();
      nbt.setString("url", this.url);
      if(!nbt.hasKey("owner")) {
         nbt.setString("owner", this.owner);
      }

      nbt.setFloat("sizeX", this.sizeX);
      nbt.setFloat("sizeY", this.sizeY);
      nbt.setInteger("render", this.renderDistance);
      nbt.setByte("offsetX", this.posX);
      nbt.setByte("offsetY", this.posY);
      nbt.setFloat("transparency", this.transparency);
      nbt.setBoolean("visibleFrame", this.visibleFrame);
      nbt.setBoolean("flipped", this.flipped);
      nbt.setByte("headPos", this.headPos);
      return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, this.blockMetadata, nbt);
   }

   @SideOnly(Side.CLIENT)
   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
      super.onDataPacket(net, pkt);
      this.url = pkt.func_148857_g().getString("url");
      this.owner = pkt.func_148857_g().getString("owner");
      this.sizeX = pkt.func_148857_g().getFloat("sizeX");
      this.sizeY = pkt.func_148857_g().getFloat("sizeY");
      this.renderDistance = pkt.func_148857_g().getInteger("render");
      this.posX = pkt.func_148857_g().getByte("offsetX");
      this.posY = pkt.func_148857_g().getByte("offsetY");
      this.transparency = pkt.func_148857_g().getFloat("transparency");
      this.visibleFrame = pkt.func_148857_g().getBoolean("visibleFrame");
      this.flipped = pkt.func_148857_g().getBoolean("flipped");
      this.headPos = pkt.func_148857_g().getByte("headPos");
      this.initClient();
      this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
   }

   public void updateBlock() {
		{
			if(!worldObj.isRemote)
			{
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				markDirty();
			}
		}
   }
}
