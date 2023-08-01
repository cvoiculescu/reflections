package org.voiculescu.reflection.c07.coding.ex9.internal;

import org.voiculescu.reflection.c07.coding.ex9.Role;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(PermissionsContainer.class)
public @interface Permissions {
    Role role();

    OperationType[] allowed();

}


