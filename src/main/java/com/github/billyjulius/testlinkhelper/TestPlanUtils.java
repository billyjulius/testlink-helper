package com.github.billyjulius.testlinkhelper;

import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;

/**
 * Class that hold method for test plan creation flow
 */
public class TestPlanUtils extends TestLinkMain{

    private String plan_name;
    private String project_name;

    /**
     * Constructor to set plan name and project name
     * @param plan_name is plan name
     * @param project_name is project name
     */
    public TestPlanUtils(String plan_name, String project_name) {
        this.plan_name = plan_name;
        this.project_name = project_name;
    }

    /**
     * Create test plan
     * @return created or existing test plan Id
     */
    public Integer createTestPlan() {
        Integer tp_id = getTestPlanID(plan_name);

        if(tp_id.equals(0)) {
            TestPlan test_plan = api.createTestPlan(plan_name, project_name, null, true, true);
            tp_id = test_plan.getId();
        };

        return tp_id;
    }

    /**
     * Get test plan Id
     * @param tp_name is test plan name
     * @return test plan Id, will return 0 if test plan not exist
     */
    private Integer getTestPlanID(String tp_name) {
        try {
            TestPlan test_plan = api.getTestPlanByName(tp_name, project_name);
            return test_plan.getId();
        } catch (TestLinkAPIException exception) {
            return 0;
        }
    }
}
