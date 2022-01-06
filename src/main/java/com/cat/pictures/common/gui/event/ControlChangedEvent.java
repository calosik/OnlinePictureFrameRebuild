package com.cat.pictures.common.gui.event;

import com.cat.pictures.common.gui.controls.GuiControl;
import com.cat.pictures.common.gui.event.GuiControlEvent;

public class ControlChangedEvent extends GuiControlEvent {

   public ControlChangedEvent(GuiControl source) {
      super(source);
   }

   public void isCancelable() {}
}
