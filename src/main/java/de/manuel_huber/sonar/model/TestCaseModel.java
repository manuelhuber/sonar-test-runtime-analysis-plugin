package de.manuel_huber.sonar.model;

/**
 * Created by Manuel on 21.10.2015.
 */
public class TestCaseModel {

    String name;
    String status;
    int durationInMs;
    int coveredLines;


    public TestCaseModel() {
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public int getDurationInMs() {
        return durationInMs;
    }


    public void setDurationInMs(int durationInMs) {
        this.durationInMs = durationInMs;
    }


    public int getCoveredLines() {
        return coveredLines;
    }


    public void setCoveredLines(int coveredLines) {
        this.coveredLines = coveredLines;
    }
}
