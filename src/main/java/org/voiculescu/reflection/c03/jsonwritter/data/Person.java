package org.voiculescu.reflection.c03.jsonwritter.data;

import lombok.Data;

@Data
public class Person {

    private final String name;
    private final boolean employed;
    private final int age;
    private final float salary;
    private final Address address;
    private final Company company;

}
