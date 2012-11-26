package com.tomkp.california.fixtures;

import com.tomkp.california.annotations.Fixture;
import com.tomkp.california.annotations.Step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.File;
import java.io.FilenameFilter;

@Fixture
public class SpecFinderFixture {

    private String directoryName;


    @Step("a directory '(.*)'")
    public void scansForFiles(String directoryName) throws Exception {
        this.directoryName = directoryName;
    }


    @Step({
            "it should contain (.*) files with suffix '(.*)'",
            "it should contain (.*) file with suffix '(.*)'"
    })
    public void scansForFiles(Integer count, String suffix) throws Exception {
        assertThat(countFiles(directoryName, suffix), equalTo(count));
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
