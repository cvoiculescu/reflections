package org.voiculescu.reflection.c03.jsonwritter.data;

import lombok.Data;

@Data
public class Company {
    private final String name;
    private final String city;
    private final Address address;
}
