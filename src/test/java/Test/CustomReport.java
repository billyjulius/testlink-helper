package Test;

import CustomListener.SerenityResult;
import net.serenitybdd.jbehave.SerenityStory;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.steps.StepEventBus;

import org.jbehave.core.annotations.AfterScenario;

import java.util.List;


public class CustomReport extends SerenityStory {

    @AfterScenario
    public void StartGenerateReport() {

        SerenityResult serenityResult = SerenityResult.getInstace();
        serenityResult.addTestCount();

        System.out.println("DEBUG TEST COUNT : " + serenityResult.getTestCount());

//        Serenity.setSessionVariable("SerenityResult").to(serenityResult);

//        List<TestOutcome> testOutcomeList= StepEventBus.getEventBus().getBaseStepListener().getTestOutcomes();
//        TestOutcome testOutcome = testOutcomeList.get(testOutcomeList.size()-1);
//
//        String suite_name = testOutcome.getStoryTitle();
//        System.out.println("DEBUG TEST SUITE NAME : " + suite_name);
//
//        String test_name = testOutcome.getName();
//        System.out.println("DEBUG TEST CASE NAME : " + test_name);
    }
}
