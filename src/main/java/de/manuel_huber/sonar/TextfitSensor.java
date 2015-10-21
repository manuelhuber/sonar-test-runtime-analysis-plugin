package de.manuel_huber.sonar;

import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;

/**
 * Created by Manuel on 21.10.2015.
 */
public class TextfitSensor implements Sensor {

    public void describe(SensorDescriptor descriptor) {

    }


    public void execute(SensorContext context) {
        try {
            String baseURL = context.settings().getString("sonar.core.serverBaseURL");
            de.manuel_huber.sonar.TextfitPlugin.setSonarBaseURL(baseURL);
            context.settings().getProperties().get("sonar.projectVersion"); //Snapshot 1.0
            context.settings().getProperties().get("sonar.moduleKey"); // htmlparser:htmlparser
        } catch (Exception e) {
            System.out.println(e.toString());
            //good luck
        }
    }
}
