package com.cat.pictures.common.event;

import com.cat.pictures.common.event.CreativeCoreCallHandler;
import com.cat.pictures.common.event.TickHandler;
import com.n247s.api.eventapi.eventsystem.CallHandler;
import com.n247s.api.eventapi.eventsystem.EventBus;
import com.n247s.api.eventapi.eventsystem.EventType;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;

public class CreativeCoreEventBus extends EventBus {

   ArrayList eventsToRaise = new ArrayList();


   public CreativeCoreEventBus() {
      if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
         this.initClient();
      } else {
         this.initServer();
      }

   }

   private void initServer() {
      TickHandler.ServerEvents.add(this);
   }

   @SideOnly(Side.CLIENT)
   public void initClient() {
      TickHandler.ClientEvents.add(this);
   }

   void raiseEvent(EventType event, boolean force) {
      event.isCancelable();
      if(!this.EventList.containsKey(event.getClass())) {
         this.EventList.put(event.getClass(), new CreativeCoreCallHandler(event.getClass()));
      }

      ((CreativeCoreCallHandler)this.EventList.get(event.getClass())).CallInstances(event);
   }

   public void raiseEvent(EventType eventType) {
      try {
         Class eventClass = eventType.getClass();
         if(!this.EventList.containsKey(eventClass)) {
            this.EventList.put(eventClass, new CreativeCoreCallHandler(eventClass));
         }

         ((CreativeCoreCallHandler)this.EventList.get(eventClass)).CallInstances(eventType);
      } catch (Exception var3) {
         ;
      }

   }

   public void removeAllEventListeners() {
      Iterator var1 = this.EventList.values().iterator();

      while(var1.hasNext()) {
         Object value = var1.next();
         ((CreativeCoreCallHandler)value).getInstanceMap().clear();
      }

      this.EventList.clear();
      if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
         this.removeTickEventClient();
      } else {
         this.removeTickEventServer();
      }

   }

   private void removeTickEventServer() {
      TickHandler.ServerEvents.remove(this);
   }

   @SideOnly(Side.CLIENT)
   public void removeTickEventClient() {
      TickHandler.ClientEvents.remove(this);
   }

   public CallHandler getCallHandlerFromEventType(Class eventTypeClass) {
      if(!this.EventList.containsKey(eventTypeClass)) {
         this.EventList.put(eventTypeClass, new CreativeCoreCallHandler(eventTypeClass));
      }

      return (CallHandler)this.EventList.get(eventTypeClass);
   }
}
