package org.voiculescu.reflection.c07.coding.ex9;


import org.voiculescu.reflection.c07.coding.ex9.internal.MethodOperations;
import org.voiculescu.reflection.c07.coding.ex9.internal.OperationType;
import org.voiculescu.reflection.c07.coding.ex9.internal.Permissions;

import static org.voiculescu.reflection.c07.coding.ex9.internal.OperationType.READ;

@Permissions(role = Role.CLERK, allowed = READ)
@Permissions(role = Role.MANAGER, allowed = {READ, OperationType.WRITE})
@Permissions(role = Role.SUPPORT_ENGINEER, allowed = {READ, OperationType.WRITE, OperationType.DELETE})
public class CompanyDataStore {
    private User user;

    public void CompanyDataStore(User user) {
        this.user = user;
    }

    @MethodOperations(READ)
    public AccountRecord[] readAccounts(String[] accountIds) throws Throwable {
        PermissionsChecker.checkPermissions(this, "readAccounts");
        // ...
        return new AccountRecord[]{};
    }

    @MethodOperations({READ, OperationType.WRITE})
    public void updateAccount(String accountId, AccountRecord record) throws Throwable {
        PermissionsChecker.checkPermissions(this, "updateAccount");
        // ...
    }

    @MethodOperations(READ)
    public Summary readAccountSummary(Role callerRole, String accountId) throws Throwable {
        PermissionsChecker.checkPermissions(this, "readAccountSummary");
        // ...
        return new Summary();
    }

    @MethodOperations(OperationType.DELETE)
    public void deleteAccount(String accountId) throws Throwable {
        PermissionsChecker.checkPermissions(this, "deleteAccount");
        // ...
    }
}
