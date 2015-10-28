package de.manuel_huber.sonar.model;

/**
 * Created by Manuel on 21.10.2015.
 */
public class TestFileModel {

    String version;

    TestCaseModel[] tests;

    public TestFileModel() {
    }

    public TestFileModel(TestCaseModel[] testCaseModels){
        this.tests = testCaseModels;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public TestCaseModel[] getTests() {
        return tests;
    }


    public void setTests(TestCaseModel[] tests) {
        this.tests = tests;
    }
}
