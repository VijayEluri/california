package com.tomkp.california.annotations;

import com.tomkp.california.processing.AnnotatedMethodProcessor;
import junit.framework.TestCase;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MethodInvokingTest<A extends Annotation> extends TestCase implements AnnotatedMethodProcessor<A> {


    private static final Logger LOG = Logger.getLogger(MethodInvokingTest.class);


    protected Object instance;
    protected Method method;
    protected A annotation;

    public MethodInvokingTest() {
        super("test");
    }

    @Override
    public void process(Object instance, Method method, A a) {
        LOG.info("process - instance: '" +  instance + "', method: '" + method + "', annotation: '" + annotation + "'");
        this.instance = instance;
        this.method = method;
        this.annotation = a;
    }


    public void test() {
        LOG.info("test - instance: '" +  instance + "', method: '" + method + "', annotation: '" + annotation + "'");
        try {
            if (method != null) {
                LOG.debug("invoke '" + method.getName() + "' on '" + instance + "'");
                method.invoke(instance);
            }
        } catch (Exception e) {
            LOG.warn("Error invoking method", e);
        }
    }


    public boolean isValid() {
        return method != null && instance != null && annotation != null;
    }

}
