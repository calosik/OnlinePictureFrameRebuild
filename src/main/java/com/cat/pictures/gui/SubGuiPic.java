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
      this.controls.add(new GuiLabel("Название Изображения", 160, 10));
      this.controls.add(new GuiTextfield("url", this.frame.url, 160, 25, 185, 20));
      this.controls.add(new GuiLabel("Размеры", 160, 53));
      this.controls.add((new GuiTextfield("sizeX", this.frame.sizeX + "", 160, 70, 40, 20)).setFloatOnly());
      this.controls.add((new GuiTextfield("sizeY", this.frame.sizeY + "", 205, 70, 40, 20)).setFloatOnly());
      this.controls.add(new GuiButton("reX", "x->y", 250, 70, 46));
      this.controls.add(new GuiButton("reY", "y->x", 300, 70, 46));
      this.controls.add(new GuiCheckBox("flip", "Отзеркалить", 160, 100, this.frame.flipped));
      this.controls.add(new GuiStateButton("head", this.frame.headPos, 256, 96, new String[]{"Поворот (нет)", "Поворот (Только X)", "Поворот (Все)"}));
      this.controls.add(new GuiStateButton("posX", this.frame.posX, 160, 120, new String[]{"left (x)", "center (x)", "right (x)"}));
      this.controls.add(new GuiStateButton("posY", this.frame.posY, 256, 120, new String[]{"left (y)", "center (y)", "right (y)"}));
      this.controls.add(new GuiCheckBox("visibleFrame", "Отображение Блока", 160, 145, this.frame.visibleFrame));
      this.controls.add(new GuiLabel("Прозрачность", 160, 165));
      this.controls.add(new GuiAnalogeSlider("transparency", 160, 180, 185, 5, this.frame.transparency, 0.0F, 1.0F));
      this.controls.add(new GuiLabel("Дистанция Отображения ", 160, 195));
      this.controls.add(new GuiSteppedSlider("renderDistance", 160, 210, 185, 15, this.frame.renderDistance, 4.0F, 64.0F));
      this.controls.add(new GuiButton("Save", "Сохранить", 160, 230, 185));
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
