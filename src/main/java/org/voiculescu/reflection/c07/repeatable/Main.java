package org.voiculescu.reflection.c07.repeatable;

import lombok.SneakyThrows;
import org.voiculescu.reflection.c07.repeatable.annotations.ExecuteOnSchedule;

import static org.voiculescu.reflection.c07.repeatable.annotations.Annotations.*;

import java.lang.reflect.Method;
import java.net.URI;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@ScanPackages({"org.voiculescu.reflection.c07.repeatable.loaders"})
public class Main {

    public static void main(String[] args) {
        schedule();
    }

    public static void schedule() {
        ScanPackages scanPackages = Main.class.getAnnotation(ScanPackages.class);
        if (scanPackages == null || scanPackages.value().length == 0) {
            return;
        }
        List<Class<?>> allClasses = getAllClasses(scanPackages.value());
        List<Method> scheduledExecutorMethod = getScheduledExecutorMethod(allClasses);
        for (Method method : scheduledExecutorMethod) {
            scheduledMethodExecution(method);
        }
    }

    private static void scheduledMethodExecution(Method method) {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        Arrays.stream(method.getAnnotationsByType(ExecuteOnSchedule.class))
                .forEach(schedule -> scheduledExecutorService.scheduleAtFixedRate(
                        () -> runWhenScheduled(method),
                        schedule.delaySeconds(),
                        schedule.periodSeconds(),
                        TimeUnit.SECONDS
                ));
    }

    @SneakyThrows
    private static void runWhenScheduled(Method method) {
        Date currentDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.printf("Executing at %s%n", simpleDateFormat.format(currentDate));
        method.invoke(null);
    }

    private static List<Method> getScheduledExecutorMethod(List<Class<?>> allClasses) {
        return allClasses.stream()
                .filter(clazz -> clazz.isAnnotationPresent(ScheduledExecutorClass.class))
                .flatMap(clazz -> Arrays.stream(clazz.getDeclaredMethods()))
                .filter(method -> method.getAnnotationsByType(ExecuteOnSchedule.class).length != 0)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public static List<Class<?>> getAllClasses(String... packageNames) {
        List<Class<?>> allClasses = new ArrayList<>();

        for (String packageName : packageNames) {
            String packageRelativePath = packageName.replace('.', '/');
            URI packageUri = org.voiculescu.reflection.c07.Main.class.getResource("/" + packageRelativePath).toURI();

            if ("file".equals(packageUri.getScheme())) {
                Path packageFullPath = Paths.get(packageUri);
                allClasses.addAll(getAllPackageClasses(packageFullPath, packageName));
            } else if ("jar".equals(packageUri.getScheme())) {
                FileSystem fileSystem = FileSystems.newFileSystem(packageUri, Collections.emptyMap());

                Path packageFullPathInJar = fileSystem.getPath(packageRelativePath);
                allClasses.addAll(getAllPackageClasses(packageFullPathInJar, packageName));

                fileSystem.close();
            }
        }
        return allClasses;
    }

    @SneakyThrows
    private static List<Class<?>> getAllPackageClasses(Path packagePath, String packageName) {

        if (!Files.exists(packagePath)) {
            return Collections.emptyList();
        }

        List<Path> files = Files.list(packagePath).filter(Files::isRegularFile).collect(Collectors.toList());

        List<Class<?>> classes = new ArrayList<>();

        for (Path filePath : files) {
            String fileName = filePath.getFileName().toString();

            if (fileName.endsWith(".class")) {
                String classFullName = packageName.isBlank() ? fileName.replaceFirst("\\.class$", "") : packageName + "." + fileName.replaceFirst("\\.class$", "");

                Class<?> clazz = Class.forName(classFullName);
                classes.add(clazz);
            }
        }
        return classes;
    }
}
