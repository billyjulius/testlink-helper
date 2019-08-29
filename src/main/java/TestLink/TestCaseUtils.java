package TestLink;

import br.eti.kinoshita.testlinkjavaapi.constants.ActionOnDuplicate;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionType;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestCaseStep;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;

import java.util.ArrayList;
import java.util.List;

public class TestCaseUtils extends TestLinkMain{

    protected String _TESTNAME;

    protected Integer _SUITEID;

    protected String _SUMMARY;

    protected List<TestCaseStep> _testCaseSteps;

    public TestCaseUtils(String test_name, Integer suite_id, String summary, List<StepResult> stepResults){
        this. _TESTNAME = test_name;
        this._SUITEID = suite_id;
        this._SUMMARY = summary;

        this._testCaseSteps = setTestStep(stepResults);
    }

    public Integer createTestCase() {
        Integer id = getTestCaseID(_TESTNAME);

        if(!isTestCaseExist(id)) {
            TestCase testCase = api.createTestCase(
                    _TESTNAME,
                    _SUITEID,
                    _PROJECTID,
                    "admin",
                    _SUMMARY,
                    _testCaseSteps,
                    null,
                    null,
                    null,
                    ExecutionType.AUTOMATED,
                    null,
                    null,
                    true,
                    ActionOnDuplicate.BLOCK
            );

            id = testCase.getId();
        } else {
            TestCase testCase = getTestCaseExist(id);

            for(TestCaseStep step : _testCaseSteps) {
                System.out.println("DEBUG Step Update = " + step.getActions());
            }

            testCase.setSteps(_testCaseSteps);
            testCase.setVersion(testCase.getVersion()+1);
            api.updateTestCase(testCase);
        }

        return id;
    }

    public String getTestCaseExternalID(Integer tp_id) {
        TestCase test_case = api.getTestCase(tp_id, null, null);
        return test_case.getFullExternalId();
    }

    private Integer getTestCaseID(String tc_name) {
        try {
            Integer tc_id = api.getTestCaseIDByName(tc_name, null,_PROJECTNAME,null);
            return tc_id;
        } catch (TestLinkAPIException exception) {
            return 0;
        }
    }

    private TestCase getTestCaseExist(Integer tc_id) {
        return api.getTestCase(tc_id, null, null);
    }

    private Boolean isTestCaseExist(Integer tc_id) {
        return tc_id != 0;
    }

    private List<TestCaseStep> setTestStep(List<StepResult> stepResults) {
        List<TestCaseStep> steps = new ArrayList<TestCaseStep>();

        Integer number = 1;
        for (StepResult stepResult : stepResults) {
            TestCaseStep step = new TestCaseStep();
            step.setNumber(number);
            step.setActions(stepResult.getName());
            step.setExecutionType(ExecutionType.AUTOMATED);
            step.setExpectedResults("");
            steps.add(step);

            number++;
        }

        return steps;
    }
}
