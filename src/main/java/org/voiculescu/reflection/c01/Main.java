package org.voiculescu.reflection.c01;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        Class<String> stringClass = String.class;

        Map<String, Integer> mapObject = new HashMap<>();

        Class<?> hashMapClass = mapObject.getClass();

        Class<?> squareClass = Class.forName("org.voiculescu.reflection.Main$Square");

        printClassInfo(stringClass, hashMapClass, squareClass);

        var circleObject = new Drawable() {
            @Override
            public int getNumberOfCorners() {
                return 0;
            }
        };
        System.out.println("---------------------------------------------------");

        printClassInfo(Collection.class, boolean.class, int[][].class, Color.class, circleObject.getClass());

    }

    private static void printClassInfo(Class<?>... classes) {

        for (Class<?> clazz : classes) {

            System.out.printf("class name : %s, class package name : %s%n",
                    clazz.getSimpleName(),
                    clazz.getPackageName());

            Class<?>[] implementedInterfaces = clazz.getInterfaces();

            for (Class<?> implementedInterface : implementedInterfaces) {
                System.out.printf("class %s implements : %s%n",
                        clazz.getSimpleName(),
                        implementedInterface.getSimpleName());
            }

            System.out.println("Is array : " + clazz.isArray());
            System.out.println("Is primitive : " + clazz.isPrimitive());
            System.out.println("Is enum : " + clazz.isEnum());
            System.out.println("Is interface : " + clazz.isInterface());
            System.out.println("Is anonymous :" + clazz.isAnonymousClass());

            System.out.println();
            System.out.println();
        }
    }

    private enum Color {
        BLUE,
        RED,
        GREEN
    }

    private static interface Drawable {
        int getNumberOfCorners();
    }

    static class Square implements Drawable {

        @Override
        public int getNumberOfCorners() {
            return 4;
        }
    }

}