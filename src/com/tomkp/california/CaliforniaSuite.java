package com.tomkp.california;

import com.tomkp.california.annotations.*;
import com.tomkp.california.invocation.StepInvoker;
import com.tomkp.california.methods.InvokableStep;
import com.tomkp.california.processing.AnnotatedMethodScanner;
import com.tomkp.california.processing.PackageExplorer;
import junit.framework.TestSuite;
import org.apache.log4j.Logger;
import org.junit.Assert;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public abstract class CaliforniaSuite extends TestSuite {


    private static final Logger LOG = Logger.getLogger(CaliforniaSuite.class);


    protected static TestSuite generateSuite(String suiteName, String basePackage, String suffix, final String specsPath) {

        if (LOG.isInfoEnabled())
            LOG.info("generate test master suite '" + suiteName + "', base package '" + basePackage + "', specifications with suffix '" + suffix + "'");

        TestSuite testSuite = new TestSuite();

        AnnotatedMethodLocator annotatedMethodLocator = new AnnotatedMethodLocator(
                new AnnotatedMethodScanner(),
                new PackageExplorer(),
                basePackage);

        SpecificationFinder specificationFinder = new SpecificationFinder(suffix);

        try {

            addTests(testSuite, annotatedMethodLocator, BeforeSuite.class);


            StepInvoker stepInvoker = createStepInvoker(annotatedMethodLocator);

            List<File> specs = specificationFinder.findSpecs(new File(specsPath));

            if (specs.isEmpty()) {
                Assert.fail("no tests found");
            }
            for (File specFile : specs) {
                if (LOG.isDebugEnabled()) LOG.debug("create test for spec: '" + specFile + "'");

                FeatureCreator featureCreator = new FeatureCreator(specFile);
                Feature feature = featureCreator.createFeature();


                TestSuite featureSuite = new TestSuite();
                featureSuite.setName(feature.getName());

                addTests(featureSuite, annotatedMethodLocator, BeforeScenarios.class);

                for (Scenario scenario : feature.getScenarios()) {

                    TestSuite scenarioSuite = new TestSuite();
                    scenarioSuite.setName(scenario.getName());

                    List<Line> lines = scenario.getLines();

                    LOG.info(scenario.getName() + ": '" + lines + "'");

                    for (Line line : lines) {

                        if (LOG.isInfoEnabled()) LOG.info("line: '" + line + "'");

                        if (!line.isEmpty()) {
                            LineTest lineTest = new LineTest(line, stepInvoker);
                            scenarioSuite.addTest(lineTest);
                        }
                    }

                    addTests(featureSuite, annotatedMethodLocator, BeforeScenario.class);

                    featureSuite.addTest(scenarioSuite);

                    addTests(featureSuite, annotatedMethodLocator, AfterScenario.class);
                }

                addTests(featureSuite, annotatedMethodLocator, AfterScenarios.class);

                testSuite.addTest(featureSuite);
            }
            addTests(testSuite, annotatedMethodLocator, AfterSuite.class);

        } catch (Exception e) {
            LOG.warn("error running tests", e);
        }


        return testSuite;
    }


    private static StepInvoker createStepInvoker(AnnotatedMethodLocator annotatedMethodLocator) throws Exception {

        List<InstanceMethods> instanceMethodsList = annotatedMethodLocator.locate(Fixture.class, Step.class);

        Map<Pattern, InvokableStep> steps = new HashMap<Pattern, InvokableStep>();

        for (InstanceMethods instanceMethods : instanceMethodsList) {
            for (Method method : instanceMethods.getMethods()) {

                Object instance = instanceMethods.getInstance();

                Step step = method.getAnnotation(Step.class);

                final String[] values = step.value();
                final String[] formats = step.formats();
                for (String value : values) {
                    Pattern pattern = Pattern.compile(value);
                    InvokableStep invokableStep = new InvokableStep(method, instance, formats);
                    if (LOG.isInfoEnabled()) LOG.info("store '" + pattern + "': '" + invokableStep + "'");
                    steps.put(pattern, invokableStep);
                }
            }
        }


        return new StepInvoker(steps);
    }



    private static <A extends Annotation> void addTests(TestSuite suite, AnnotatedMethodLocator annotatedMethodLocator, Class<A> a) throws Exception {

        List<InstanceMethods> methodsList = annotatedMethodLocator.locate(Fixture.class, a);

        for (InstanceMethods instanceMethods : methodsList) {
            LOG.info("instance methods: '" + instanceMethods + "'");
            for (Method method : instanceMethods.getMethods()) {
                LOG.info("method: '" + method + "'");
                final A annotation = method.getAnnotation(a);
                Method value = annotation.getClass().getMethod("value");
                Object invoke = value.invoke(annotation);
                String name = invoke.toString();
                MethodInvokingTest methodInvokingTest = new MethodInvokingTest(name, instanceMethods.getInstance(), method);
                suite.addTest(methodInvokingTest);
            }
        }

    }


}


