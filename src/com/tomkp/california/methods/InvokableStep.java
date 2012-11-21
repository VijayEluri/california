package com.tomkp.california.methods;

import com.tomkp.california.coercion.TypeCoercion;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InvokableStep {

    private static final Logger LOG = Logger.getLogger(InvokableStep.class);

    private Method method;
    private Object instance;
    private String[] formats;


    public InvokableStep(Method method, Object instance, String[] formats) {
        this.method = method;
        this.instance = instance;
        this.formats = formats;
    }


    public boolean invoke(List<String> params) {

        Class<?>[] classes = method.getParameterTypes();
        List<String> subList = params.subList(1, params.size());
        List<Object> paramList = new ArrayList<Object>();

        for (int i = 0; i < classes.length; i++) {

            Class<?> aClass = classes[i];
            String param = subList.get(i);

            String format = null;

            if (i < formats.length) {
                format = formats[i];
                if (LOG.isInfoEnabled()) LOG.info("format: '" + format + "'");
            }
            try {
                Object coerced = TypeCoercion.coerce(aClass, param, format);
                //LOG.info("coerced: '" + coerced + "' to a  '" + coerced.getClass().getSimpleName() + "'");
                paramList.add(coerced);
            } catch (Exception e) {
                LOG.warn("error coercing parameter '" + param + "'", e);
                throw new AssertionError("error coercing parameter '" + param + "'");
            }
        }

        boolean result = false;
        try {
            LOG.debug("invoke '" + method.getDeclaringClass().getSimpleName() + "."  + method.getName() + "' with parameters: '" + paramList + "'");
            if (LOG.isDebugEnabled()) LOG.debug("instance: '" + instance + "'");
            if (classes.length == 0) {
                method.invoke(instance);
            } else {
                method.invoke(instance, paramList.toArray());
            }
            result = true;
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof AssertionError) {
                AssertionError assertionError = (AssertionError) e.getTargetException();
                if (LOG.isDebugEnabled()) LOG.debug("assertion error: '" + assertionError + "'");
                throw assertionError;
            } else {
                LOG.warn("unable to invoke method " + method + " on " + instance, e);
            }
            AssertionError assertionError = new AssertionError("unable to invoke method " + method + " on " + instance);
            if (LOG.isInfoEnabled()) LOG.info("assertion error: '" + assertionError + "'");
            throw assertionError;
            // todo
            //break;
        } catch (IllegalAccessException e) {
            LOG.warn("error accessing method " + method + " on " + instance, e);
        }
        return result;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("InvokableStep");
        sb.append("{method=").append(method.getName());
        sb.append(", instance=").append(instance.getClass().getName());
        sb.append(", formats='").append(Arrays.asList(formats)).append('\'');
        sb.append('}');
        return sb.toString();
    }
}