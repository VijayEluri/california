package com.tomkp.california;

import com.tomkp.california.annotations.Fixture;
import com.tomkp.california.tests.MethodInvokingTest;
import junit.framework.TestCase;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Hooks {

    private static final Logger LOG = Logger.getLogger(Hooks.class);


    private AnnotatedMethodLocator annotatedMethodLocator;

    public Hooks(AnnotatedMethodLocator annotatedMethodLocator) {
        this.annotatedMethodLocator = annotatedMethodLocator;
    }

    public <A extends Annotation> List<TestCase> generateTests(Class<A> a) throws Exception {

        List<InstanceMethods> methodsList = annotatedMethodLocator.locate(Fixture.class, a);

        List<TestCase> tests = new ArrayList<TestCase>();

        for (InstanceMethods instanceMethods : methodsList) {
            LOG.info("instance methods: '" + instanceMethods + "'");
            for (Method method : instanceMethods.getMethods()) {
                LOG.info("method: '" + method + "'");
                final A annotation = method.getAnnotation(a);
                Method value = annotation.getClass().getMethod("value");
                Object invoke = value.invoke(annotation);
                String name = invoke.toString();
                TestCase methodInvokingTest = new MethodInvokingTest(name, instanceMethods.getInstance(), method);
                //suite.addTest(methodInvokingTest);
                tests.add(methodInvokingTest);
            }
        }
        return tests;

    }
}
