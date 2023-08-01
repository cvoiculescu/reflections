package org.voiculescu.reflection.c07.repeatable.loaders;

import org.voiculescu.reflection.c07.repeatable.annotations.ExecuteOnSchedule;

import static org.voiculescu.reflection.c07.repeatable.annotations.Annotations.*;

@ScheduledExecutorClass
public class Cache {

    @ExecuteOnSchedule(periodSeconds = 5)
    @ExecuteOnSchedule(periodSeconds = 1,delaySeconds = 10)
    public static void reloadCache() {
        System.out.println("Reloading cache...");
    }

}
