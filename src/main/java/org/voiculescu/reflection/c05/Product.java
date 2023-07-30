package org.voiculescu.reflection.c05;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
public class Product {
    private double price;
    private String name;
    private long quantity;
    private Date expirationDate;
}
