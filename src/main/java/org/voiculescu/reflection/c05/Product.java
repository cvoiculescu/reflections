package org.voiculescu.reflection.c05;

import lombok.Getter;

import java.util.Date;

@Getter
public class Product {
    private double price;
    private String name;
    private long quantity;
    private Date expirationDate;
}
