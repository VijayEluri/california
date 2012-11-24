package com.tomkp.california.processing;

import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotatedMethodScanner {

    private static final Logger LOG = Logger.getLogger(AnnotatedMethodScanner.class);

    public <A extends Annotation> List<Method> scanMethods(Object instance, Class<A> annotationClass) {
        LOG.info("scan '" + instance.getClass().getSimpleName() + "' for annotation: '" + annotationClass.getSimpleName() + "'");
        List<Method> methods = new ArrayList<Method>();
        Class clas = instance.getClass();
        for (Method method : clas.getMethods()) {
            if (method.isAnnotationPresent(annotationClass)) {
                methods.add(method);
            }
        }
        return methods;
    }


}
