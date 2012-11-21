package com.tomkp.california;

import com.tomkp.california.processing.AnnotatedMethodProcessor;
import com.tomkp.california.processing.AnnotationScanner;
import com.tomkp.california.processing.PackageExplorer;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotatedMethodLocator {

    private static final Logger LOG = Logger.getLogger(AnnotatedMethodLocator.class);

    private AnnotationScanner scanner;
    private PackageExplorer packageExplorer;
    private String packageName;

    private Map<Class, Object> annotatedClassCache = new HashMap<Class, Object>();
    
    public AnnotatedMethodLocator(AnnotationScanner scanner,
                                  PackageExplorer packageExplorer,
                                  String packageName) {
        this.scanner = scanner;
        this.packageExplorer = packageExplorer;
        this.packageName = packageName;
    }
    

    public <M extends Annotation, C extends Annotation> void locate(Class<C> classAnnotation, Class<M> methodAnnotation, AnnotatedMethodProcessor<M> annotationProcessor) throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("locate '" + methodAnnotation.getSimpleName() + "' annotations in '" + packageName + "'");
        List<Class> classes = packageExplorer.getClasses(packageName);
        for (Class aClass : classes) {
            if (aClass.isAnnotationPresent(classAnnotation)) {
                LOG.info("'" + aClass + "' is annotated as a '" + classAnnotation.getSimpleName() + "'");
                if (!annotatedClassCache.containsKey(aClass)) {
                    if (LOG.isInfoEnabled()) LOG.info("create new instance of '" + aClass.getSimpleName() + "'");
                    annotatedClassCache.put(aClass, aClass.newInstance());
                }
                Object instance = annotatedClassCache.get(aClass);
                scanner.scanMethods(instance, methodAnnotation, annotationProcessor);
            }
        }
    }


}
