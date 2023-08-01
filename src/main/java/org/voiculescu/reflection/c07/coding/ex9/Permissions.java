package org.voiculescu.reflection.c07.coding.ex9;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(PermissionsContainer.class)
public @interface Permissions {
    Role role();

    OperationType[] allowed();

}


