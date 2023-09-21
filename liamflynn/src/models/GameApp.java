package models;

/**
 * The Game App class represents Game App objects that extend the App superclass.
 * Game apps extend all app variables as well as having an assigned isMultiplayer variable.
 *
 * The methods set the fields to their specified values,
 * and generate a toString that extends the App toString.
 *
 * @author Liam FLynn
 * @version 1.0
 */
public class GameApp extends App {

    public boolean isMultiplayer;

    public boolean isMultiplayer() {
        return isMultiplayer;
    }

    public void setMultiplayer(boolean isMultiplayer) {
        this.isMultiplayer = isMultiplayer;
    }

    public GameApp(models.Developer developer, String appName, double appSize, double appVersion, double appCost, boolean isMultiplayer) {
        super(developer, appName, appSize, appVersion, appCost);
        setDeveloper(developer);
        setAppName(appName);
        setAppSize(appSize);
        setAppVersion(appVersion);
        setAppCost(appCost);
        setMultiplayer(isMultiplayer);
    }

    /**
     * This method evaluates whether this app meets the requirements to be recommended or not.
     * Recommended if the game is multiplayer and calculated rating is greater than or equal to 4.
     *
     * @return true if app is recommended, false if not
     */
    public boolean isRecommendedApp() {
        return isMultiplayer && calculateRating() >= 4.0;
    }

    /**
     * This method adds the summary contents of a game app to the summary of the app superclass.
     *
     * @return a user-friendly string that adds the value of the game app exclusive isMultiplayer variable to the app summary string.
     */
    public String appSummary() {
        return super.appSummary() + ("\t Multiplayer: " + utils.Utilities.booleanToYN(isMultiplayer()) + "\n");
    }

    /**
     * Generates a toString that appends Game App exclusive values to the app superclass toString.
     *
     * @return The app superclass toString with the app type (Game) stated and whether the game app is multiplayer or not appended on.
     */
    @Override
    public String toString() {
        return "Game App: \n " + super.toString() + "\n Multiplayer: " + utils.Utilities.booleanToYN(isMultiplayer());
    }
}
