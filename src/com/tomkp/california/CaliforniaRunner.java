package com.tomkp.california;

import junit.framework.TestSuite;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class CaliforniaRunner extends BlockJUnit4ClassRunner {

    private static final Logger LOG = Logger.getLogger(CaliforniaRunner.class);

   private Class<?> testClass;

    public CaliforniaRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
        this.testClass = testClass;
    }


    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        System.out.println("\n\n* " + splitCamelCase(method.getName()).toLowerCase() + " *\n");

        LOG.info(testClass + "." + method.getName() + "");

        // do stuff here!
        super.runChild(method, notifier);
    }



    private static String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }
}
