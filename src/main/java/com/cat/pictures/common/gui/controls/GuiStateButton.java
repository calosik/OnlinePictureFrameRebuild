package com.cat.pictures.common.gui.controls;

import com.cat.pictures.common.gui.controls.GuiButton;

public class GuiStateButton extends GuiButton {

   private final String[] states;


   private GuiStateButton(String name, String caption, int x, int y, String ... states) {
      super(name, name, x, y, 90);
      this.caption = caption;
      this.states = states;
   }

   public GuiStateButton(String name, int index, int x, int y, String ... states) {
      this(name, states[index], x, y, states);
   }

   private void setState(int index) {
      this.caption = this.states[index];
   }

   public int getState() {
      for(int i = 0; i < this.states.length; ++i) {
         if(this.states[i].equals(this.caption)) {
            return i;
         }
      }

      return -1;
   }

   private void nextState() {
      int state = this.getState();
      ++state;
      if(state < 0) {
         state = this.states.length - 1;
      }

      if(state >= this.states.length) {
         state = 0;
      }

      this.setState(state);
   }

   public boolean mousePressed(int posX, int posY, int button) {
      if(super.mousePressed(posX, posY, button)) {
         this.nextState();
         return true;
      } else {
         return false;
      }
   }
}
