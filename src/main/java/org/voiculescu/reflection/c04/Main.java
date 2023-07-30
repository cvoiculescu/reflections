package org.voiculescu.reflection.c04;

import lombok.SneakyThrows;
import org.voiculescu.reflection.c04.data.GameConfig;
import org.voiculescu.reflection.c04.data.UserInterfaceConfig;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Scanner;

import static java.lang.String.format;

public class Main {


    private static final Path GAME_CONFIG_PATH = Path.of("src/main/resources/game-properties.cfg");
    private static final Path USER_INTERFACE_CONFIG_PATH = Path.of("src/main/resources/user-interface.cfg");

    public static void main(String[] args) {
        System.out.println(GAME_CONFIG_PATH);
        System.out.println(USER_INTERFACE_CONFIG_PATH);
        GameConfig gameConfig = createConfig(GameConfig.class, GAME_CONFIG_PATH);
        UserInterfaceConfig userInterfaceConfig = createConfig(UserInterfaceConfig.class, USER_INTERFACE_CONFIG_PATH);
        System.out.println(gameConfig);
        System.out.println(userInterfaceConfig);
    }

    @SneakyThrows
    public static <T> T createConfig(Class<T> clazz, Path filePath) {
        Scanner scanner = new Scanner(filePath);
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        T configInstance = (T) constructor.newInstance();
        while (scanner.hasNext()) {
            String next = scanner.nextLine();
            String[] split = next.split("=");
            String propertyName = split[0];
            String propertyValue = split[1];
            Field field;
            try {
                field = clazz.getDeclaredField(propertyName);
            } catch (NoSuchFieldException ex) {
                System.err.printf("Property name: %s is unsupported", propertyName);
                continue;
            }
            field.setAccessible(true);
            Object parsedValue = parseValue(field.getType(), propertyValue);
            field.set(configInstance, parsedValue);
        }
        return configInstance;
    }

    private static Object parseArray(Class<?> arrayElementType, String value) {
        String[] split = value.split(",");
        Object arrayObject = Array.newInstance(arrayElementType, split.length);
        for (int i = 0; i < split.length; i++) {
            Array.set(arrayObject, i, parseValue(arrayElementType, split[i]));
        }
        return arrayObject;
    }


    private static Object parseValue(Class<?> type, String value) {
        if (type.isArray()) {
            return parseArray(type.getComponentType(), value);
        } else if (type.equals(int.class)) {
            return Integer.parseInt(value);
        } else if (type.equals(short.class)) {
            return Short.parseShort(value);
        } else if (type.equals(long.class)) {
            return Long.parseLong(value);
        } else if (type.equals(double.class)) {
            return Double.parseDouble(value);
        } else if (type.equals(float.class)) {
            return Float.parseFloat(value);
        } else if (type.equals(String.class)) {
            return value;
        }
        throw new IllegalArgumentException(format("Type: %s unsupported\n", type.getSimpleName()));
    }
}
