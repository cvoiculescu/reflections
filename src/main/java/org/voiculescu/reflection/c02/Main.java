package org.voiculescu.reflection.c02;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException, IOException, NoSuchMethodException {
        printConstructorData(Person.class);
        printConstructorData(Address.class);
        Person p1 = createInstanceWithArguments(Person.class, null, "Corneliu", 20);
        System.out.println(p1);
        Person p2 =  createInstanceWithArguments(Person.class, "Corneliu", 20);
        System.out.println(p2);

    }

    public static void printConstructorData(Class<?> clazz) {
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        System.out.format("class %s has %d declared constructors\n", clazz.getSimpleName(), declaredConstructors.length);
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            Class<?>[] parameterTypes = declaredConstructor.getParameterTypes();
            List<String> paramTypeNames = Arrays.stream(parameterTypes).map(Class::getSimpleName).collect(Collectors.toList());
            System.out.println(paramTypeNames);
        }
    }

    public static <T> T createInstanceWithArguments(Class<T> clazz, Object... args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Constructor<?> declaredConstructor : clazz.getDeclaredConstructors()) {
            if (declaredConstructor.getParameterTypes().length == args.length) {
                return (T) declaredConstructor.newInstance(args);
            }
        }
        return null;
    }

}
