package com.tomkp.california.annotations;

import com.tomkp.california.methods.InvokableStep;
import com.tomkp.california.processing.AnnotatedMethodProcessor;
import org.apache.log4j.Logger;

import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Method;

public class StepProcessor implements AnnotatedMethodProcessor<Step> {


    private static final Logger LOG = Logger.getLogger(StepProcessor.class);

    private final Map<Pattern, InvokableStep> steps = new HashMap<Pattern, InvokableStep>();


    @Override
    public void process(Object instance, Method method, Step step) {
        final String[] values = step.value();
        final String[] formats = step.formats();
        for (String value : values) {
            Pattern pattern = Pattern.compile(value);
            InvokableStep invokableStep = new InvokableStep(method, instance, formats);
            if (LOG.isInfoEnabled()) LOG.info("store '" + pattern + "': '" + invokableStep + "'");
            steps.put(pattern, invokableStep);
        }
    }

    public Map<Pattern, InvokableStep> getStepMethods() {
        return steps;
    }

}
