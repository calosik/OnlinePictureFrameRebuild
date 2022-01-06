package com.cat.pictures.client;

import com.cat.pictures.client.PictureTexture;
import com.cat.pictures.client.ProcessedImageData;

public class OrdinaryTexture extends PictureTexture {

   private final int textureID;


   public OrdinaryTexture(ProcessedImageData image) {
      super(image.getWidth(), image.getHeight());
      this.textureID = image.uploadFrame(0);
   }

   public void tick() {}

   public int getTextureID() {
      return this.textureID;
   }
}
