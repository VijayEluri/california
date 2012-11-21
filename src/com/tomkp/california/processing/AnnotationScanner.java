package com.tomkp.california.processing;

import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationScanner {

    private static final Logger LOG = Logger.getLogger(AnnotationScanner.class);

    public <A extends Annotation> void scanMethods(Object instance, Class<A> annotationClass, AnnotatedMethodProcessor<A> processor) {
        LOG.info("scan '" + instance.getClass().getSimpleName() + "' for annotation: '" + annotationClass.getSimpleName() + "'");
        Class clas = instance.getClass();
        for (Method method : clas.getMethods()) {
            if (method.isAnnotationPresent(annotationClass)) {
                A annotation = method.getAnnotation(annotationClass);
                processor.process(instance, method, annotation);
            }
        }
    }


}
