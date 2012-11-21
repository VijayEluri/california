package com.tomkp.california;

import com.tomkp.california.methods.InvokableStep;

import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;

public class PatternMapper {

    private Map<Pattern, InvokableStep> patternMapping = new HashMap<Pattern, InvokableStep>();
            
    public void match(Pattern pattern, InvokableStep invokableStep) {
        patternMapping.put(pattern, invokableStep);
    }


}
