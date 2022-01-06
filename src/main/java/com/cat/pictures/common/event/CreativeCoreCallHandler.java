package com.cat.pictures.common.event;

import com.n247s.api.eventapi.eventsystem.EventApiCallHandler;
import com.n247s.api.eventapi.eventsystem.EventType;
import java.util.HashMap;

public class CreativeCoreCallHandler extends EventApiCallHandler {

   public CreativeCoreCallHandler(Class eventType) {
      super(eventType);
   }

   protected boolean CallInstances(EventType eventType) {
      return super.CallInstances(eventType);
   }

   public HashMap getInstanceMap() {
      return this.instanceMap;
   }
}
