package com.tomkp.california.utils;


import com.tomkp.california.RecursiveFileLoader;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RecursiveFileLoaderTest {


    private final RecursiveFileLoader fileLoader = new RecursiveFileLoader("feature");


    @Test
    public void loadFeatures() {
        List<File> features = fileLoader.findFeatures(new File("testdata/features"));
        assertEquals("test-feature3.feature", features.get(0).getName());
        assertEquals("test-feature1.feature", features.get(1).getName());
        assertEquals("test-feature2.feature", features.get(2).getName());
        assertEquals(3, features.size());
    }

    @Test
    public void loadFeaturesFromNonExistentDirectory() {
        List<File> features = fileLoader.findFeatures(new File("testdata/nonexistent"));
        assertTrue(features.isEmpty());
    }
}
