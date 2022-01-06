package com.cat.pictures.common.event;

import com.cat.pictures.common.event.CreativeCoreEventBus;
import com.n247s.api.eventapi.eventsystem.EventType;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;

public class TickHandler {

   static ArrayList ServerEvents = new ArrayList();
   @SideOnly(Side.CLIENT)
   public static int defaultScale;
   @SideOnly(Side.CLIENT)
   public static boolean changed;
   @SideOnly(Side.CLIENT)
   public static ArrayList ClientEvents;


   @SubscribeEvent
   public void onEventTick(TickEvent tick) {}

   @SideOnly(Side.CLIENT)
   @SubscribeEvent
   public void onTick(RenderTickEvent tick) {
      if(tick.phase == Phase.START) {
         if(ClientEvents == null) {
            ClientEvents = new ArrayList();
         }

         try {
            Iterator var2 = ClientEvents.iterator();

            while(var2.hasNext()) {
               CreativeCoreEventBus clientEvent = (CreativeCoreEventBus)var2.next();

               for(int j = 0; j < clientEvent.eventsToRaise.size(); ++j) {
                  clientEvent.raiseEvent((EventType)clientEvent.eventsToRaise.get(j), true);
               }

               clientEvent.eventsToRaise.clear();
            }
         } catch (Exception var5) {
            ;
         }
      }

   }

}
