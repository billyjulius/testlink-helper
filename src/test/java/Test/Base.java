package Test;

import TestLink.StepResult;

import TestLink.TestLinkMain;
import net.serenitybdd.jbehave.*;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestStep;
import net.thucydides.core.steps.StepEventBus;
import org.jbehave.core.annotations.AfterScenario;
import net.thucydides.core.reports.xml.XMLTestOutcomeReporter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Base extends SerenityStory {

    protected TestOutcome testOutcome;

    protected String errorMessage;

    protected Boolean isSuccess = true;


    public void TestLinkIntegration() {
        this.testOutcome = GetTestOutcome();

        String TestCaseName = GetTestCaseName();

        //  Suite ID from scenario name with pattern '1234567'
        // Integer SuiteID = ParseSuiteID(TestCaseName);

        //  Suite ID hardcode if necessary
        Integer SuiteID = 2812;

        System.out.println("");
        System.out.println("<====================================>");
        System.out.println("");

        System.out.println("DEBUG TEST CASE NAME : " + TestCaseName);

        List<StepResult> stepResults = GetTestResult();

        for (StepResult stepResult : stepResults) {
            System.out.println("DEBUG TEST STEP NAME : " + stepResult.getName() + " ("+ stepResult.getStatus() +")");
        }

        TestLinkMain testLinkMain = new TestLinkMain(TestCaseName, this.isSuccess, this.errorMessage, stepResults, SuiteID);
        testLinkMain.run();
    }

    private TestOutcome GetTestOutcome() {
        List<TestOutcome> testOutcomeList= StepEventBus.getEventBus().getBaseStepListener().getTestOutcomes();

        TestOutcome testOutcome = testOutcomeList.get(testOutcomeList.size()-1);

        errorMessage = "";
        if(testOutcome.isFailure() || testOutcome.isError()) {
            errorMessage = testOutcome.getTestFailureMessage();
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
            String status = "Success";
            if(testStep.isFailure() || testStep.isError()) {
                status = "Failed";
            }

            StepResult stepResult = new StepResult();
            stepResult.name = testStep.getDescription();
            stepResult.status = status;

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
