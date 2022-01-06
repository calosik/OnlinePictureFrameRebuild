package com.n247s.api.eventapi.eventsystem;

import com.n247s.api.eventapi.eventsystem.CallHandler;
import com.n247s.api.eventapi.eventsystem.CustomEventSubscribe;
import com.n247s.api.eventapi.eventsystem.EventType;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class EventApiCallHandler extends CallHandler {

   public EventApiCallHandler(Class eventType) {}

   protected boolean CallInstances(EventType eventType) {
      boolean isCanceled = false;

      try {
         isCanceled = this.CallInstancesInOrder(eventType);
      } catch (Exception var4) {
         ;
      }

      return isCanceled;
   }

   private boolean CallInstancesInOrder(EventType eventTypeInstance) {
      try {
         for(int i = 0; i < 5; ++i) {
            LinkedHashMap linkedHashMap = (LinkedHashMap)this.instanceMap.get(CustomEventSubscribe.Priority.getPriorityInOrder(i));
            if(linkedHashMap != null) {
               Iterator var4 = linkedHashMap.keySet().iterator();

               while(var4.hasNext()) {
                  Object originalEntry = var4.next();
                  ((Method)linkedHashMap.get(originalEntry)).invoke(originalEntry, new Object[]{eventTypeInstance});
               }
            }
         }
      } catch (Exception var6) {
         ;
      }

      return eventTypeInstance.isCanceled();
   }
}
