package com.tomkp.california;

import com.tomkp.california.annotations.*;
import com.tomkp.california.invocation.StepInvoker;
import com.tomkp.california.methods.InvokableStep;
import com.tomkp.california.processing.AnnotationScanner;
import com.tomkp.california.processing.PackageExplorer;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.log4j.Logger;
import org.junit.Assert;

import java.io.File;
import java.util.Enumeration;
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
                new AnnotationScanner(),
                new PackageExplorer(),
                basePackage);

        SpecificationFinder specificationFinder = new SpecificationFinder(suffix);

        try {

            MethodInvokingTest beforeSuite = beforeSuite(annotatedMethodLocator);
            if (beforeSuite.isValid()) {
                testSuite.addTest(beforeSuite);
            }

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


                MethodInvokingTest beforeScenarios = beforeScenarios(annotatedMethodLocator);
                if (beforeScenarios.isValid()) {
                    featureSuite.addTest(beforeScenarios);
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

                    MethodInvokingTest beforeScenario = beforeScenario(annotatedMethodLocator);
                    if (beforeScenario.isValid()) {
                        featureSuite.addTest(beforeScenario);
                    }

                    featureSuite.addTest(scenarioSuite);

                    MethodInvokingTest afterScenario = afterScenario(annotatedMethodLocator);
                    if (afterScenario.isValid()) {
                        featureSuite.addTest(afterScenario);
                    }

                }
                MethodInvokingTest afterScenarios = afterScenarios(annotatedMethodLocator);
                if (afterScenarios.isValid()) {
                    featureSuite.addTest(afterScenarios);
                }

                testSuite.addTest(featureSuite);
            }

            MethodInvokingTest afterSuite = afterSuite(annotatedMethodLocator);
            if (afterSuite.isValid()) {
                testSuite.addTest(afterSuite);
            }

        } catch (Exception e) {
            LOG.warn("error running tests", e);
        }


        System.out.println();
        System.out.println();
        recurse(testSuite);
        System.out.println();
        System.out.println();

        return testSuite;
    }


    private static StepInvoker createStepInvoker(AnnotatedMethodLocator annotatedMethodLocator) throws Exception {
        StepProcessor stepProcessor = new StepProcessor();

        annotatedMethodLocator.locate(Fixture.class, Step.class, stepProcessor);

        Map<Pattern, InvokableStep> stepMethodMap = stepProcessor.getStepMethods();

        return new StepInvoker(stepMethodMap);
    }


    private static void recurse(TestSuite suite) {
        LOG.info("suite: '" + suite + "'");

        Enumeration<Test> tests = suite.tests();
        while (tests.hasMoreElements()) {
            Test test = tests.nextElement();

            if (test instanceof TestSuite) {
                recurse((TestSuite) test);
            } else {
                LOG.info("test:  '" + test + "'");
            }
        }

    }


    private static MethodInvokingTest afterScenario(AnnotatedMethodLocator annotatedMethodLocator) throws Exception {
        MethodInvokingTest<AfterScenario> afterTest = new MethodInvokingTest<AfterScenario>() {
            @Override
            public String getName() {
                if (annotation == null) {
                    return "after scenario";
                }
                return annotation.value();
            }
        };
        annotatedMethodLocator.locate(Fixture.class, AfterScenario.class, afterTest);
        return afterTest;
    }


    private static MethodInvokingTest beforeScenario(AnnotatedMethodLocator annotatedMethodLocator) throws Exception {
        MethodInvokingTest<BeforeScenario> beforeTest = new MethodInvokingTest<BeforeScenario>() {
            @Override
            public String getName() {
                if (annotation == null) {
                    return "before scenario";
                }
                return annotation.value();
            }
        };
        annotatedMethodLocator.locate(Fixture.class, BeforeScenario.class, beforeTest);
        return beforeTest;
    }


    private static MethodInvokingTest afterScenarios(AnnotatedMethodLocator annotatedMethodLocator) throws Exception {
        MethodInvokingTest<AfterScenarios> afterTest = new MethodInvokingTest<AfterScenarios>() {
            @Override
            public String getName() {
                if (annotation == null) {
                    return "after scenarios";
                }
                return annotation.value();
            }
        };
        annotatedMethodLocator.locate(Fixture.class, AfterScenarios.class, afterTest);
        return afterTest;
    }


    private static MethodInvokingTest beforeScenarios(AnnotatedMethodLocator annotatedMethodLocator) throws Exception {
        MethodInvokingTest<BeforeScenarios> beforeTest = new MethodInvokingTest<BeforeScenarios>() {
            @Override
            public String getName() {
                if (annotation == null) {
                    return "before scenarios";
                }
                return annotation.value();
            }
        };
        annotatedMethodLocator.locate(Fixture.class, BeforeScenarios.class, beforeTest);
        return beforeTest;
    }


    private static MethodInvokingTest afterSuite(AnnotatedMethodLocator annotatedMethodLocator) throws Exception {
        MethodInvokingTest<AfterSuite> afterTest = new MethodInvokingTest<AfterSuite>() {
            @Override
            public String getName() {
                if (annotation == null) {
                    return "after suite";
                }
                return annotation.value();
            }
        };
        annotatedMethodLocator.locate(Fixture.class, AfterSuite.class, afterTest);
        return afterTest;
    }


    private static MethodInvokingTest beforeSuite(AnnotatedMethodLocator annotatedMethodLocator) throws Exception {
        MethodInvokingTest<BeforeSuite> beforeTest = new MethodInvokingTest<BeforeSuite>() {
            @Override
            public String getName() {
                if (annotation == null) {
                    return "before suite";
                }
                return annotation.value();
            }
        };
        annotatedMethodLocator.locate(Fixture.class, BeforeSuite.class, beforeTest);
        return beforeTest;
    }


}