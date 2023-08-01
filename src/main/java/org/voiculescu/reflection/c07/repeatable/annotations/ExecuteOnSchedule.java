package org.voiculescu.reflection.c07.repeatable.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ExecutionSchedules.class)
public @interface ExecuteOnSchedule {
    int delaySeconds() default 0;

    int periodSeconds();
}
