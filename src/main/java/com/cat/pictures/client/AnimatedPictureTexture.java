package com.cat.pictures.client;

import com.cat.pictures.client.PictureTexture;
import com.cat.pictures.client.ProcessedImageData;
import java.util.Arrays;

public class AnimatedPictureTexture extends PictureTexture {

   private final int[] textureIDs;
   private final long[] delay;
   private final long duration;
   private int completedFrames;
   private ProcessedImageData imageData;


   public AnimatedPictureTexture(ProcessedImageData image) {
      super(image.getWidth(), image.getHeight());
      this.imageData = image;
      this.textureIDs = new int[image.getFrameCount()];
      this.delay = image.getDelay();
      this.duration = image.getDuration();
      Arrays.fill(this.textureIDs, -1);
   }

   public void tick() {
      if(this.imageData != null) {
         long startTime = System.currentTimeMillis();
         int index = 0;

         while(this.completedFrames < this.textureIDs.length && index < this.textureIDs.length && System.currentTimeMillis() - startTime < 10L) {
            while(this.textureIDs[index] != -1 && index < this.textureIDs.length - 1) {
               ++index;
            }

            if(this.textureIDs[index] == -1) {
               this.textureIDs[index] = this.uploadFrame(index);
            }
         }
      }

   }

   public int getTextureID() {
      long time = this.duration > 0L?System.currentTimeMillis() % this.duration:0L;
      int index = 0;

      for(int i = 0; i < this.delay.length; ++i) {
         if(this.delay[i] >= time) {
            index = i;
            break;
         }
      }

      return this.textureIDs[index];
   }

   private int uploadFrame(int index) {
      int id = this.imageData.uploadFrame(index);
      this.textureIDs[index] = id;
      if(++this.completedFrames >= this.imageData.getFrameCount()) {
         this.imageData = null;
      }

      return id;
   }
}
