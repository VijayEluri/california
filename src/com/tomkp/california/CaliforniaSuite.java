package com.tomkp.california;

import com.tomkp.california.annotations.*;
import com.tomkp.california.invocation.StepInvoker;
import com.tomkp.california.methods.InvokableStep;
import com.tomkp.california.processing.AnnotatedMethodScanner;
import com.tomkp.california.processing.PackageExplorer;
import com.tomkp.california.tests.LineTest;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.log4j.Logger;
import org.junit.Assert;

import java.io.File;
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

        FeatureCreator featureCreator = new FeatureCreator();

        Hooks hooks = new Hooks(annotatedMethodLocator);

        try {

            List<TestCase> tests;

            tests = hooks.generateTests(BeforeSuite.class);
            for (TestCase test : tests) {
                testSuite.addTest(test);
            }


            StepInvoker stepInvoker = createStepInvoker(annotatedMethodLocator);

            List<File> specs = specificationFinder.findSpecs(new File(specsPath));

            if (specs.isEmpty()) {
                Assert.fail("no tests found");
            }
            for (File specFile : specs) {
                if (LOG.isDebugEnabled()) LOG.debug("create test for spec: '" + specFile + "'");


                Feature feature = featureCreator.parse(specFile);


                TestSuite featureSuite = new TestSuite();
                featureSuite.setName(feature.getName());

                tests = hooks.generateTests(BeforeScenarios.class);

                for (TestCase test : tests) {
                    featureSuite.addTest(test);
                }

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

                    tests = hooks.generateTests(BeforeScenario.class);
                    for (TestCase test : tests) {
                        featureSuite.addTest(test);
                    }

                    featureSuite.addTest(scenarioSuite);

                    tests = hooks.generateTests(AfterScenario.class);
                    for (TestCase test : tests) {
                        featureSuite.addTest(test);
                    }
                }

                tests = hooks.generateTests(AfterScenarios.class);
                for (TestCase test : tests) {
                    featureSuite.addTest(test);
                }
                testSuite.addTest(featureSuite);
            }
            tests = hooks.generateTests(AfterSuite.class);
            for (TestCase test : tests) {
                testSuite.addTest(test);
            }
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


}


