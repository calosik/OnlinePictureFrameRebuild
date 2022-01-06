package com.cat.pictures.common.gui.event;

import com.cat.pictures.common.gui.controls.GuiControl;
import com.n247s.api.eventapi.eventsystem.EventType;

public abstract class GuiControlEvent extends EventType {

   public GuiControl source;


   GuiControlEvent(GuiControl source) {
      this.source = source;
   }
}
