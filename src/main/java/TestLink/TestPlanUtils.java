package TestLink;

import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;

public class TestPlanUtils extends TestLinkMain{

    protected String _PLANNAME;

    public TestPlanUtils(String plan_name) {
        _PLANNAME = plan_name;
    }

    public Integer createTestPlan() {
        Integer tp_id = getTestPlanID(_PLANNAME);

        if(!isTestPlanExist(tp_id)) {
            TestPlan test_plan = api.createTestPlan(_PLANNAME, _PROJECTNAME, null, true, true);
            tp_id = test_plan.getId();
        };

        return tp_id;
    }

    public Integer getTestPlanID(String tp_name) {
        try {
            TestPlan test_plan = api.getTestPlanByName(tp_name, _PROJECTNAME);
            return test_plan.getId();
        } catch (TestLinkAPIException exception) {
            return 0;
        }
    }

    public Boolean isTestPlanExist(Integer tp_id) {
        return tp_id != 0;
    }
}
