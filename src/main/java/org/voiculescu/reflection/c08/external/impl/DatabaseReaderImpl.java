package org.voiculescu.reflection.c08.external.impl;

import org.voiculescu.reflection.c08.external.DatabaseReader;

public final class DatabaseReaderImpl implements DatabaseReader {
    @Override
    public int countRowsInTable(String tableName) throws InterruptedException {
        System.out.printf("DatabaseReaderImpl - counting rows in table %s%n", tableName);

        Thread.sleep(1000);
        return 50;
    }

    @Override
    public String[] readRow(String sqlQuery) throws InterruptedException {
        System.out.printf("DatabaseReaderImpl - Executing SQL query : %s%n", sqlQuery);

        Thread.sleep(1500);
        return new String[]{"column1", "column2", "column3"};
    }
}
