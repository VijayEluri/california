package com.tomkp.california.coercion;

import org.omg.CORBA.REBIND;

public class IntegerCoercer implements Coercer<Integer> {

    @Override
    public Integer coerce(String value, String format) {
        if (value == null || value.trim().length() == 0) {
            return 0;
        }
        return Integer.parseInt(value);
    }

}
