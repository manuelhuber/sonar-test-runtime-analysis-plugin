package de.manuel_huber.sonar;

import org.sonar.api.SonarPlugin;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Manuel on 21.10.2015.
 */
public class TestRuntimePlugin extends SonarPlugin {

    public static String getSonarBaseURL() {
        return sonarBaseURL;
    }

    public static void setSonarBaseURL(String sonarBaseURL) {
        TestRuntimePlugin.sonarBaseURL = sonarBaseURL;
    }

    static String sonarBaseURL;


    public List getExtensions() {
        return Arrays.asList
                (
                        TestRuntimeDecorator.class,
                        TestRuntimeSensor.class
                        ,
                        TestRuntimeRulesDefinition.class
                );
    }
}
