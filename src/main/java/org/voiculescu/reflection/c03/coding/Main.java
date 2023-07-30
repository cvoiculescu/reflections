package org.voiculescu.reflection.c03.coding;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        AccountSummary accountSummary = new AccountSummary("John", "Smith", (short) 20, 100_000);
        ObjectSizeCalculator objectSizeCalculator = new ObjectSizeCalculator();
        System.out.println(objectSizeCalculator.sizeOfObject(accountSummary));
    }
}
