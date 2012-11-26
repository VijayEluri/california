package com.tomkp.california.tests;

import com.tomkp.california.Line;
import com.tomkp.california.invocation.InvocationResult;
import com.tomkp.california.invocation.StepInvoker;
import junit.framework.TestCase;
import org.apache.log4j.Logger;

public class LineTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(LineTest.class);

    private Line line;
    private StepInvoker stepInvoker;

    public LineTest(Line line, StepInvoker stepInvoker) {
        super("testMethodName");
        this.line = line;
        this.stepInvoker = stepInvoker;
    }


    public void testMethodName() {
        System.out.println(line);
        if (LOG.isDebugEnabled()) LOG.debug("test line: '" + line + "'");
        InvocationResult invocationResult = stepInvoker.invoke(line);
        if (LOG.isDebugEnabled()) LOG.debug("invocation result: '" + invocationResult + "'");

        if (invocationResult == InvocationResult.INVOKED) {
            LOG.debug("INVOKED: " + line.getContents() + "");
        } else if (invocationResult == InvocationResult.IGNORED) {
            LOG.debug("IGNORED: " + line.getContents() + "");
        } else {
            LOG.debug("TO DO  : " + line.getContents() + "");
            //fail("TO DO  : " + line.getContents() + "");
            //assertTrue(false);
            throw new AssertionError("Missing step: " + line.getContents() + "");
        }

        //assertTrue(invoked);
        assertTrue(true);
    }



    @Override
    public String getName() {
        return "" + line.getContents() + "  (" + line.getScenario().getFilePath() + ":" + line.getNumber() + ")";
    }

}
