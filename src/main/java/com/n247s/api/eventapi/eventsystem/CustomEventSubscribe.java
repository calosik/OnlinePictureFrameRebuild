package com.n247s.api.eventapi.eventsystem;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CustomEventSubscribe {

   CustomEventSubscribe.Priority eventPriority() default CustomEventSubscribe.Priority.Normal;

   public static enum Priority {

      Highest("Highest", 0),
      High("High", 1),
      Normal("Normal", 2),
      Low("Low", 3),
      Lowest("Lowest", 4);
      // $FF: synthetic field
      private static final CustomEventSubscribe.Priority[] $VALUES = new CustomEventSubscribe.Priority[]{Highest, High, Normal, Low, Lowest};


      private Priority(String var1, int var2) {}

      public static CustomEventSubscribe.Priority getPriorityInOrder(int i) {
         switch(i) {
         case 0:
            return Highest;
         case 1:
            return High;
         case 2:
            return Normal;
         case 3:
            return Low;
         case 4:
            return Lowest;
         default:
            return null;
         }
      }

   }
}
