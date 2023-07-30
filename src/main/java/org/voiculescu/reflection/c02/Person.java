package org.voiculescu.reflection.c02;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString(exclude = "address")
public class Person {
    private final Address address;
    private final String name;
    private final int age;

    public Person(String name) {
        this(null, name, 0);
    }

    public Person(String name, int age) {
        this(null, name, age);
    }
}
