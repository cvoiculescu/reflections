package org.voiculescu.reflection.c08;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.voiculescu.reflection.c08.external.DatabaseReader;
import org.voiculescu.reflection.c08.external.HttpClient;
import org.voiculescu.reflection.c08.external.impl.HttpClientImpl;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        HttpClient httpClient = createProxy(new HttpClientImpl());
        useHttpClient(httpClient);
//        DatabaseReader databaseReader = new DatabaseReaderImpl();
//        useDatabaseReader(databaseReader);
    }

    public static void useHttpClient(HttpClient httpClient) {
        httpClient.initialize();
        String response = httpClient.sendRequest("some request");

        System.out.printf("Http response is : %s%n", response);
    }

    @SneakyThrows
    public static void useDatabaseReader(DatabaseReader databaseReader) throws InterruptedException {
        int rowsInGamesTable = 0;
        try {
            rowsInGamesTable = databaseReader.countRowsInTable("GamesTable");
        } catch (IOException e) {
            System.out.println("Catching exception " + e);
            return;
        }

        System.out.printf("There are %s rows in GamesTable%n", rowsInGamesTable);

        String[] data = databaseReader.readRow("SELECT * from GamesTable");

        System.out.printf("Received %s%n", String.join(" , ", data));
    }


    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Object originalObject) {
        Class<?>[] interfaces = originalObject.getClass().getInterfaces();
        TimeMeasuringProxyHandler timeMeasuringProxyHandler = new TimeMeasuringProxyHandler(originalObject);
        return (T) Proxy.newProxyInstance(originalObject.getClass().getClassLoader(), interfaces, timeMeasuringProxyHandler);
    }

    @RequiredArgsConstructor
    public static class TimeMeasuringProxyHandler implements InvocationHandler {

        private final Object originalObject;

        @Override
        @SneakyThrows
        public Object invoke(Object proxy, Method method, Object[] args) {
            System.out.printf("Measuring Proxy - Before executing method: %s%n%n", method.getName());
            long startTime = System.currentTimeMillis();
            Object result;
            try {
                result = method.invoke(originalObject, args);
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
            long endTime = System.currentTimeMillis();
            System.out.printf("%nMeasuring Proxy - Executing of %s() took %dms%n%n", method.getName(), endTime - startTime);
            return result;
        }
    }

}
