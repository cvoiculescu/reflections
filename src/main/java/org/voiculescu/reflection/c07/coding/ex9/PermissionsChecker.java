package org.voiculescu.reflection.c07.coding.ex9;


import org.voiculescu.reflection.c07.coding.ex9.internal.MethodOperations;
import org.voiculescu.reflection.c07.coding.ex9.internal.OperationType;
import org.voiculescu.reflection.c07.coding.ex9.internal.PermissionException;
import org.voiculescu.reflection.c07.coding.ex9.internal.Permissions;

import java.util.*;
import java.lang.reflect.*;
import java.util.stream.Collectors;

public class PermissionsChecker {

    /**
     * Checks that the logged-in user in the callerObject has the right permissions to perform the operations
     * in the caller method.
     * Throws PermissionException if the user is not authorized to perform those operations based on the user's
     * role
     */
    public static void checkPermissions(Object callerObject, String callerMethodName) throws Throwable {
        // DO NOT MODIFY THIS METHOD
        User user = getLoggedInUser(callerObject);
        Method callingMethod = getCallingMethod(callerObject, callerMethodName);
        Permissions[] allPermissions = getClassAnnotatedPermissions(callerObject);
        MethodOperations methodOperations = getCallerMethodOperations(callingMethod);

        OperationType[] methodOperationTypes = methodOperations.value();

        List<OperationType> userAllowedOperations = findUserAllowedOperations(allPermissions, user);

        for (OperationType methodOperationsTypes : methodOperationTypes) {
            if (!userAllowedOperations.contains(methodOperationsTypes)) {
                throw new PermissionException();
            }
        }
    }

    /**
     * Returns a List of all OperationTypes that the logged-in user is allowed to perform
     * If the user has no permissions in the allPermissions, an empty list should be returned
     */
    static List<OperationType> findUserAllowedOperations(Permissions[] allPermissions, User user) {
        return Arrays.stream(allPermissions)
                .filter(currentPermissions -> user.getRole() == currentPermissions.role())
                .findFirst()
                .map(currentPermissions -> Arrays.asList(currentPermissions.allowed()))
                .orElse(Collections.emptyList());
    }

    /**
     * Returns all the Permissions annotations the the callerObject's class is annotated with
     */
    static Permissions[] getClassAnnotatedPermissions(Object callerObject) {
        return callerObject.getClass()
                .getAnnotationsByType(Permissions.class);
    }

    /**
     * Returns the MethodOperations annotation that the callerMethod is annotated with
     */
    static MethodOperations getCallerMethodOperations(Method callerMethod) {
        return callerMethod.getAnnotation(MethodOperations.class);
    }


//********************* Helper Methods ******************************/

    /**
     * Returns the User object representing the logged-in user
     */
    private static User getLoggedInUser(Object callerObject) throws NoSuchFieldException, IllegalAccessException {
        // DO NOT MODIFY THIS METHOD
        Class<?> callerClass = callerObject.getClass();

        Field userField = callerClass.getDeclaredField("user");

        userField.setAccessible(true);

        if (!userField.getType().equals(User.class)) {
            throw new IllegalStateException("The caller object must have a user field of type User");
        }

        return (User) userField.get(callerObject);
    }

    /**
     * Returns the Method object of the callerObject's class corresponding to the methodName
     */
    private static Method getCallingMethod(Object callerObject, String methodName) {
        // DO NOT MODIFY THIS METHOD
        return Arrays.stream(callerObject.getClass().getDeclaredMethods())
                .filter(method -> method.getName().equals(methodName))
                .findFirst()
                .orElseThrow(()
                        -> new IllegalStateException(String.format("The passed method name :%s does not exist", methodName)));
    }
}
