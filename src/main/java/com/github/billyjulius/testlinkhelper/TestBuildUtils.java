package com.github.billyjulius.testlinkhelper;

import br.eti.kinoshita.testlinkjavaapi.model.Build;

/**
 * Class that hold method for test build creation flow
 */
public class TestBuildUtils extends TestLinkMain {

    private Integer plan_id;
    private String build_name;
    private Build[] builds;

    /**
     * Constructor to set test plan Id and test build name
     * @param test_plan_id is test plan Id
     * @param test_build_name is test build name
     */
    public TestBuildUtils(Integer test_plan_id, String test_build_name) {
        plan_id = test_plan_id;
        build_name = test_build_name;
    }

    /**
     * Create test build if not exist
     * @return build Id
     */
    public Integer createTestBuild() {
        Integer id = 0;
        builds = getAllTestBuild();
        if(!isGivenBuildNameExist()) {
            Build build = api.createBuild(plan_id, build_name, null);
            id = build.getId();
        } else {
            id = getSpecificBuildId();
        }

        return id;
    }

    /**
     * Get all test build
     * @return list of all build in test plan
     */
    private Build[] getAllTestBuild() {
        return api.getBuildsForTestPlan(plan_id);
    }

    /**
     * Check if test build with given name is exist
     * @return boolean true if test build is exist
     */
    private Boolean isGivenBuildNameExist() {
        if(builds != null) {
            for (Build num:builds) {
                if(num.getName().equals(build_name)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Find build Id with designated name
     * @return build id
     */
    private Integer getSpecificBuildId() {
        Integer id = 0;
        if(builds != null) {
            for (Build num:builds) {
                if(num.getName().equals(build_name)) {
                    id = num.getId();
                }
            }
        }

        return id;
    }
}
