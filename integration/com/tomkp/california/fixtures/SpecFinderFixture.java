package com.tomkp.california.fixtures;

import com.tomkp.california.annotations.Fixture;
import com.tomkp.california.annotations.Step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.File;
import java.io.FilenameFilter;

@Fixture
public class SpecFinderFixture {


    @Step("directory '(.*)' contains (.*) files with suffix '(.*)'")
    public void scansForFiles(String dir, Integer count, String suffix) throws Exception {
        assertThat(countFiles(dir, suffix), equalTo(count));
    }



    private int countFiles(String dir, final String suffix) {
        int fileCount = 0;
        File file = new File(dir);
        if (file.isDirectory()) {
            fileCount = file.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(suffix);
                }
            }).length;
        }
        return fileCount;
    }

}
