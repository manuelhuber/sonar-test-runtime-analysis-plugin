import de.manuel_huber.sonar.TestRuntimeDecorator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by Manuel on 21.10.2015.
 */
public class TestRuntimeDecoratorTest {

    TestRuntimeDecorator testRuntimeDecorator;

    @Before
    public void buildUp(){
        testRuntimeDecorator = new TestRuntimeDecorator();
    }

    @Test
    public void testCompareTestExecutionTimes() {
        assertThat(testRuntimeDecorator.calculatePercentileIncrease(20, 30), equalTo((float)50));
        assertThat(testRuntimeDecorator.calculatePercentileIncrease(200, 37893), equalTo((float)18846.5));
        assertThat(testRuntimeDecorator.calculatePercentileIncrease(1, 50), equalTo((float) 4900));
        assertThat(testRuntimeDecorator.calculatePercentileIncrease(0, 30), equalTo((float) 3000));
        float result = ((float)2/(float)7)*(float)100;
        assertThat(testRuntimeDecorator.calculatePercentileIncrease(70, 90), equalTo(result));
        assertThat(testRuntimeDecorator.calculatePercentileIncrease(30, 30), equalTo((float)0));
        assertThat(testRuntimeDecorator.calculatePercentileIncrease(0, 0), equalTo((float)0));
        assertThat(testRuntimeDecorator.calculatePercentileIncrease(500, 350), equalTo((float)0));
        assertThat(testRuntimeDecorator.calculatePercentileIncrease(50, 0), equalTo((float)0));
    }

//    @Test
//    public void testTestPlanToTestFileModel(){
//        testRuntimeDecorator.testPlanToTestFileModel()
//    }
}
