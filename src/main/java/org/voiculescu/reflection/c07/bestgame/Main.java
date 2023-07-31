package org.voiculescu.reflection.c07.bestgame;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.voiculescu.reflection.c07.bestgame.annotations.Annotations.*;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
//        BestGamesFinder bestGamesFinder = new BestGamesFinder();
//
//        List<String> bestGamesInDescendingOrder = execute(bestGamesFinder);
//
//        System.out.println(bestGamesInDescendingOrder);
        SqlQueryBuilder sqlQueryBuilder = new SqlQueryBuilder(Arrays.asList("1", "2", "3"), 10, "Movies", Arrays.asList("Id", "Name"));
        Object execute = execute(sqlQueryBuilder);
        System.out.println(execute);
    }

    public static <T> T execute(Object instance) throws InvocationTargetException, IllegalAccessException {
        Class<?> clazz = instance.getClass();

        Map<String, Method> operationToMethod = getOperationToMethod(clazz);
        Map<String, Field> inputField = getInputToField(clazz);

        Method finalResultMethod = findFinalResultMethod(clazz);

        return (T) executeWithDependencies(instance, finalResultMethod, operationToMethod, inputField);
    }

    @SneakyThrows
    private static Object executeWithDependencies(Object instance,
                                                  Method currentMethod,
                                                  Map<String, Method> operationToMethod,
                                                  Map<String, Field> inputToField) {
        List<Object> parameterValues = new ArrayList<>(currentMethod.getParameterCount());

        for (Parameter parameter : currentMethod.getParameters()) {
            Object value = null;
            if (parameter.isAnnotationPresent(DependsOn.class)) {
                String dependencyOperationName = parameter.getAnnotation(DependsOn.class).value();
                Method dependencyMethod = operationToMethod.get(dependencyOperationName);

                value = executeWithDependencies(instance, dependencyMethod, operationToMethod, inputToField);
            } else if (parameter.isAnnotationPresent(Input.class)) {
                String inputName = parameter.getAnnotation(Input.class).value();

                Field field = inputToField.get(inputName);
                field.setAccessible(true);

                value = field.get(instance);
            }

            parameterValues.add(value);
        }

        return currentMethod.invoke(instance, parameterValues.toArray());
    }

    private static Map<String, Method> getOperationToMethod(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Operation.class))
                .collect(Collectors.toMap(method -> method.getAnnotation(Operation.class).value(), method -> method));
    }

    private static Method findFinalResultMethod(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(FinalResult.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No method found with FinalResult annotation"));
    }

    private static Map<String, Field> getInputToField(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Input.class))
                .collect(Collectors.toMap(field -> field.getAnnotation(Input.class).value(), field -> field));

    }
}
