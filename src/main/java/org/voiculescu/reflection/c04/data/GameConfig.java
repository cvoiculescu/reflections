package org.voiculescu.reflection.c04.data;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GameConfig {
    private int releaseYear;
    private String gameName;
    private double price;
    private String[] characterNames;

}
