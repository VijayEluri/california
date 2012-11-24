package com.tomkp.california.annotations;

import com.tomkp.california.processing.AnnotatedMethodProcessor;
import junit.framework.TestCase;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MethodInvokingXTest extends TestCase {


    private static final Logger LOG = Logger.getLogger(MethodInvokingXTest.class);


    protected Object instance;
    protected Method method;
    private String name;


    public MethodInvokingXTest(String name, Object instance, Method method) {
        super("test");
        this.name = name;
        this.instance = instance;
        this.method = method;
    }



    public void test() {
        LOG.info("test - instance: '" +  instance + "', method: '" + method + "'");
        try {
            if (method != null) {
                LOG.debug("invoke '" + method.getName() + "' on '" + instance + "'");
                method.invoke(instance);
            }
        } catch (Exception e) {
            LOG.warn("Error invoking method", e);
        }
    }


    @Override
    public String getName() {
        return name;
    }
}
