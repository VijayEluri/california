package com.tomkp.california;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class SpecificationFinder {

    private static final Logger LOG = Logger.getLogger(SpecificationFinder.class);

    private final String suffix;

    public SpecificationFinder(final String suffix) {
        this.suffix = suffix;
    }


    public List<File> findSpecs(File dir) {
        List<File> files = new ArrayList<File>();
        recurse(dir, files);
        return files;
    }

    public void recurse(File dir, List<File> list) {
        if (LOG.isDebugEnabled()) LOG.debug("search for specs in dir: '" + dir.getAbsolutePath() + "'");
        if (dir.exists()) {
            if (dir.isDirectory()) {
                File[] files = listFiles(dir);
                for (File file : files) {
                    if (file.isDirectory()) {
                        recurse(file, list);
                    } else {
                        list.add(file);
                    }
                }
            } else {
                LOG.warn(dir + " is not a directory");
            }

        } else {
            LOG.warn(dir + " does not exist");
        }
    }


    private File[] listFiles(File dir) {
        return dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                String name = file.getName();
                boolean matched = matched(file, name);
                if (LOG.isDebugEnabled()) LOG.debug("name: '" + name + "' " + matched);
                return matched;
            }
        });
    }

    private boolean matched(File file, String name) {
        return (name.endsWith(suffix) || file.isDirectory())
                && !isSubversionDirectory(name)
                && !name.startsWith("before")
                && !name.startsWith("after");
    }

    private boolean isSubversionDirectory(String name) {
        return ".svn".equals(name);
    }


}
