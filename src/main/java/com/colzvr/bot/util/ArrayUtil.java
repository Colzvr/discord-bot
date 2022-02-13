package com.colzvr.bot.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ArrayUtil {

    public static int indexOf(Object[] array, Object value) {
        for (int i = 0; i < array.length; i++) {
            if (value == null && array[i] == null)
                return i;

            if (value != null && value.equals(array[i]))
                return i;
        }

        return -1;
    }
}
