package org.voiculescu.reflection.c03.coding;

import java.lang.reflect.Array;

public class ArrayReader {

    public Object getArrayElement(Object array, int index) {
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("provided object is not of type array");
        }

        int length = Array.getLength(array);
        int pos = index >= 0 ? index : length + index;
        if (pos < 0 || pos >= length) {
            throw new IllegalArgumentException("index out of scope");
        }
        return Array.get(array, pos);
    }

}
