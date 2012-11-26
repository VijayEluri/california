package com.tomkp.california.coercion;

public class ClassCoercer implements Coercer<Class> {


    @Override
    public Class coerce(String value, String format) {
        if (value != null && !value.isEmpty()) {
            try {
                return Class.forName(value);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("unable to coerce '" + value + "'to Class", e);
            }
        }
        throw new RuntimeException("unable to coerce '" + value + "'to Class");
    }


}
