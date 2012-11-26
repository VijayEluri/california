package com.tomkp.california.coercion;

import java.util.ArrayList;
import java.util.List;

public class ParametersConverter {


    public List<Object> convertParameters(Class[] parameterTypes, String[] formats, List<String> parameters) {

        List<Object> convertedParameters = new ArrayList<Object>();

        for (int i = 0; i < parameterTypes.length; i++) {

            Class clas = parameterTypes[i];

            String param = parameters.get(i);
            String format = null;

            if (i < formats.length) {
                format = formats[i];
                //LOG.info("format: '{}'", format);
            }

            try {
                Object coerced = TypeCoercion.coerce(clas, param, format);
                //LOG.info("coerced: '{}' to a  '{}'", coerced, coerced.getClass().getSimpleName());
                convertedParameters.add(coerced);
            } catch (Exception e) {
                throw new RuntimeException("error coercing parameter '" + param + "'");
            }
        }

        return convertedParameters;
    }
}
