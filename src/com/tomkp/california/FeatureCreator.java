package com.tomkp.california;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FeatureCreator {

    private static final Logger LOG = Logger.getLogger(FeatureCreator.class);

    private File file;


    public FeatureCreator(File file) {
        this.file = file;
    }



    public Feature createFeature() {
        Feature feature = new Feature(file);

        if (LOG.isInfoEnabled()) LOG.info("create feature from file: '" + file.getName() + "'");
        if (file != null) {
            try {
                if (LOG.isDebugEnabled()) LOG.debug("process file: '" + file + "'");

                String contents = FileUtils.readFileToString(file);
                List<String> lines = Arrays.asList(contents.split("\n"));

                int lineNumber = 0;
                
                
                Scenario scenario = null;
                for (String line : lines) {

                    lineNumber++;
                    line = line.trim();
                    if (line.contains("Feature:")) {
                        feature.setName(line);
                    }

                    if (line.contains("Scenario:")) {
                        scenario = new Scenario(file, line);
                        feature.addScenario(scenario);
                    }
                    else if (scenario != null) {
                        scenario.addLine(new Line(file, lineNumber, line));
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException("error reading file '" + file + "'");
            }
        }
        //return runners;
        return feature;
    }
    
    


}
