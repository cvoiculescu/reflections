package org.voiculescu.reflection.c05;


import lombok.SneakyThrows;
import org.voiculescu.reflection.c05.inherit.ClothingProduct;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ProductTest {

    public static void main(String[] args) {
        getGetters(ClothingProduct.class);
        System.out.println("---------------------------");
        testSetters(ClothingProduct.class);
    }

    public static void testSetters(Class<?> clazz) {

        List<Method> collect = getAllFields(clazz).stream()
                .map(field -> {
                    String setterName = "set" + capitalizeFirstLetter(field.getName());
                    try {
                        return clazz.getMethod(setterName, field.getType());
                    } catch (NoSuchMethodException e) {
                        System.err.printf("Setter: %s not found", setterName);
                    }
                    return null;
                })
                .collect(Collectors.toList());
        collect.forEach(System.out::println);

    }

    private static List<Field> getAllFields(Class<?> clazz) {
        if (clazz == null || clazz.equals(Object.class)) {
            return Collections.emptyList();
        }
        Field[] currentClassFields = clazz.getDeclaredFields();
        List<Field> inheritedFields = getAllFields(clazz.getSuperclass());
        Set<Field> allFields = new HashSet<>();
        allFields.addAll(Arrays.asList(currentClassFields));
        allFields.addAll(inheritedFields);
        return new ArrayList<>(allFields);
    }

    public static void getGetters(Class<?> clazz) {
        List<Object> getterNames = getAllFields(clazz).stream()
                .map(Field::getName)
                .map(fieldName -> "get" + capitalizeFirstLetter(fieldName))
                .collect(Collectors.toList());
        Map<String, Method> map = mapMethodNameToMethod(clazz);
        Map<String, Method> getters = map.keySet().stream()
                .filter(getterNames::contains)
                .collect(Collectors.toMap(key -> key, map::get));
        getters.keySet().forEach(key -> System.out.println(getters.get(key)));
    }

    private static String capitalizeFirstLetter(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    private static Map<String, Method> mapMethodNameToMethod(Class<?> dataClass) {
        return Arrays.stream(dataClass.getMethods())
                .filter(method -> method.getName().startsWith("get"))
                .collect(Collectors.toMap(Method::getName, method -> method));
    }

}