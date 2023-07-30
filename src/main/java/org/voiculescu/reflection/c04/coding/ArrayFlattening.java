package org.voiculescu.reflection.c04.coding;

import java.lang.reflect.Array;

public class ArrayFlattening {

    public <T> T concat(Class<?> type, Object... arguments) {

        if (arguments.length == 0) {
            return null;
        }

        int length = 0;
        for (Object argument : arguments) {
            if (argument.getClass().isArray()) {
                length += Array.getLength(argument);
            } else {
                length += 1;
            }
        }

        Object newArray = Array.newInstance(type, length);
        int count=0;
        for (int i = 0; i < arguments.length; i++) {
            Object argument = arguments[i];
            if(argument.getClass().isArray()){
                for (int j = 0; j < Array.getLength(argument); j++) {
                    Object element = Array.get(argument, j);
                    Array.set(newArray, count,element);
                    count++;
                }
            }else{
                Array.set(newArray, count,argument);
                count++;
            }
        }
        return (T) newArray;

    }
}
