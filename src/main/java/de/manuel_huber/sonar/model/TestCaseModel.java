package de.manuel_huber.sonar.model;

/**
 * Created by Manuel on 21.10.2015.
 */
public class TestCaseModel {

    String name;
    int durationInMs;


    public TestCaseModel() {
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getDurationInMs() {
        return durationInMs;
    }


    public void setDurationInMs(int durationInMs) {
        this.durationInMs = durationInMs;
    }

    public void setDurationInMs(long durationInMs) {
        this.durationInMs = (int) durationInMs;
    }


}
