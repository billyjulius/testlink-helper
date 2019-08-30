package TestLink;

/**
 * Class to hold test step result information
 */
public class StepResult {

    public String name;

    public String status;

    /**
     * Get step name
     * @return a <code>string</code> step name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get step result
     * @return a <code>string</code> step result
     */
    public String getStatus() {
        return this.status;
    }
}
