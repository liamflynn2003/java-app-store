package models;

/**
 * The Education App class represents Education App objects that extend the App superclass.
 * Education apps extend all app variables as well as having an assigned level variable representing the difficulty of the app.
 *
 * The methods set the fields to their specified values,
 * and generate a toString that extends the App toString.
 *
 * @author Liam FLynn
 * @version 1.0
 */
public class EducationApp extends App {
    private int level = 0;

    public int getLevel() {
        return level;
    }

    /**
     * This setter method ensures any given Level is between 1 and 10.
     *
     * @param level The level assigned to an Education App, should be an int from a range of 1 to 10 and has a default value of 0.
     */
    public void setLevel(int level) {
        if (utils.Utilities.validRange(level, 1, 10)) {
            this.level = level;
        }
    }

    public EducationApp(models.Developer developer, String appName, double appSize, double appVersion, double appCost, int level) {
        super(developer, appName, appSize, appVersion, appCost);
        setDeveloper(developer);
        setAppName(appName);
        setAppSize(appSize);
        setAppVersion(appVersion);
        setAppCost(appCost);
        setLevel(level);
    }

    /**
     * This method evaluates whether this app meets the requirements to be recommended or not.
     * Recommended if cost is greater than 0.99, level is greater than or equal to 3 and the calculated rating is greater than or equal to 3.5.
     *
     * @return true if app is recommended, false if not
     */
    public boolean isRecommendedApp() {
        return getAppCost() > 0.99 && getLevel() >= 3 && calculateRating() >= 3.5;
    }

    /**
     * This method adds the summary contents of an education app to the summary of the app superclass.
     *
     * @return a user-friendly string that adds the education app exclusive level variable to the app summary string.
     */
    public String appSummary() {
        return super.appSummary() + (" level " + getLevel() + "\n");
    }

    /**
     * Generates a toString that appends Education App exclusive values to the app superclass toString.
     *
     * @return The app superclass toString with the app type (Education) stated and level appended on.
     */
    @Override
    public String toString() {
        return "Education App: \n" + super.toString() +
                "\n Level: " + getLevel();
    }
}