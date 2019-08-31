## Testlink Helper

Testlink Helper is a java package for easier integration with automation testing tool that need to sync the test documentation with test management tools [Testlink](http://testlink.org/).

Flow: 
1. Create test plan
2. Create test build
3. Create test case
4. Assign test case to test plan
5. Assign user to test execution plan
6. Send report test execution

### Example

TestLinkMain.java
```java
TestLinkMain testLinkMain = new TestLinkMain(TESTLINK_XMLRPC_URL, TESTLINK_USER_KEY);
testLinkMain.Init(PROJECT_NAME, PROJECT_ID, VERSION, BUILD_NAME, PLAN_NAME, USERNAME)
testLinkMain.Run(TESTCASE_NAME, TEST_IS_SUCCESS, ERROR_MESSAGE, STEP_RESULTS, SUITE_ID);
```

To send step result to TestLink with Testlink Helper, you need to send it with class StepResult.java
```java
List<StepResult> stepResults =  new ArrayList<StepResult>();

for(TestStep testStep : testStepList) {
    StepResult stepResult = new StepResult();
    stepResult.name = testStep.getDescription();
    stepResult.status = testStep.isFailure() || testStep.isError() ? "Failed" : "Success";
    stepResults.add(stepResult);
}
```