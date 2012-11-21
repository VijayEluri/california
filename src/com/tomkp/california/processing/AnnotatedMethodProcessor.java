package com.tomkp.california.processing;

import java.lang.reflect.Method;
import java.lang.annotation.Annotation;

public interface AnnotatedMethodProcessor<A extends Annotation>  {

    void process(Object instance, Method method, A a);
    

}
