package com.tomkp.california.processing;

import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AnnotatedMethodScannerTest {


    private AnnotatedMethodScanner annotatedMethodScanner;

    @Before
    public void setUp() throws Exception {
        annotatedMethodScanner = new AnnotatedMethodScanner();
    }


    @Test
    public void scanForZeroAnnotatedMethods() {
        List<Method> methods = annotatedMethodScanner.scanMethods(new ZeroAnnotatedMethods(), MethodAnnotation.class);
        assertEquals(0, methods.size());
    }


    @Test
    public void scanForOneAnnotatedMethod() {
        List<Method> methods = annotatedMethodScanner.scanMethods(new OneAnnotatedMethod(), MethodAnnotation.class);
        assertEquals(1, methods.size());
        assertEquals("x", methods.get(0).getName());
    }


    @Test
    public void scanForTwoAnnotatedMethods() {
        List<Method> methods = annotatedMethodScanner.scanMethods(new TwoAnnotatedMethods(), MethodAnnotation.class);
        assertEquals(2, methods.size());
        assertEquals("x", methods.get(0).getName());
        assertEquals("y", methods.get(1).getName());
    }


    /// test annotations and classes

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public @interface MethodAnnotation {
    }

    public class ZeroAnnotatedMethods {
    }

    public class OneAnnotatedMethod {
        @MethodAnnotation
        public void x() {
        }
    }

    public class TwoAnnotatedMethods {
        @MethodAnnotation
        public void x() {
        }
        @MethodAnnotation
        public void y() {
        }
    }
}
