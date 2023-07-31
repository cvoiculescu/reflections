package org.voiculescu.reflection.c07.coding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Exercise {

    /**
     * Complete your code here if necessary
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface OpenResources {
        /**
         * Complete your code here if necessary
         */
    }

    public Set<Method> getAllAnnotatedMethods(Object input) {
        return Arrays.stream(input.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(OpenResources.class))
                .collect(Collectors.toSet());
    }
}
