package com.tomkp.california;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Feature {


    private File file;
    private String name;

    private List<Scenario> scenarios = new ArrayList<Scenario>();

    public Feature(File file) {
        this.file = file;
        name = file.getName();
        name = "Feature: " + name.replace("_", " ").substring(0, name.lastIndexOf("."));
    }

    public void addScenario(Scenario scenario) {
        scenarios.add(scenario);
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
