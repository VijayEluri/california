package com.tomkp.california;

import java.io.File;

public class Line {

    private int number;
    private String contents;
    private Scenario scenario;

    public Line(Scenario scenario, int number, String contents) {
        this.scenario = scenario;
        this.number = number;
        this.contents = contents;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public int getNumber() {
        return number;
    }

    public String getContents() {
        return contents;
    }

    public boolean isEmpty() {
        return contents.isEmpty();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("").append(scenario.getFilePath()).append(":").append(number).append(": ");
        sb.append("\"").append(contents).append("\"");
        return sb.toString();
    }
}
