package de.manuel_huber.sonar;

import org.sonar.api.rule.Severity;
import org.sonar.api.server.rule.RulesDefinition;

/**
 * Created by Manuel on 21.10.2015.
 */
public class TextfitRulesDefinition implements RulesDefinition {

    public static final String REPOSITORY_KEY = "textfit-rules";
    public static final String INCREASE50 = "50p-increase";

    public void define(Context context) {
        NewRepository repo = context.createRepository(REPOSITORY_KEY, "java");
        repo.setName("Textfit");

        repo.createRule(INCREASE50).setName("50% increase").setSeverity(Severity.INFO).setHtmlDescription("The Textfit for this Test has increased by over 50%");

        repo.done();



    }
}
