package com.n247s.api.eventapi.eventsystem;

import java.lang.reflect.Method;
import java.util.HashMap;

public class EventBus {

   protected HashMap EventList = new HashMap();


   public void raiseEvent(EventType eventType) {
      try {
         Class eventClass = eventType.getClass();
         if(!this.EventList.containsKey(eventClass)) {
            this.EventList.put(eventClass, new EventApiCallHandler(eventClass));
         }

         ((CallHandler)this.EventList.get(eventClass)).CallInstances(eventType);
      } catch (Exception var3) {
         ;
      }

   }

   public void RegisterEventListener(Object listener) {
      Class clazz = !(listener instanceof Class)?listener.getClass():(Class)listener;
      Method[] methods = clazz.getMethods();
      Method[] var4 = methods;
      int var5 = methods.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Method currentMethod = var4[var6];
         if(currentMethod.isAnnotationPresent(CustomEventSubscribe.class)) {
            CustomEventSubscribe annotation = (CustomEventSubscribe)currentMethod.getAnnotation(CustomEventSubscribe.class);
            Class methodEventType = currentMethod.getParameterTypes()[0];
            this.getCallHandlerFromEventType(methodEventType).RegisterEventListener(annotation.eventPriority(), listener, currentMethod);
         }
      }

   }

   public CallHandler getCallHandlerFromEventType(Class eventTypeClass) {
      if(!this.EventList.containsKey(eventTypeClass)) {
         this.EventList.put(eventTypeClass, new EventApiCallHandler(eventTypeClass));
      }

      return (CallHandler)this.EventList.get(eventTypeClass);
   }
}
