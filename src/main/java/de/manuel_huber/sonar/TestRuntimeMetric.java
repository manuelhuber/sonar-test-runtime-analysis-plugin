package de.manuel_huber.sonar;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

import java.util.Arrays;
import java.util.List;

/**
 * Created by m.huber on 22/10/2015.
 */
public class TestRuntimeMetric implements Metrics {

    public static final Metric TEST_RUNTIME_SAVE =
            new Metric.Builder(
                    "test_runtime_save",
                    "test runtime save,",
                    Metric.ValueType.DATA)
                    .setDescription("The test runtimes saved as JSon")
                    .setQualitative(false)
                    .setDomain(CoreMetrics.DOMAIN_TESTS)
            .create();

    public List<Metric> getMetrics() {
        return Arrays.<Metric>asList(TEST_RUNTIME_SAVE);
    }
}
