package models;
/**
 * The Productivity App class represents Game App objects that extend the App superclass.
 * Productivity apps extend all app variables.
 *
 * The methods set the fields to their specified values,
 * and generate a toString that extends the App toString.
 *
 * @author Liam FLynn
 * @version 1.0
 */
public class ProductivityApp extends App {

    /**
     * Generates a toString that states the app type before extending the app superclass toString.
     * @return The app superclass toString with the app type (Productivity) stated.
     */
    @Override
    public String toString() {
        return "Productivity App: \n" + super.toString();
    }

    public ProductivityApp(models.Developer developer, String appName, double appSize, double appVersion, double appCost) {
        super(developer, appName, appSize, appVersion, appCost);
        setDeveloper(developer);
        setAppName(appName);
        setAppSize(appSize);
        setAppVersion(appVersion);
        setAppCost(appCost);
    }

    /**
     * This method evaluates whether this app meets the requirements to be recommended or not.
     * Recommended if the cost is greater than or equal to 1.99 and calculated rating is greater than 3.
     *
     * @return true if app is recommended, false if not
     */
    public boolean isRecommendedApp() {
        return getAppCost() >= 1.99 && calculateRating() > 3.0;
    }
}
