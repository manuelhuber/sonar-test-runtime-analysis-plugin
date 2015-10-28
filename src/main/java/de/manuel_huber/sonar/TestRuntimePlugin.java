package de.manuel_huber.sonar;

import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.PropertyType;
import org.sonar.api.SonarPlugin;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Manuel on 21.10.2015.
 */

@Properties({
        @Property(
                key = "test1",
                defaultValue = "",
                name = "Path to TSLint",
                description = "Path to installed Node TSLint",
                project = false,
                global = true
        ),
        @Property(
                key = "test2",
                defaultValue = "",
                name = "TSLint Config file",
                description = "Path to TSLint Config File",
                project = true,
                global = false
        ),
        @Property(
                key = "test3",
                type = PropertyType.BOOLEAN,
                defaultValue = "true",
                name = "Exclude .d.ts files",
                description = ".d.ts TESTING",
                project = true,
                global = false
        ),
        @Property(
                key ="test4",
                type = PropertyType.STRING,
                name = "LCOV report path",
                description = "LCOV report path",
                project = true,
                global = false
        ),
        @Property(
                key = "test5",
                defaultValue = "false",
                type = PropertyType.BOOLEAN,
                name = "Force 0 coverage",
                description = "Force coverage to be set to 0 when no report is provided.",
                project = true,
                global = false
        )
})
public class TestRuntimePlugin extends SonarPlugin {

    private static String projectVersion;

    public static String getProjectVersion() {
        return projectVersion;
    }

    public static void setProjectVersion(String projectVersion) {
        TestRuntimePlugin.projectVersion = projectVersion;
    }

    public List getExtensions() {
        return Arrays.asList
                (
                        TestRuntimeDecorator.class,
                        TestRuntimeSensor.class,
                        TestRuntimeRulesDefinition.class,
                        TestRuntimeMetric.class
                );
    }
}
