package org.voiculescu.reflection.c05.inherit;

import lombok.Data;
import org.voiculescu.reflection.c05.Product;

@Data
public class ClothingProduct extends Product {
    private String color;
    private Size size;
}
