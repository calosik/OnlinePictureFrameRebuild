package com.n247s.api.eventapi.eventsystem;

import com.n247s.api.eventapi.eventsystem.CustomEventSubscribe;
import com.n247s.api.eventapi.eventsystem.EventType;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class CallHandler {

   protected final HashMap instanceMap = new HashMap();


   public CallHandler() {
      this.instanceMap.put(CustomEventSubscribe.Priority.Highest, new LinkedHashMap());
      this.instanceMap.put(CustomEventSubscribe.Priority.High, new LinkedHashMap());
      this.instanceMap.put(CustomEventSubscribe.Priority.Normal, new LinkedHashMap());
      this.instanceMap.put(CustomEventSubscribe.Priority.Low, new LinkedHashMap());
      this.instanceMap.put(CustomEventSubscribe.Priority.Lowest, new LinkedHashMap());
   }

   protected abstract boolean CallInstances(EventType var1);

   public void RegisterEventListener(CustomEventSubscribe.Priority priority, Object Listener, Method method) {
      ((LinkedHashMap)this.instanceMap.get(priority)).put(Listener, method);
   }
}
