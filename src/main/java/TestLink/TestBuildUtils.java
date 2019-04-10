package TestLink;

import br.eti.kinoshita.testlinkjavaapi.model.Build;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;

public class TestBuildUtils extends TestLinkMain {

    protected Integer _PLANID;

    protected Build[] builds;

    public TestBuildUtils(Integer test_plan_id) {
        _PLANID = test_plan_id;
    }

    public void createTestBuild() {
        builds = getAllTestBuild();
        if(!isGivenBuildNameExist()) {
            Build build = api.createBuild(_PLANID, _BUILDNAME, null);
        }
    }

    public Build[] getAllTestBuild() {
        return api.getBuildsForTestPlan(_PLANID);
    }

    public Boolean isGivenBuildNameExist() {
        if(builds != null) {
            for (Build num:builds) {
                if(num.getName().equals(_BUILDNAME)) {
                    return true;
                }
            }
        }

        return false;
    }
}
