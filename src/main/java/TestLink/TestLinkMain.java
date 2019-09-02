package TestLink;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;
import br.eti.kinoshita.testlinkjavaapi.model.ReportTCResultResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 *  This class is the entry for testlink flow
 */
public class TestLinkMain {

    protected static TestLinkAPI api;

    protected String _PROJECTNAME;
    protected Integer _PROJECTID;
    protected Integer _VERSION;
    protected String _BUILDNAME;
    protected String _PLANNAME;
    protected String _USERNAME;

    protected String _testname;
    protected String _testsummary;
    protected List<StepResult> _stepResults;
    protected Integer _suiteid;
    protected ExecutionStatus _resultstatus;
    protected String _resultnotes;

    protected Integer tc_id;
    protected Integer tp_id;
    protected Integer tc_external_id;
    protected String full_tc_external_id;

    /**
     * No argument default constructor
     */
    public TestLinkMain() {}

    /**
     * Constructor with arguments to initialize testlink connection and saving required data
     * @param testlink_url is testlink xml-rpc url location
     * @param testlink_key is user api key for authenticate
     */
    public TestLinkMain(String testlink_url, String testlink_key) {
        URL url = null;

        try {
            url = new URL(testlink_url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        api = new TestLinkAPI(url, testlink_key);
    }

    /**
     * Set required testlink data for project, build, and plan
     * @param project_name is project name
     * @param project_id is project id seen in testlink
     * @param version is project version (always set 1)
     * @param build_name is build name
     * @param plan_name is plan name
     * @param username is username that will assigned in test
     */
    public void Init(String project_name, Integer project_id, Integer version, String build_name, String plan_name, String username) {
        this._PROJECTNAME = project_name;
        this._PROJECTID = project_id;
        this._VERSION = version;
        this._BUILDNAME = build_name;
        this._PLANNAME = plan_name;
        this._USERNAME = username;
    }

    /**
     * Run the creation of test case and test case execution
     * @param test_name is string for displayed test case name
     * @param test_is_success is boolean test status
     * @param test_error_message is error message for displayed in test case result notes (leave it empty if there is no error)
     * @param stepResults is list of step result modeled in StepResult class
     * @param test_suite_id is id of test suite
     */
    public void Run(String test_name, Boolean test_is_success, String test_error_message, List<StepResult> stepResults, Integer test_suite_id) {
        this._testname = test_name;
        this._testsummary = test_name;
        this._suiteid = test_suite_id;
        this._resultstatus = this.setExecutionStatus(test_is_success);
        this._resultnotes = test_error_message;
        this._stepResults = stepResults;

        TestPlanUtils testPlanUtils = new TestPlanUtils(_PLANNAME, _PROJECTNAME);
        tp_id = testPlanUtils.createTestPlan();

        TestBuildUtils testBuildUtils = new TestBuildUtils(tp_id, _BUILDNAME);
        testBuildUtils.createTestBuild();

        TestCaseUtils testCaseUtils = new TestCaseUtils(_PROJECTID, _PROJECTNAME, _testname, _suiteid, _testsummary, _stepResults);
        tc_id = testCaseUtils.createTestCase();
        full_tc_external_id = testCaseUtils.getTestCaseExternalID(tc_id);
        tc_external_id = Integer.parseInt(full_tc_external_id.split("-")[1]);

        assignTestCaseToTestPlan();
        assignTestCase();
        reportTestCaseResult();

        System.out.println("DEBUG TEST PLAN :"+ tp_id);
        System.out.println("DEBUG TEST CASE :"+ tc_id);
    }

    /**
     * Send test case execution report
     */
    private void reportTestCaseResult() {
        ReportTCResultResponse result = api.reportTCResult(
                tc_id,
                tc_external_id,
                tp_id,
                _resultstatus,
                null,
                _BUILDNAME,
                _resultnotes,
                false,
                null,
                null,
                null,
                null,
                false
        );
    }

    /**
     * Assign test case to test plan
     */
    private void assignTestCaseToTestPlan() {
        Integer id = api.addTestCaseToTestPlan(_PROJECTID, tp_id, tc_id, _VERSION, null, null, null);
    }

    /**
     * Set execution status for test case execution
     * @param status is status of test case run in test tools
     * @return ExecutionStatus enum
     */
    private ExecutionStatus setExecutionStatus(Boolean status) {
        if(status) {
            return ExecutionStatus.PASSED;
        } else {
            return ExecutionStatus.FAILED;
        }
    }

    /**
     * Assign username to test case
     */
    private void assignTestCase() {
        api.assignTestCaseExecutionTask(tp_id, full_tc_external_id, _USERNAME, _BUILDNAME);
    }
}
