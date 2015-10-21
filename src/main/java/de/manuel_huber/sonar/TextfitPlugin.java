package de.manuel_huber.sonar;

import org.sonar.api.SonarPlugin;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Manuel on 21.10.2015.
 */
public class TextfitPlugin extends SonarPlugin {

    public static String getSonarBaseURL() {
        return sonarBaseURL;
    }

    public static void setSonarBaseURL(String sonarBaseURL) {
        de.manuel_huber.sonar.TextfitPlugin.sonarBaseURL = sonarBaseURL;
    }

    static String sonarBaseURL;


    public List getExtensions() {
        return Arrays.asList
                (
                        TextfitDecorator.class,
                        TextfitSensor.class
                        ,
                        TextfitRulesDefinition.class
                );
    }
}
