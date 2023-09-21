package models;

import java.util.ArrayList;
import java.util.List;

/**
 * The App superclass represents App objects that can be contained in the AppStoreAPI array list.
 * The superclass can contain objects with the subclasses Game App, Education App and Productivity Apps.
 *
 * Every app has an assigned developer and name, size, version and cost variables.
 * Each app also contains a list of the ratings given to the app by users.
 * <p>
 * The methods set the fields to their specified values,
 * can generate a summary of the app including all it's variables,
 * add and list ratings as well as calculate the average rating,
 * and generate a toString.
 *
 * The subclasses GameApp, EducationApp and ProductivityApp all extend this class.
 *
 * @author Liam FLynn
 * @version 1.0
 */
public abstract class App {

    private Developer developer;
    private String appName = "No App Name";
    private double appSize = 0;
    private double appVersion = 1.0;
    private double appCost = 0;
    private List<models.Rating> ratings = new ArrayList<>();

    /**
     * Generates a toString for an app containing all relevant values.
     *
     * @return The app name, version, size, cost, calculated overall rating and list of all ratings as a user-friendly String.
     */
    @Override
    public String toString() {
        return getAppName() +
                " (Version " + getAppVersion() +
                ")," + "Developer: " + getDeveloper()
                + getAppSize() + "MB" +
                ", Cost: " + getAppCost() +
                ", Ratings (" + calculateRating() + ")" +
                "\n List of ratings: \n" + listRatings();
    }

    public App(Developer developer, String appName, double appSize, double appVersion, double appCost) {
        setDeveloper(developer);
        setAppName(appName);
        setAppSize(appSize);
        setAppVersion(appVersion);
        setAppCost(appCost);
    }


    public List<Rating> getRatings() {
        return ratings;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public double getAppSize() {
        return appSize;
    }

    /**
     * This setter method ensures any given App Size is between 1 and 1000.
     *
     * @param appSize The size of an individual app, should be an int from a range of 1 to 1000 and has a default value of 0.
     */
    public void setAppSize(double appSize) {
        if (utils.Utilities.validRange(appSize, 1, 1000)) {
            this.appSize = appSize;
        }
    }

    public double getAppVersion() {
        return appVersion;
    }

    /**
     * This setter method ensures any given App Version is greater than 1.0.
     *
     * @param appVersion The version number of the app, must be greater than 1.0 and has a default value of 1.0.
     */
    public void setAppVersion(double appVersion) {
        if (appVersion >= 1.0) {
            this.appVersion = appVersion;
        }
    }

    public double getAppCost() {
        return appCost;
    }

    /**
     * This setter method ensures any given App Cost is greater than 0.
     *
     * @param appCost The cost of an individual app, must be greater than 0 and has a default value of 0 and has no upper limit.
     */
    public void setAppCost(double appCost) {
        if (appCost >= 0) {
            this.appCost = appCost;
        }
    }

    public abstract boolean isRecommendedApp();

    /**
     * This method adds a rating to an app.
     *
     * @param rating rating the user wants to give the app.
     */
    public void addRating(Rating rating) {
        ratings.add(rating);
    }

    /**
     * This method list all ratings that have been added to an app.
     *
     * @return a string containing all ratings added to an app, and tells the user that no ratings have been added if so.
     */
    public String listRatings() {
        if (ratings.isEmpty()) {
            return "No ratings added" + "\n";
        }
        StringBuilder string = new StringBuilder();
        for (Rating rating : ratings) {
            string.append(rating).append("\n");
        }
        return string.toString();
    }

    /**
     * This method calculates the average rating of an app by getting the average of all ratings added to the app.
     *
     * @return the double value of the calculated rating
     */
    public double calculateRating() {
        if (ratings.isEmpty()) {
            return 0;
        }
        double average = 0;
        int i = 0;
        for (Rating rating : ratings) {
            if (rating.getNumberOfStars() != 0) {
                average += rating.getNumberOfStars();
                i++;
            }
        }
        return average / i;
    }

    /**
     * This method constructs an in depth summary of the app, it's specifics and its average rating.
     *
     * @return a user-friendly string containing a summary of the app with its name, version, developer, cost and calculated rating.
     */
    public String appSummary() {
        return appName + "(V" + appVersion + ") by " + developer + ", €" + appCost + ". Rating: " + calculateRating();
    }

}