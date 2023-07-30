package org.voiculescu.reflection.c05;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class ProductTest {

    public static void main(String[] args) {
        getGetters(Product.class);
    }

    public static void getGetters(Class<?> clazz) {
        List<Object> getterNames = Arrays.stream(clazz.getDeclaredFields())
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