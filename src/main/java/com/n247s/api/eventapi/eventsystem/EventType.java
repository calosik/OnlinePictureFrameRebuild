package com.n247s.api.eventapi.eventsystem;


public abstract class EventType {

   public abstract void isCancelable();

   final boolean isCanceled() {
      return false;
   }
}
