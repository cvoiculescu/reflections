package org.voiculescu.reflection.c02.tictactow_restricted;

import org.voiculescu.reflection.c02.tictactow_restricted.game.Game;
import org.voiculescu.reflection.c02.tictactow_restricted.game.internal.TicTacToeGame;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Game game = createObjectRecursively(TicTacToeGame.class);
        game.startGame();
    }

    public static <T> T createObjectRecursively(Class<T> tClass) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = getFirstConstructor(tClass);
        List<Object> constructorArguments =
                new ArrayList<>();
        for (Class<?> aClass : constructor.getParameterTypes()) {
            Object objectRecursively = createObjectRecursively(aClass);
            constructorArguments.add(objectRecursively);
        }
        constructor.setAccessible(true);
        return (T) constructor.newInstance(constructorArguments.toArray());
    }

    private static Constructor<?> getFirstConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        if (constructors.length == 0) {
            throw new IllegalStateException(format("No constructor has been found for class %s\n", clazz.getName()));
        }
        return constructors[0];
    }
}
