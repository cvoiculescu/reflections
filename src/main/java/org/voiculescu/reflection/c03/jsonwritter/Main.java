package org.voiculescu.reflection.c03.jsonwritter;

import org.voiculescu.reflection.c03.jsonwritter.data.*;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

public class Main {

    public static void main(String[] args) throws IllegalAccessException {

        Actor a1 = new Actor("actor1", new String[]{"movie1", "movie2"});
        Actor a2 = new Actor("actor2", new String[]{"movie1", "movie2"});
        Actor a3 = new Actor("actor3", new String[]{"movie1", "movie2"});
        Movie movie = new Movie("Lord of the rings", 4.9f,
                new String[]{"DRAMA", "ACTION"},
                new Actor[]{a1, a2, a3});
        System.out.println(objectToJson(movie, 0));
    }

    public static String objectToJson(Object instance, int indentSize) throws IllegalAccessException {
        Field[] fields = instance.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        sb.append(indent(indentSize));
        sb.append("{").append("\n");
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if (field.isSynthetic()) {
                continue;
            }
            Class<?> fieldType = field.getType();
            Object fieldInstance = field.get(instance);
            sb.append(indent(indentSize + 1));
            sb.append(formatStringValue(field.getName())).append(": ");
            if (fieldType.isPrimitive()) {
                sb.append(formatPrimitiveValue(fieldInstance, fieldType));
            } else if (fieldType.equals(String.class)) {
                sb.append(formatStringValue(fieldInstance.toString()));
            } else if (fieldType.isArray()) {
                sb.append(arrayToJson(fieldInstance, indentSize + 1));
            } else {
                sb.append(objectToJson(fieldInstance, indentSize + 1));
            }
            if (i < fields.length - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append(indent(indentSize));
        sb.append("}");
        return sb.toString();
    }

    private static String arrayToJson(Object instance, int indentSize) throws IllegalAccessException {
        int arrayLength = Array.getLength(instance);
        Class<?> componentType = instance.getClass().getComponentType();
        StringBuilder sb = new StringBuilder();
        sb.append("[").append("\n");
        for (int i = 0; i < arrayLength; i++) {
            Object element = Array.get(instance, i);
            if (componentType.isPrimitive()) {
                sb.append(indent(indentSize + 1));
                sb.append(formatPrimitiveValue(element, componentType));
            } else if (componentType.equals(String.class)) {
                sb.append(indent(indentSize + 1));
                sb.append(formatStringValue(element.toString()));
            } else {
                sb.append(objectToJson(element, indentSize + 1));
            }
            if (i != arrayLength - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append(indent(indentSize)).append("]");
        return sb.toString();
    }

    private static String formatPrimitiveValue(Object instance, Class<?> type) throws IllegalAccessException {
        if (type.equals(boolean.class)
                || type.equals(int.class)
                || type.equals(short.class)
                || type.equals(long.class)
        ) {
            return instance.toString();
        } else if (type.equals(double.class)
                || type.equals(float.class)) {
            return format("%.2f", (float) instance);
        } else {
            throw new RuntimeException(format("Type: %s is unsupported", type.getName()));
        }
    }

    private static String indent(int indentSize) {
        return IntStream.range(0, indentSize)
                .mapToObj(i -> "\t")
                .collect(Collectors.joining());
    }

    private static String formatStringValue(String value) {
        return format("\"%s\"", value);
    }


}
