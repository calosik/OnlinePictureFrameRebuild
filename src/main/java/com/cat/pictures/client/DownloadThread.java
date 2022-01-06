package com.cat.pictures.client;

import com.cat.pictures.client.AnimatedPictureTexture;
import com.cat.pictures.client.GifDecoder;
import com.cat.pictures.client.OrdinaryTexture;
import com.cat.pictures.client.PictureTexture;
import com.cat.pictures.client.ProcessedImageData;
import com.cat.pictures.client.TextureCache;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.apache.commons.io.IOUtils;

@SideOnly(Side.CLIENT)
public class DownloadThread extends Thread {

   public static final TextureCache TEXTURE_CACHE = new TextureCache();
   public static final DateFormat FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
   public static final Object LOCK = new Object();
   public static final int MAXIMUM_ACTIVE_DOWNLOADS = 5;
   public static int activeDownloads = 0;
   public static HashMap loadedImages = new HashMap();
   public static Set loadingImages = new HashSet();
   private final String url;
   private ProcessedImageData processedImage;
   private boolean complete;


   public DownloadThread(String url) {
      this.url = url;
      Object var2 = LOCK;
      synchronized(LOCK) {
         loadingImages.add(url);
         ++activeDownloads;
      }

      this.setName("Cat Download \"" + url + "\"");
      this.setDaemon(true);
      this.start();
   }

   public boolean hasFinished() {
      return this.complete;
   }

   public boolean hasFailed() {
      return this.hasFinished() && this.processedImage == null;
   }

   public void run() {
      try {
         byte[] data = load(this.url);
         String type = readType(data);
         ByteArrayInputStream in = null;

         try {
            in = new ByteArrayInputStream(data);
            if(type.equalsIgnoreCase("gif")) {
               GifDecoder image = new GifDecoder();
               int status = image.read((InputStream)in);
               if(status == 0) {
                  this.processedImage = new ProcessedImageData(image);
               }
            } else {
               try {
                  BufferedImage image1 = ImageIO.read(in);
                  if(image1 != null) {
                     this.processedImage = new ProcessedImageData(image1);
                  }
               } catch (IOException var13) {
                  ;
               }
            }
         } finally {
            IOUtils.closeQuietly(in);
         }
      } catch (Exception var15) {
         ;
      }

      if(this.processedImage == null) {
         TEXTURE_CACHE.deleteEntry(this.url);
      }

      this.complete = true;
      Object data1 = LOCK;
      synchronized(LOCK) {
         loadingImages.remove(this.url);
         --activeDownloads;
      }
   }

   public static byte[] load(String url) throws IOException {
      TextureCache.CacheEntry entry = TEXTURE_CACHE.getEntry(url);
      long requestTime = System.currentTimeMillis();
      URLConnection connection = (new URL(url)).openConnection();
      connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
      int responseCode = -1;
      if(connection instanceof HttpURLConnection) {
         HttpURLConnection in = (HttpURLConnection)connection;
         if(entry != null) {
            if(entry.getEtag() != null) {
               in.setRequestProperty("If-None-Match", entry.getEtag());
            } else if(entry.getTime() != -1L) {
               in.setRequestProperty("If-Modified-Since", FORMAT.format(new Date(entry.getTime())));
            }
         }

         responseCode = in.getResponseCode();
      }

      InputStream in1 = null;

      byte[] var16;
      try {
         in1 = connection.getInputStream();
         String etag = connection.getHeaderField("ETag");
         long expireTimestamp = -1L;
         String maxAge = connection.getHeaderField("max-age");
         if(maxAge != null && !maxAge.isEmpty()) {
            try {
               expireTimestamp = requestTime + Long.parseLong(maxAge) * 1000L;
            } catch (NumberFormatException var24) {
               ;
            }
         }

         String expires = connection.getHeaderField("Expires");
         if(expires != null && !expires.isEmpty()) {
            try {
               expireTimestamp = FORMAT.parse(expires).getTime();
            } catch (ParseException var23) {
               ;
            }
         }

         String lastModified = connection.getHeaderField("Last-Modified");
         long lastModifiedTimestamp;
         if(lastModified != null && !lastModified.isEmpty()) {
            try {
               lastModifiedTimestamp = FORMAT.parse(lastModified).getTime();
            } catch (ParseException var22) {
               lastModifiedTimestamp = requestTime;
            }
         } else {
            lastModifiedTimestamp = requestTime;
         }

         if(entry != null) {
            if(etag != null && !etag.isEmpty()) {
               entry.setEtag(etag);
            }

            entry.setTime(lastModifiedTimestamp);
            if(responseCode == 304) {
               File data = entry.getFile();
               if(data.exists()) {
                  var16 = IOUtils.toByteArray(new FileInputStream(data));
                  return var16;
               }
            }
         }

         byte[] data1 = IOUtils.toByteArray(in1);
         TEXTURE_CACHE.save(url, etag, lastModifiedTimestamp, expireTimestamp, data1);
         var16 = data1;
      } finally {
         IOUtils.closeQuietly(in1);
      }

      return var16;
   }

   private static String readType(byte[] input) throws IOException {
      ByteArrayInputStream in = null;

      String var2;
      try {
         in = new ByteArrayInputStream(input);
         var2 = readType((InputStream)in);
      } finally {
         IOUtils.closeQuietly(in);
      }

      return var2;
   }

   private static String readType(InputStream input) throws IOException {
      ImageInputStream stream = ImageIO.createImageInputStream(input);
      Iterator iter = ImageIO.getImageReaders(stream);
      if(!iter.hasNext()) {
         return "";
      } else {
         ImageReader reader = (ImageReader)iter.next();
         if(reader.getFormatName().equalsIgnoreCase("gif")) {
            return "gif";
         } else {
            ImageReadParam param = reader.getDefaultReadParam();
            reader.setInput(stream, true, true);

            try {
               reader.read(0, param);
            } catch (IOException var9) {
               ;
            } finally {
               reader.dispose();
               IOUtils.closeQuietly(stream);
            }

            input.reset();
            return reader.getFormatName();
         }
      }
   }

   public static PictureTexture loadImage(DownloadThread thread) {
      Object texture = null;
      if(!thread.hasFailed()) {
         if(thread.processedImage.isAnimated()) {
            texture = new AnimatedPictureTexture(thread.processedImage);
         } else {
            texture = new OrdinaryTexture(thread.processedImage);
         }
      }

      if(texture != null) {
         Object var2 = LOCK;
         synchronized(LOCK) {
            loadedImages.put(thread.url, texture);
         }
      }

      return (PictureTexture)texture;
   }

}
