package de.manuel_huber.sonar;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.manuel_huber.sonar.model.TestCaseModel;
import de.manuel_huber.sonar.model.TestFileModel;
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.issue.Issuable;
import org.sonar.api.issue.Issue;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Qualifiers;
import org.sonar.api.resources.Resource;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.test.MutableTestPlan;
import org.sonar.api.test.TestCase;
import org.sonar.api.test.TestPlan;

/**
 * Created by Manuel on 21.10.2015.
 */
public class TestRuntimeDecorator implements Decorator {

    private final ResourcePerspectives resourcePerspectives;
    String baseURL;


    public TestRuntimeDecorator(ResourcePerspectives resourcePerspectives) {
        this.resourcePerspectives = resourcePerspectives;
    }


    public TestRuntimeDecorator() {
        this.resourcePerspectives = null;
    }


    public void decorate(Resource resource, DecoratorContext context) {
        if (resource.getQualifier().equals(Qualifiers.UNIT_TEST_FILE)) {
            try {
                checkTestCases(resource, context);
            } catch (Exception e) {
                //good luck
                System.out.println(e.toString());
            }
        }
    }


    public boolean shouldExecuteOnProject(Project project) {
        return true;
    }


    public TestFileModel getTestFileModel(Resource resource) throws UnirestException {
        baseURL = TestRuntimePlugin.getSonarBaseURL();
        HttpResponse<JsonNode> getResponse = Unirest.get(baseURL + "/api/tests/show")
                .field("key", resource.getEffectiveKey()).asJson();
        Gson gson = new Gson();
        JsonNode body = getResponse.getBody();
        return gson.fromJson(String.valueOf(body), TestFileModel.class);
    }


    public void checkTestCases(Resource resource, DecoratorContext context) throws UnirestException {
        System.out.println("----------------------Decorator----------------------");

        TestCaseModel[] previousTestCasesArray = getTestFileModel(resource).getTests();
        TestPlan currentTestPlan = resourcePerspectives.as(MutableTestPlan.class, resource);

        for (TestCaseModel previousTestCase : previousTestCasesArray) {
            Iterable<TestCase> currentTestCaseIterable = currentTestPlan.testCasesByName(previousTestCase.getName());
            //this Iterable should only contain 1 item
            for (TestCase currentTestCase : currentTestCaseIterable) {
                long old = previousTestCase.getDurationInMs();
                long current = currentTestCase.durationInMs();
                float percentileIncrease = calculatePercentileIncrease(old, current);
                if (percentileIncrease >= 50) {
                    Issuable issuable = resourcePerspectives.as(Issuable.class, resource);
                    if (issuable != null) {
                        Issue issue = issuable.newIssueBuilder()
                                .ruleKey(RuleKey.of(TestRuntimeRulesDefinition.REPOSITORY_KEY, TestRuntimeRulesDefinition.INCREASE50))
                                .attribute("File", resource.getPath())
                                .attribute("TestCase", currentTestCase.name())
                                .build();
                        issuable.addIssue(issue);
                    }
                }
            }
        }
        System.out.println("----------------------Decorator----------------------");
    }


    public float calculatePercentileIncrease(long old, long current) {
        float percentileIncrease = 0;
        if (current > old) {
            long dif = current - old;
            float divider;
            if (old < 1) {
                divider = 1;
            } else {
                divider = (float) old;
            }
            percentileIncrease = ((float) dif / divider) * 100;
        }
        return percentileIncrease;
    }
}
