package com.tomkp.california;

import com.tomkp.california.processing.AnnotatedMethodScanner;
import com.tomkp.california.processing.PackageExplorer;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotatedMethodLocatorX {

    private static final Logger LOG = Logger.getLogger(AnnotatedMethodLocatorX.class);

    private AnnotatedMethodScanner scanner;
    private PackageExplorer packageExplorer;
    private String packageName;

    private Map<Class, Object> annotatedClassCache = new HashMap<Class, Object>();

    public AnnotatedMethodLocatorX(AnnotatedMethodScanner scanner,
                                   PackageExplorer packageExplorer,
                                   String packageName) {
        this.scanner = scanner;
        this.packageExplorer = packageExplorer;
        this.packageName = packageName;
    }
    

    public <M extends Annotation, C extends Annotation> List<InstanceMethods> locate(Class<C> classAnnotation, Class<M> methodAnnotation) throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("locate '" + methodAnnotation.getSimpleName() + "' annotations in '" + packageName + "'");
        List<Class> classes = packageExplorer.getClasses(packageName);
        List<InstanceMethods> instanceMethodsList = new ArrayList<InstanceMethods>();
        for (Class aClass : classes) {
            if (aClass.isAnnotationPresent(classAnnotation)) {
                LOG.info("'" + aClass + "' is annotated as a '" + classAnnotation.getSimpleName() + "'");
                if (!annotatedClassCache.containsKey(aClass)) {
                    if (LOG.isInfoEnabled()) LOG.info("create new instance of '" + aClass.getSimpleName() + "'");
                    annotatedClassCache.put(aClass, aClass.newInstance());
                }
                Object instance = annotatedClassCache.get(aClass);
                List<Method> methods = scanner.scanMethods(instance, methodAnnotation);
                InstanceMethods instanceMethods = new InstanceMethods(instance, methods);
                instanceMethodsList.add(instanceMethods);
            }
        }
        return instanceMethodsList;
    }


}
