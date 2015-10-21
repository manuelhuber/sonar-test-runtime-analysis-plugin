import de.manuel_huber.sonar.TextfitDecorator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by Manuel on 21.10.2015.
 */
public class TextfitDecoratorTest {

    TextfitDecorator textfitDecorator;

    @Before
    public void buildUp(){
        textfitDecorator = new TextfitDecorator();
    }

    @Test
    public void testCompareTestExecutionTimes() {
        assertThat(textfitDecorator.calculatePercentileIncrease(20, 30), equalTo((float)50));
        assertThat(textfitDecorator.calculatePercentileIncrease(200, 37893), equalTo((float)18846.5));
        assertThat(textfitDecorator.calculatePercentileIncrease(1, 50), equalTo((float) 4900));
        assertThat(textfitDecorator.calculatePercentileIncrease(0, 30), equalTo((float) 3000));
        float result = ((float)2/(float)7)*(float)100;
        assertThat(textfitDecorator.calculatePercentileIncrease(70, 90), equalTo(result));
        assertThat(textfitDecorator.calculatePercentileIncrease(30, 30), equalTo((float)0));
        assertThat(textfitDecorator.calculatePercentileIncrease(0, 0), equalTo((float)0));
        assertThat(textfitDecorator.calculatePercentileIncrease(500, 350), equalTo((float)0));
        assertThat(textfitDecorator.calculatePercentileIncrease(50, 0), equalTo((float)0));
    }
}
