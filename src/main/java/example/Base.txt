package example;


import TestLink.StepResult;
import TestLink.TestLinkMain;
import net.serenitybdd.jbehave.SerenityStory;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestStep;
import net.thucydides.core.steps.StepEventBus;
import org.jbehave.core.annotations.AfterScenario;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Base extends SerenityStory {

    protected TestOutcome testOutcome;
    protected String errorMessage;
    protected Boolean isSuccess = true;

    String TESTLINK_URL = "http://<TESTLINK_INSTACE>lib/api/xmlrpc/v1/xmlrpc.php";
    String TESTLINK_KEY = "xxxxxxxxxxx";

    String _PROJECTNAME = "Project Name here";
    Integer _PROJECTID = 12345;
    Integer _VERSION = 1;
    String _BUILDNAME = "Build Name Here";
    String _PLANNAME = "Plan Name Here";
    String _USERNAME = "Username here";

    @AfterScenario()
    public void TestLinkIntegration() {
        this.testOutcome = GetTestOutcome();

        String TestCaseName = GetTestCaseName();

        //  Suite ID from scenario name with pattern '1234567'
        Integer SuiteID = ParseSuiteID(TestCaseName);

        //  Suite ID hardcode if necessary
        // Integer SuiteID = 12345;

        List<StepResult> stepResults = GetTestResult();

        TestLinkMain testLinkMain = new TestLinkMain(TESTLINK_URL, TESTLINK_KEY);
        testLinkMain.Init(_PROJECTNAME, _PROJECTID, _VERSION, _BUILDNAME, _PLANNAME, _USERNAME);
        testLinkMain.Run(TestCaseName, this.isSuccess, this.errorMessage, stepResults, SuiteID);
    }

    private TestOutcome GetTestOutcome() {
        List<TestOutcome> testOutcomeList= StepEventBus.getEventBus().getBaseStepListener().getTestOutcomes();

        TestOutcome testOutcome = testOutcomeList.get(testOutcomeList.size()-1);

        errorMessage = "";
        if(testOutcome.isFailure() || testOutcome.isError()) {
            errorMessage = testOutcome.getErrorMessage();
            isSuccess = false;
        }

        return testOutcome;
    }

    private String GetTestCaseName() {
        return this.testOutcome.getName();
    }

    private List<StepResult> GetTestResult() {
        List<TestStep> testStepList = this.testOutcome.getTestSteps();
        List<StepResult> stepResults =  new ArrayList<StepResult>();

        for(TestStep testStep : testStepList) {
            StepResult stepResult = new StepResult();

            stepResult.name = testStep.getDescription();
            stepResult.status = testStep.isFailure() || testStep.isError() ? "Failed" : "Success";

            stepResults.add(stepResult);
        }

        return stepResults;
    }

    private Integer ParseSuiteID(String TestCaseName) {
        Pattern pattern = Pattern.compile("'(\\d+)'");
        Matcher matcher = pattern.matcher(TestCaseName);

        if(matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        return null;
    }
}
