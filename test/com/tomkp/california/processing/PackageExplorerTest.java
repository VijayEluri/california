package com.tomkp.california.processing;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PackageExplorerTest {

    private PackageExplorer packageExplorer;

    @Before
    public void setUp() throws Exception {
        packageExplorer = new PackageExplorer();
    }

    @Test
    public void x() throws Exception {
        List<Class> classes = packageExplorer.getClasses("com.tomkp");
        for (Class aClass : classes) {
            System.out.println("" + aClass);
        }
    }

}
