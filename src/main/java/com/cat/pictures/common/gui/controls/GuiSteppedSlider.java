package com.cat.pictures.common.gui.controls;

import com.cat.pictures.common.gui.controls.GuiAnalogeSlider;

public class GuiSteppedSlider extends GuiAnalogeSlider {

   public GuiSteppedSlider(String name, int x, int y, int width, int height, int value, float min, float max) {
      super(name, x, y, width, height, (float)value, min, max);
   }

   public void mouseMove(int posX, int posY, int button) {
      super.mouseMove(posX, posY, button);
      this.value = (float)((int)this.value);
   }

   public void setValue(float value) {
      super.setValue((float)((int)value));
   }
}
