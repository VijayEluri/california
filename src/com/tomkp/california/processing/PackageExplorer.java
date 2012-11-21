package com.tomkp.california.processing;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class PackageExplorer {

    private static final Logger LOG = Logger.getLogger(PackageExplorer.class);


    public List<Class> getClasses(String packageName) throws ClassNotFoundException, IOException {
        if (LOG.isInfoEnabled()) LOG.info("get classes in package '" + packageName + "'");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }


    private List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        if (LOG.isDebugEnabled()) LOG.debug("scan directory '" + directory + "'");
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = className(packageName, file);
                Class<?> clas = Class.forName(className);
                classes.add(clas);
            }
        }
        return classes;
    }

    private String className(String packageName, File file) {
        return packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
    }

}
