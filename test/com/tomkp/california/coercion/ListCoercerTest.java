package com.tomkp.california.coercion;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ListCoercerTest {


    private final ListCoercer coercer = new ListCoercer();


    @Test
    public void coerceListWithDefaultFormat() {
        List<String> list = coercer.coerce("jim, bob, jane", null);
        assertEquals("jim", list.get(0));
        assertEquals("bob", list.get(1));
        assertEquals("jane", list.get(2));
        assertEquals(3, list.size());
    }


    @Test
    public void coerceListWithPipeFormat() {
        List<String> list = coercer.coerce("jim|bob|jane", "\\|");
        assertEquals("jim", list.get(0));
        assertEquals("bob", list.get(1));
        assertEquals("jane", list.get(2));
        assertEquals(3, list.size());
    }


    @Test
    public void coerceListWithCommaFormat() {
        List<String> list = coercer.coerce("jim,bob,jane", ",");
        assertEquals("jim", list.get(0));
        assertEquals("bob", list.get(1));
        assertEquals("jane", list.get(2));
        assertEquals(3, list.size());
    }

}
