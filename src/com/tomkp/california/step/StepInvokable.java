package com.tomkp.california.step;


import com.tomkp.california.Line;
import com.tomkp.california.annotations.Step;
import com.tomkp.california.processing.AnnotatedMethod;

import java.util.List;

public class StepInvokable {

    private AnnotatedMethod<Step> annotatedMethod;
    private List<String> methodParameters;
    private Line line;

    public StepInvokable(AnnotatedMethod<Step> annotatedMethod, List<String> methodParameters, Line line) {
        this.annotatedMethod = annotatedMethod;
        this.methodParameters = methodParameters;
        this.line = line;
    }

    public AnnotatedMethod<Step> getAnnotatedMethod() {
        return annotatedMethod;
    }

    public List<String> getMethodParameters() {
        return methodParameters;
    }

    public Line getLine() {
        return line;
    }
}
