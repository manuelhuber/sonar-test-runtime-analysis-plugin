package de.manuel_huber.sonar.model;

/**
 * Created by Manuel on 21.10.2015.
 */
public class TestFileModel {

    TestCaseModel[] tests;


    public TestFileModel() {
    }


    public TestCaseModel[] getTests() {
        return tests;
    }


    public void setTests(TestCaseModel[] tests) {
        this.tests = tests;
    }
}
