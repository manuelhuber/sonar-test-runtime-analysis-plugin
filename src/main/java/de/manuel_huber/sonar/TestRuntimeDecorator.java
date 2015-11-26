package de.manuel_huber.sonar;

import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.manuel_huber.sonar.model.TestCaseModel;
import de.manuel_huber.sonar.model.TestFileModel;
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.config.Settings;
import org.sonar.api.issue.Issuable;
import org.sonar.api.issue.Issue;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Qualifiers;
import org.sonar.api.resources.Resource;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.test.MutableTestCase;
import org.sonar.api.test.MutableTestPlan;
import org.sonar.api.test.TestCase;
import org.sonar.api.test.TestPlan;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manuel on 21.10.2015.
 */
public class TestRuntimeDecorator implements Decorator {

    private final ResourcePerspectives resourcePerspectives;
    private final Settings settings;
    private DecoratorContext context;
    private Resource resource;
    private String projectVersion;

    public TestRuntimeDecorator(ResourcePerspectives resourcePerspectives, Settings settings) {
        this.resourcePerspectives = resourcePerspectives;
        this.settings = settings;
    }

    // Needed to run tests
    public TestRuntimeDecorator() {
        this.resourcePerspectives = null;
        this.settings = null;
    }


    public boolean shouldExecuteOnProject(Project project) {
        return true;
    }


    public void decorate(Resource resource, DecoratorContext context) {
        if (resource.getQualifier().equals(Qualifiers.UNIT_TEST_FILE)) {
            System.out.println("----------------------Decorator----------------------");
            this.context = context;
            this.resource = resource;
            projectVersion = settings.getProperties().get("sonar.projectVersion");
            try {
                TestCaseModel[] previousTestCasesArray = getTestFileModel().getTests();
                TestPlan currentTestPlan = resourcePerspectives.as(MutableTestPlan.class, resource);
                checkTestCases(previousTestCasesArray, currentTestPlan);
            } catch (UnirestException e) {
                System.out.println("There was a problem getting the data from the Rest call");
                System.out.println(e.toString());
            } finally {
                System.out.println("----------------------Decorator----------------------");
            }
        }

        System.out.println("______________________TEasda1111111111111sfasfdasfERS______________________");
    }


    public void checkTestCases(TestCaseModel[] previousTestCasesArray, TestPlan currentTestPlan) {
        saveDataForThisProjectVersion(currentTestPlan);
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

    }


    /**
     * Makes a REST call to get the Data from the previous run
     *
     * @return
     * @throws UnirestException
     */
    public TestFileModel getTestFileModel() throws UnirestException {
        String baseURL = settings.getString("sonar.core.serverBaseURL");
        HttpResponse<JsonNode> getResponse = Unirest.get(baseURL + "/api/tests/show")
                .field("key", resource.getEffectiveKey()).asJson();
        Gson gson = new Gson();
        JsonNode body = getResponse.getBody();
        return gson.fromJson(String.valueOf(body), TestFileModel.class);
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

    public void saveDataForThisProjectVersion(TestPlan currentTestPlan) {
        String runTimeSave = null;
        try {
            Measure measure = context.getMeasure(TestRuntimeMetric.TEST_RUNTIME_SAVE);
            runTimeSave = measure.getData();
        } catch (Exception e) {

        }
            Map<String, TestFileModel> saveMap;
            Gson gson = new Gson();
            if (runTimeSave == null || runTimeSave.isEmpty()) {
                saveMap = new HashMap<String, TestFileModel>();
            } else {
                Type type = new TypeToken<Map<String, TestFileModel>>() {
                }.getType();
                saveMap = gson.fromJson(runTimeSave, type);
            }
            TestFileModel testFileModel = testPlanToTestFileModel(currentTestPlan);
            saveMap.put(projectVersion, testFileModel);
            context.saveMeasure(new Measure(TestRuntimeMetric.TEST_RUNTIME_SAVE, gson.toJson(saveMap)));
    }

    public TestFileModel testPlanToTestFileModel(TestPlan testPlan) {
        Iterable<MutableTestCase> testCases = testPlan.testCases();
        testPlan.testCases();
        TestCaseModel[] testCaseArray = new TestCaseModel[Iterables.size(testCases)];
        int i = 0;
        for (TestCase testCase : testCases) {
            TestCaseModel model = new TestCaseModel();
            model.setName(testCase.name());
            model.setDurationInMs(testCase.durationInMs());
            testCaseArray[i++] = model;
        }
        return new TestFileModel(testCaseArray);
    }
}
