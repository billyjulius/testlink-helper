package CustomListener;

public class SerenityResult {

    private static SerenityResult sInstance = null;

    protected Integer TEST_COUNT = 0;

    private SerenityResult() {}

    public static SerenityResult getInstace() {
        if(sInstance == null) {
            sInstance = new SerenityResult();
        }

        return sInstance;
    }

    public void addTestCount() {
        TEST_COUNT += 1;
    }

    public Integer getTestCount() {
        return TEST_COUNT;
    }
}
