package org.voiculescu.reflection.c07.bestgame;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

import static org.voiculescu.reflection.c07.bestgame.annotations.Annotations.*;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        BestGamesFinder bestGamesFinder = new BestGamesFinder();

        List<String> bestGamesInDescendingOrder = execute(bestGamesFinder);

        System.out.println(bestGamesInDescendingOrder);
    }

    public static <T> T execute(Object instance) throws InvocationTargetException, IllegalAccessException {
        Class<?> clazz = instance.getClass();

        Map<String, Method> operationToMethod = getOperationToMethod(clazz);

        Method finalResultMethod = findFinalResultMethod(clazz);

        return (T) executeWithDependencies(instance, finalResultMethod, operationToMethod);
    }

    @SneakyThrows
    private static Object executeWithDependencies(Object instance, Method currentMethod, Map<String, Method> operationToMethod) {
        return currentMethod.invoke(instance,
                Arrays.stream(currentMethod.getParameters())
                        .filter(param -> param.isAnnotationPresent(DependsOn.class))
                        .map(parameter -> {
                            String dependencyOperationName = parameter.getAnnotation(DependsOn.class).value();
                            Method dependencyMethod = operationToMethod.get(dependencyOperationName);
                            return executeWithDependencies(instance, dependencyMethod, operationToMethod);
                        }).toArray());
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
}
