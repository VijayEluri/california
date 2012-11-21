package com.tomkp.california.invocation;

import com.tomkp.california.Line;
import com.tomkp.california.methods.InvokableStep;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StepInvoker {

    private static final Logger LOG = Logger.getLogger(StepInvoker.class);

    private static final String CUCUMBER_SYNTAX = "^(Given.*|And.*|Then.*|When.*|But.*)";
    private static final String CUCUMBER_TRIMMING_SYNTAX = "^(Given |And |Then |When |But )";


    private Map<Pattern, InvokableStep> steps;

    public StepInvoker(Map<Pattern, InvokableStep> steps) {
        this.steps = steps;
    }

    public InvocationResult invoke(Line line) {
        if (LOG.isDebugEnabled()) LOG.debug("invoke line: '" + line + "'");

        InvocationResult invocationResult = InvocationResult.NOT_INVOKED;

        List<String> params = new ArrayList<String>();
        String step = line.getContents().trim();
        
        if (validateStep(step)) {

            step = step.replaceAll(CUCUMBER_TRIMMING_SYNTAX, "");
            
            if (LOG.isDebugEnabled()) LOG.debug("process step '" + step + "'");

            for (Map.Entry<Pattern, InvokableStep> entry : steps.entrySet()) {

                Pattern pattern = entry.getKey();
                //if (LOG.isInfoEnabled()) LOG.info("pattern: '" + pattern.toString() + "'");

                Matcher matcher = pattern.matcher(step);
                if (matcher.matches()) {

                    if (LOG.isDebugEnabled()) LOG.debug("matched against '" + pattern + "'");

                    InvokableStep invokableStep = entry.getValue();

                    int count = matcher.groupCount();
                    for (int i = 0; i <= count; i++) {
                        String group = matcher.group(i);
                        params.add(group);
                    }

                    boolean invoked = invokableStep.invoke(params);
                    if (invoked) {
                        invocationResult = InvocationResult.INVOKED;
                    }

                }
            }
        } else {
            if (LOG.isInfoEnabled()) LOG.info("comment: '" + step + "'");
            invocationResult = InvocationResult.IGNORED;
        }
        return invocationResult;
    }




    private boolean validateStep(String step) {
        return step.length() > 0 && step.matches(CUCUMBER_SYNTAX);
    }



}
