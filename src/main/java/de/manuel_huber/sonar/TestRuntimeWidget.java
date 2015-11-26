package de.manuel_huber.sonar;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;

/**
 * Created by m.huber on 28/10/2015.
 */
@UserRole(UserRole.USER)
@Description("TODO")
public class TestRuntimeWidget extends AbstractRubyTemplate implements RubyRailsWidget {
    @Override
    protected String getTemplatePath() {
        // for development
        return "C:/Users/m.huber/_Projects/sonar-test-runtime-analysis-plugin/src/main/resources/TestRuntime.html.erb";
        // for deployment
        // return "src/main/resources/TestRuntime.html.erb";
    }

    public String getId() {
        return "testruntimeanalysis";
    }

    public String getTitle() {
        return "Test runtime analysis";
    }
}
