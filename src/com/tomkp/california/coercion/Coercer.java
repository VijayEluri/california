package com.tomkp.california.coercion;

public interface Coercer<T> {

    T coerce(String value, String format);

}
