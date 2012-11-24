package com.tomkp.california;

import java.lang.reflect.Method;
import java.util.List;

public class InstanceMethods {

    private Object instance;
    private List<Method> methods;

    public InstanceMethods(Object instance, List<Method> methods) {
        this.instance = instance;
        this.methods = methods;
    }


    public Object getInstance() {
        return instance;
    }

    public List<Method> getMethods() {
        return methods;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("InstanceMethods");
        sb.append("{instance=").append(instance);
        sb.append(", methods=").append(methods);
        sb.append('}');
        return sb.toString();
    }
}
