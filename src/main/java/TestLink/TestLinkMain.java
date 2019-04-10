package TestLink;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;
import br.eti.kinoshita.testlinkjavaapi.model.ReportTCResultResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class TestLinkMain {

    static TestLinkAPI api;

    String TESTLINK_URL = "http://testlink.sepulsa.id:6969/lib/api/xmlrpc/v1/xmlrpc.php";
    String TESTLINK_KEY = "4b13c21a5a1fd0d2c5f4333f8ad724c6";

    protected Integer tc_id;
    protected Integer tp_id;
    protected Integer tc_external_id;
    protected String full_tc_external_id;

    String _PROJECTNAME = "Project Coba Integrasi";
    Integer _PROJECTID = 1169;
    Integer _VERSION = 1;
    String _BUILDNAME = "Automated";
    String _PLANNAME = "Plan 201906";
    String _USERNAME = "billy";

    protected String _testname;
    protected String _testsummary;
    protected List<StepResult> _stepResults;
    protected Integer _suiteid;
    protected ExecutionStatus _resultstatus;
    protected String _resultnotes;

    public TestLinkMain() {}

    public TestLinkMain(String test_name, Boolean test_is_success, String test_error_message, List<StepResult> stepResults, Integer test_suite_id) {
        URL url = null;

        try {
            url = new URL(TESTLINK_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        api = new TestLinkAPI(url, TESTLINK_KEY);

        this._testname = test_name;
        this._testsummary = test_name;
        this._suiteid = test_suite_id;
        this._resultstatus = this.setExecutionStatus(test_is_success);
        this._resultnotes = test_error_message;
        this._stepResults = stepResults;
    }

    public void run() {
        TestPlanUtils testPlanUtils = new TestPlanUtils(_PLANNAME);
        tp_id = testPlanUtils.createTestPlan();

        TestBuildUtils testBuildUtils = new TestBuildUtils(tp_id);
        testBuildUtils.createTestBuild();

        TestCaseUtils testCaseUtils = new TestCaseUtils(_testname, _suiteid, _testsummary, _stepResults);
        tc_id = testCaseUtils.createTestCase();
        full_tc_external_id = testCaseUtils.getTestCaseExternalID(tc_id);
        tc_external_id = parseFullExternalID(full_tc_external_id);

        assignTestCaseToTestPlan();

        assigtTestCase();

        reportTestCaseResult();

        System.out.println("DEBUG TEST PLAN :"+ tp_id);
        System.out.println("DEBUG TEST CASE :"+ tc_id);
    }

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

    private void assignTestCaseToTestPlan() {
        Integer id = api.addTestCaseToTestPlan(_PROJECTID, tp_id, tc_id, _VERSION, null, null, null);
    }

    private ExecutionStatus setExecutionStatus(Boolean status) {
        if(status) {
            return ExecutionStatus.PASSED;
        } else {
            return ExecutionStatus.FAILED;
        }
    }

    private Integer parseFullExternalID(String full_external) {
        return Integer.parseInt(full_external.split("-")[1]);
    }

    private void assigtTestCase() {
        api.assignTestCaseExecutionTask(tp_id, full_tc_external_id, _USERNAME, "Automated");
    }
}
