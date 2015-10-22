package de.manuel_huber.sonar;

import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;

/**
 * Created by Manuel on 21.10.2015.
 */
public class TestRuntimeSensor implements Sensor {

    public void describe(SensorDescriptor descriptor) {

    }


    public void execute(SensorContext context) {
        // The Settings are only availble from the SensorContext but I need them in the Decorator
        // Current workaround: Save them in
        String baseURL = context.settings().getString("sonar.core.serverBaseURL");
        TestRuntimePlugin.setSonarBaseURL(baseURL);
        context.settings().getProperties().get("sonar.projectVersion"); //Snapshot 1.0
        context.settings().getProperties().get("sonar.moduleKey"); // htmlparser:htmlparser
    }
}
