package TestLink;

import br.eti.kinoshita.testlinkjavaapi.constants.ActionOnDuplicate;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionType;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestCaseStep;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that hold method for test case creation flow
 */
public class TestCaseUtils extends TestLinkMain{

    private String _TESTNAME;

    private Integer _SUITEID;

    private String _SUMMARY;

    private Integer _PROJECT_ID;

    private String _PROJECT_NAME;

    private List<TestCaseStep> _testCaseSteps;

    /**
     * Class constructor with argument for set required data for test case creation
     * @param test_name is test name
     * @param suite_id is id of test suite
     * @param summary is summary of test case
     * @param stepResults is list of step result modeled in StepResult class
     */
    public TestCaseUtils(Integer project_id, String project_name, String test_name, Integer suite_id, String summary, List<StepResult> stepResults){
        this._PROJECT_ID = project_id;
        this._PROJECT_NAME = project_name;
        this. _TESTNAME = test_name;
        this._SUITEID = suite_id;
        this._SUMMARY = summary;

        this._testCaseSteps = setTestStep(stepResults);
    }

    /**
     * Create test case or get existing test case
     * @return a <code>integer</code> of created or existing test case ID
     */
    public Integer createTestCase() {
        Integer id = getTestCaseID(_TESTNAME);

        if(id == 0) {
            TestCase testCase = api.createTestCase(
                    _TESTNAME,
                    _SUITEID,
                    _PROJECT_ID,
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
            TestCase testCase = api.createTestCase(
                    _TESTNAME,
                    _SUITEID,
                    _PROJECT_ID,
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
                    ActionOnDuplicate.CREATE_NEW_VERSION
            );
            id = testCase.getId();
        }

        return id;
    }

    /**
     * Get test case full external Id
     * @param tc_id is test case Id
     * @return a <code>string</code> of the full external Id, composed by the prefix + externalId
     */
    public String getTestCaseExternalID(Integer tc_id) {
        TestCase test_case = api.getTestCase(tc_id, null, null);
        return test_case.getFullExternalId();
    }

    /**
     * Get test case Id with given test case name
     * @param tc_name is test case name
     * @return a <code>integer</code> of test case Id with return 0 if test case not found.
     */
    private Integer getTestCaseID(String tc_name) {
        try {
            Integer tc_id = api.getTestCaseIDByName(tc_name, null,_PROJECT_NAME,null);
            return tc_id;
        } catch (TestLinkAPIException exception) {
            return 0;
        }
    }

    /**
     * Get existing test case with give test case Id
     * @param tc_id is test case Id
     * @return a <code>TestCase</code> class containing existing test case information
     */
    private TestCase getTestCaseExist(Integer tc_id) {
        return api.getTestCase(tc_id, null, null);
    }

    /**
     * Set step result to TestCaseStep class model
     * @param stepResults is list of StepResult class
     * @return list of TestCaseStep class
     */
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
