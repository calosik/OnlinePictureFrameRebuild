package com.cat.pictures.gui;

import com.cat.pictures.common.gui.SubGui;
import com.cat.pictures.common.gui.controls.GuiAnalogeSlider;
import com.cat.pictures.common.gui.controls.GuiButton;
import com.cat.pictures.common.gui.controls.GuiCheckBox;
import com.cat.pictures.common.gui.controls.GuiLabel;
import com.cat.pictures.common.gui.controls.GuiStateButton;
import com.cat.pictures.common.gui.controls.GuiSteppedSlider;
import com.cat.pictures.common.gui.controls.GuiTextfield;
import com.cat.pictures.common.gui.event.ControlClickEvent;
import com.cat.pictures.common.tileentity.TileEntityPicFrame;
import com.n247s.api.eventapi.eventsystem.CustomEventSubscribe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;

@SideOnly(Side.CLIENT)
public class SubGuiPic extends SubGui {

   private final TileEntityPicFrame frame;
   private static final int OFFSET_X = 160;
   private static final int MAX_SIZE = 18;


   public SubGuiPic(TileEntityPicFrame frame) {
      this.frame = frame;
   }

   public void createControls() {
      this.controls.add(new GuiLabel("\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435 \u0418\u0437\u043E\u0431\u0440\u0430\u0436\u0435\u043D\u0438\u044F", 160, 10));
      this.controls.add(new GuiTextfield("url", this.frame.url, 160, 25, 185, 20));
      this.controls.add(new GuiLabel("\u0420\u0430\u0437\u043C\u0435\u0440\u044B", 160, 53));
      this.controls.add((new GuiTextfield("sizeX", this.frame.sizeX + "", 160, 70, 40, 20)).setFloatOnly());
      this.controls.add((new GuiTextfield("sizeY", this.frame.sizeY + "", 205, 70, 40, 20)).setFloatOnly());
      this.controls.add(new GuiButton("reX", "x->y", 250, 70, 46));
      this.controls.add(new GuiButton("reY", "y->x", 300, 70, 46));
      this.controls.add(new GuiCheckBox("flip", "\u041E\u0442\u0437\u0435\u0440\u043A\u0430\u043B\u0438\u0442\u044C", 160, 100, this.frame.flipped));
      this.controls.add(new GuiStateButton("head", this.frame.headPos, 256, 96, new String[]{"\u041F\u043E\u0432\u043E\u0440\u043E\u0442 (\u043D\u0435\u0442)", "\u041F\u043E\u0432\u043E\u0440\u043E\u0442 (\u0422\u043E\u043B\u044C\u043A\u043E X)", "\u041F\u043E\u0432\u043E\u0440\u043E\u0442 (\u0412\u0441\u0435)"}));
      this.controls.add(new GuiStateButton("posX", this.frame.posX, 160, 120, new String[]{"left (x)", "center (x)", "right (x)"}));
      this.controls.add(new GuiStateButton("posY", this.frame.posY, 256, 120, new String[]{"left (y)", "center (y)", "right (y)"}));
      this.controls.add(new GuiCheckBox("visibleFrame", "\u041E\u0442\u043E\u0431\u0440\u0430\u0436\u0435\u043D\u0438\u0435 \u0411\u043B\u043E\u043A\u0430", 160, 145, this.frame.visibleFrame));
      this.controls.add(new GuiLabel("\u041F\u0440\u043E\u0437\u0440\u0430\u0447\u043D\u043E\u0441\u0442\u044C", 160, 165));
      this.controls.add(new GuiAnalogeSlider("transparency", 160, 180, 185, 5, this.frame.transparency, 0.0F, 1.0F));
      this.controls.add(new GuiLabel("\u0414\u0438\u0441\u0442\u0430\u043D\u0446\u0438\u044F \u041E\u0442\u043E\u0431\u0440\u0430\u0436\u0435\u043D\u0438\u044F ", 160, 195));
      this.controls.add(new GuiSteppedSlider("renderDistance", 160, 210, 185, 15, this.frame.renderDistance, 4.0F, 64.0F));
      this.controls.add(new GuiButton("Save", "\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C", 160, 230, 185));
   }

   @CustomEventSubscribe
   public void onClicked(ControlClickEvent event) {
      GuiTextfield url;
      if(!event.source.is("reX") && !event.source.is("reY")) {
         if(event.source.is("Save")) {
            NBTTagCompound nbt1 = new NBTTagCompound();
            url = (GuiTextfield)this.getControl("url");
            GuiTextfield sizeX1 = (GuiTextfield)this.getControl("sizeX");
            GuiTextfield sizeY1 = (GuiTextfield)this.getControl("sizeY");
            GuiStateButton buttonPosX = (GuiStateButton)this.getControl("posX");
            GuiStateButton buttonPosY = (GuiStateButton)this.getControl("posY");
            GuiStateButton head = (GuiStateButton)this.getControl("head");
            GuiCheckBox flip = (GuiCheckBox)this.getControl("flip");
            GuiAnalogeSlider transparency = (GuiAnalogeSlider)this.getControl("transparency");
            GuiCheckBox visibleFrame = (GuiCheckBox)this.getControl("visibleFrame");
            GuiSteppedSlider renderDistance = (GuiSteppedSlider)this.getControl("renderDistance");
            nbt1.setByte("posX", (byte)buttonPosX.getState());
            nbt1.setByte("posY", (byte)buttonPosY.getState());
            nbt1.setByte("headPos", (byte)head.getState());
            nbt1.setBoolean("flipped", flip.value);
            nbt1.setFloat("transparency", transparency.value);
            nbt1.setBoolean("visibleFrame", visibleFrame.value);
            nbt1.setInteger("render", (int)renderDistance.value);
            nbt1.setString("url", url.text);

            float x;
            try {
               x = Float.parseFloat(sizeX1.text);
            } catch (Exception var17) {
               x = 1.0F;
            }

            float y;
            try {
               y = Float.parseFloat(sizeY1.text);
            } catch (Exception var16) {
               y = 1.0F;
            }

            x = Math.min(x, 18.0F);
            y = Math.min(y, 18.0F);
            nbt1.setFloat("x", x);
            nbt1.setFloat("y", y);
            this.sendPacketToServer(nbt1);
         }
      } else {
         GuiTextfield nbt = (GuiTextfield)this.getControl("sizeX");
         url = (GuiTextfield)this.getControl("sizeY");

         float sizeX;
         try {
            sizeX = Float.parseFloat(nbt.text);
         } catch (Exception var19) {
            sizeX = 1.0F;
         }

         float sizeY;
         try {
            sizeY = Float.parseFloat(url.text);
         } catch (Exception var18) {
            sizeY = 1.0F;
         }

         if(this.frame.texture != null) {
            if(event.source.is("reX")) {
               url.text = "" + (float)this.frame.texture.height / ((float)this.frame.texture.width / sizeX);
            } else {
               nbt.text = "" + (float)this.frame.texture.width / ((float)this.frame.texture.height / sizeY);
            }
         }
      }

   }
}
