package org.voiculescu.reflection.c03.jsonwritter.data;

import lombok.Data;

@Data
public class Movie {
    private final String name;
    private final float rating;
    private final String[] categories;
    private final Actor[] actors;
}
