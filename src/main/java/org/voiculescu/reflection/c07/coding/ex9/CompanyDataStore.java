package org.voiculescu.reflection.c07.coding.ex9;


@Permissions(role = Role.CLERK, allowed = OperationType.READ)
@Permissions(role = Role.MANAGER, allowed = {OperationType.READ, OperationType.WRITE})
@Permissions(role = Role.SUPPORT_ENGINEER, allowed = {OperationType.READ, OperationType.WRITE, OperationType.DELETE})
public class CompanyDataStore {

    private User user;

    public void CompanyDataStore(User user) {
        this.user = user;
    }

}
