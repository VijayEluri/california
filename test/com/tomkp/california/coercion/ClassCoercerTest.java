package com.tomkp.california.coercion;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClassCoercerTest {


    private ClassCoercer classCoercer;


    @Before
    public void setUp() {
        classCoercer = new ClassCoercer();
    }


    @Test(expected = RuntimeException.class)
    public void nullValueThrowsRuntimeException() {
        classCoercer.coerce(null, null);
    }


    @Test(expected = RuntimeException.class)
    public void emptyStringThrowsRuntimeException() {
        classCoercer.coerce("", null);
    }


    @Test(expected = RuntimeException.class)
    public void badClassThrowsRuntimeException() {
        classCoercer.coerce("this.class.does.not.Exist", null);
    }


    @Test
    public void stringReturnsStringClass() {
        Class clas = classCoercer.coerce("java.lang.String", null);
        assertEquals("java.lang.String", clas.getName());
    }


}
