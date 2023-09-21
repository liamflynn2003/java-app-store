package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.*;
import utils.ISerializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import static utils.RatingUtility.generateRandomRating;

/**
 * The AppStoreAPI class contains and manages an Apps array list.
 * <p>
 * It can pull from and change Game Apps, Productivity Apps, Education Apps.
 * <p>
 * The methods get info from and updates apps in several ways,
 * return the number of specific app types,
 * list apps in specific orders or apps of specific types,
 * search for apps by different means,
 * and sort and swap apps.
 *
 * @author Liam FLynn
 * @version 1.0
 */
public class AppStoreAPI implements ISerializer {

    private List<App> apps;

    public AppStoreAPI() {
        apps = new ArrayList<>();
    }

    /**
     * This method adds an app object to the apps array list within the AppStoreAPI.
     *
     * @param App an object of the app class.
     * @return the app that was added, null if adding of app was a failure.
     */
    public boolean addApp(App App) {
        return apps.add(App);
    }


    /**
     * This method finds an app object in the apps array list within the AppStoreAPI using its index number.
     * @param index the index number of the app.
     * @return the app at the index number given.
     */
    public App findApp(int index) {
        if (isValidIndex(index)) {
            return apps.get(index);
        }
        return null;
    }

    /**
     * This method can update a pre-existing Productivity App with new values.
     *
     * @param indexToUpdate the index number of the app the user wants to update.
     * @param developer     the new developer of the app that the user will enter.
     * @param appName       the new name of the app that the user will choose.
     * @param appSize       the new size of the app that the user will choose.
     * @param appVersion    the new version of the app.
     * @param appCost       the new cost of the app.
     * @return the boolean result of the update depending on its success.
     */
    public boolean updateProductivityApp(int indexToUpdate, Developer developer, String appName, double appSize, double appVersion, double appCost) {
        App foundApp = findApp(indexToUpdate);
        if ((foundApp instanceof ProductivityApp)) {
            foundApp.setDeveloper(developer);
            foundApp.setAppName(appName);
            foundApp.setAppSize(appSize);
            foundApp.setAppVersion(appVersion);
            foundApp.setAppCost(appCost);
            return true;
        }
        return false;
    }

    /**
     * This method can update a pre-existing Education App with new values.
     *
     * @param indexToUpdate the index number of the app the user wants to update.
     * @param developer     the new developer of the app that the user will enter.
     * @param appName       the new name of the app that the user will choose.
     * @param appSize       the new size of the app that the user will choose.
     * @param appVersion    the new version of the app.
     * @param appCost       the new cost of the app.
     * @param level         the new level of the app.
     * @return the boolean result of the update depending on its success.
     */
    public boolean updateEducationApp(int indexToUpdate, Developer developer, String appName, double appSize, double appVersion, double appCost, int level) {
        App foundApp = findApp(indexToUpdate);
        if ((foundApp instanceof EducationApp)) {
            foundApp.setDeveloper(developer);
            foundApp.setAppName(appName);
            foundApp.setAppSize(appSize);
            foundApp.setAppVersion(appVersion);
            foundApp.setAppCost(appCost);
            ((EducationApp) foundApp).setLevel(level);
            return true;
        }
        return false;
    }

    /**
     * This method can update a pre-existing Game App with new values.
     *
     * @param indexToUpdate the index number of the app the user wants to update.
     * @param developer     the new developer of the app that the user will enter.
     * @param appName       the new name of the app that the user will choose.
     * @param appSize       the new size of the app that the user will choose.
     * @param appVersion    the new version of the app.
     * @param appCost       the new cost of the app.
     * @param isMultiplayer whether the app is still a multiplayer app or not.
     * @return the boolean result of the update depending on its success.
     */
    public boolean updateGameApp(int indexToUpdate, Developer developer, String appName, double appSize, double appVersion, double appCost, boolean isMultiplayer) {
        App foundApp = findApp(indexToUpdate);
        if ((foundApp instanceof GameApp)) {
            foundApp.setDeveloper(developer);
            foundApp.setAppName(appName);
            foundApp.setAppSize(appSize);
            foundApp.setAppVersion(appVersion);
            foundApp.setAppCost(appCost);
            ((GameApp) foundApp).setMultiplayer(isMultiplayer);
            return true;
        }
        return false;
    }

    /**
     * This method returns the number of apps stored in AppStoreAPI.
     *
     * @return the number of total apps
     */
    public int numberOfApps() {
        return apps.size();
    }

    /**
     * This method returns the number of game apps stored in AppStoreAPI.
     *
     * @return the number of game apps
     */
    public int numberOfGameApps() {
        int number = 0;
        for (App app : apps) {
            if (app instanceof GameApp) {
                number++;
            }
        }
        return number;
    }

    /**
     * This method returns the number of productivity apps stored in AppStoreAPI.
     *
     * @return the number of productivity apps
     */
    public int numberOfProductivityApps() {
        int number = 0;
        for (App app : apps) {
            if (app instanceof ProductivityApp) {
                number++;
            }
        }
        return number;
    }

    /**
     * This method returns the number of education apps stored in AppStoreAPI.
     *
     * @return the number of education apps
     */
    public int numberOfEducationApps() {
        int number = 0;
        for (App app : apps) {
            if (app instanceof EducationApp) {
                number++;
            }
        }
        return number;
    }

    /**
     * This method deletes an app stored at a specific index in AppStoreAPI.
     *
     * @param index the index of the app that is to be deleted.
     * @return the deleted app
     */
    public App deleteAppByIndex(int index) {
        if (isValidIndex(index)) {
            return apps.remove(index);
        }
        return null;
    }

    /**
     * This method returns an app stored at a specific index in AppStoreAPI.
     *
     * @param index the index of the app that is to be found.
     * @return the app at the requested index
     */
    public App getAppByIndex(int index) {
        if (isValidIndex(index)) {
            return apps.get(index);
        }
        return null;
    }

    /**
     * This method returns an app with a specific name found in AppStoreAPI.
     * @param name the title of the app that is to be found.
     * @return the app that has the requested name.
     */
    public App getAppByName(String name) {
        for (App app : apps) {
            if (Objects.equals(app.getAppName(), name)) return app;
        }
        return null;
    }

    /**
     * This method lists all apps.
     *
     * @return a String that either tells the user no apps have been stored yet or lists all stored apps.
     */
    public String listAllApps() {
        if(apps.isEmpty()){return "No apps added yet";}
        StringBuilder list = new StringBuilder("Apps: ");
            for (App app : apps) {
                list.append("Index: ").append(getAppIndex(app.getAppName())).append(" ").append(app).append("\n");
            }
            return list.toString();
    }

    /**
     * This method lists the summary of all stored apps.
     *
     * @return a String that either tells the user no apps have been stored yet or lists the summary of every app.
     */
    public String listSummaryOfAllApps() {
        StringBuilder list = new StringBuilder("No apps");
        if (!apps.isEmpty()) {
            list = new StringBuilder("List of every App: " + "\n");
            for (App app : apps) {
                list.append("Index: ").append(getAppIndex(app.getAppName())).append(" ").append(app.appSummary()).append("\n");
            }
            return list.toString();
        }
        return list.toString();
    }

    /**
     * This method lists all Game apps.
     *
     * @return a String that either tells the user no game apps have been stored yet or lists all stored game apps.
     */
    public String listAllGameApps() {
        StringBuilder list = new StringBuilder("No Game apps");
        if (!apps.isEmpty()) {
            list = new StringBuilder("List of every Game App: " + "\n");
            for (App app : apps) {
                if (app instanceof GameApp) {
                    list.append("Index: ").append(getAppIndex(app.getAppName())).append(" ").append(app).append("\n");
                }
            }
            return list.toString();
        }
        return list.toString();
    }

    /**
     * This method lists all education apps.
     *
     * @return a String that either tells the user no education apps have been stored yet or lists all stored education apps.
     */
    public String listAllEducationApps() {
        StringBuilder list = new StringBuilder("No Education apps");
        if (!apps.isEmpty()) {
            list = new StringBuilder("List of every Education App: " + "\n");
            for (App app : apps) {
                if (app instanceof EducationApp) {
                    list.append("Index: ").append(getAppIndex(app.getAppName())).append(" ").append(app).append("\n");
                }
            }
            return list.toString();
        }
        return list.toString();
    }

    /**
     * This method lists all productivity apps.
     *
     * @return a String that either tells the user no apps have been stored yet or lists all stored productivity apps.
     */
    public String listAllProductivityApps() {
        StringBuilder list = new StringBuilder("No Productivity apps");
        if (!apps.isEmpty()) {
            list = new StringBuilder("List of every Productivity App: " + "\n");
            for (App app : apps) {
                if (app instanceof ProductivityApp) {
                    list.append("Index: ").append(getAppIndex(app.getAppName())).append(" ").append(app).append("\n");
                }
            }
            return list.toString();
        }
        return list.toString();
    }

    /**
     * This method lists all apps containing a specific name.
     *
     * @param name apps containing this string will be returned.
     * @return a String that either tells the user no apps have been stored yet or lists all stored apps that contain the searched string.
     */
    public String listAllAppsByName(String name) {
        StringBuilder list = new StringBuilder("No apps of this name.");
        if (!apps.isEmpty()) {
            list = new StringBuilder("List of every App with this name: " + "\n");
            for (App app : apps) {
                if (app.getAppName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT))) {
                    int i = 0;
                    i++;
                    list.append("Index: ").append(i).append(" ").append(app).append("\n");
                }
            }
            return list.toString();
        }
        return list.toString();
    }

    /**
     * This method lists all apps containing a specific name.
     *
     * @param rating apps with a rating equal to or higher than this int will be returned
     * @return a String that either tells the user no apps have been stored yet or lists all stored apps with a rating equal to or higher than the requested rating.
     */
    public String listAllAppsAboveOrEqualAGivenStarRating(int rating) {
        StringBuilder list = new StringBuilder("No apps have a rating of " + rating + " or above.");
        if (!apps.isEmpty()) {
            list = new StringBuilder("List of every App with this rating or higher: " + "\n");
            for (App app : apps) {
                if (app.calculateRating() >= rating) {
                    int i = 0;
                    i++;
                    list.append("Index: ").append(i).append(" ").append(app).append("\n");
                }
            }
            return list.toString();
        }
        return list.toString();
    }

    /**
     * This method returns the index of an app containing a specific name.
     *
     * @param appName name of the apps of which the index will be found
     * @return the index of the app that was searched for
     */
    public int getAppIndex(String appName) {
        for (App app : apps) {
            if (app.getAppName().equalsIgnoreCase(appName)) {
                return apps.indexOf(app);
            }
        }
        return -1;
    }

    /**
     * This method lists all recommended apps regardless of app type.
     *
     * @return a String that either tells the user no recommended apps have been stored yet or lists the summary of all stored recommended apps.
     */
    public String listAllRecommendedApps() {
        StringBuilder list = new StringBuilder("No recommended apps");
        if (!apps.isEmpty()) {
            for (App app : apps) {
                if (app.isRecommendedApp()) {
                    list = new StringBuilder("Recommended Apps: \n");
                    list.append("Index: ").append(getAppIndex(app.getAppName())).append(" ").append(app.appSummary()).append("\n");
                }
            }
            return list.toString();
        }
        return list.toString();
    }

    /**
     * This method lists all recommended apps regardless of app type.
     *
     * @return a String that either tells the user no recommended apps have been stored yet or lists the summary of all stored recommended Game apps.
     */
    public String listAllRecommendedGameApps() {
        StringBuilder list = new StringBuilder("No recommended apps.");
        if (!apps.isEmpty()) {
            list = new StringBuilder("List of every recommended App: " + "\n");
            for (App app : apps) {
                if (app.isRecommendedApp() && app instanceof GameApp) {
                    list = new StringBuilder("Recommended Apps: \n");
                    list.append("Index: ").append(getAppIndex(app.getAppName())).append(" ").append(app.appSummary()).append("\n");
                }
            }
            return list.toString();
        }
        return list.toString();
    }

    /**
     * This method lists all recommended apps regardless of app type.
     *
     * @return a String that either tells the user no recommended apps have been stored yet or lists the summary of all stored recommended Education apps.
     */
    public String listAllRecommendedEducationApps() {
        StringBuilder list = new StringBuilder("No recommended apps.");
        if (!apps.isEmpty()) {
            list = new StringBuilder("List of every recommended App: " + "\n");
            for (App app : apps) {
                if (app.isRecommendedApp() && app instanceof EducationApp) {
                    list = new StringBuilder("Recommended Apps: \n");
                    list.append("Index: ").append(getAppIndex(app.getAppName())).append(" ").append(app.appSummary()).append("\n");
                }
            }
            return list.toString();
        }
        return list.toString();
    }

    /**
     * This method lists all recommended apps regardless of app type.
     *
     * @return a String that either tells the user no recommended apps have been stored yet or lists the summary of all stored recommended Productivity apps.
     */
    public String listAllRecommendedProductivityApps() {
        StringBuilder list = new StringBuilder("No recommended apps.");
        if (!apps.isEmpty()) {
            list = new StringBuilder("List of every recommended App: " + "\n");
            for (App app : apps) {
                if (app.isRecommendedApp() && app instanceof ProductivityApp) {
                    list = new StringBuilder("Recommended Apps: \n");
                    list.append("Index: ").append(getAppIndex(app.getAppName())).append(" ").append(app.appSummary()).append("\n");
                }
            }
            return list.toString();
        }
        return list.toString();
    }

    /**
     * This method returns a list of apps by a specific developer.
     *
     * @param developer the developer whose apps will be searched for.
     * @return a String containing all apps by the developer.
     */
    public String listAllAppsByChosenDeveloper(Developer developer) {
        StringBuilder list = new StringBuilder("No apps for developer: " + developer);
        if (!apps.isEmpty()) {
            list = new StringBuilder("List of every App from this developer: " + "\n");
            for (App app : apps) {
                if (app.getDeveloper().equals(developer)) {
                    int i = 0;
                    i++;
                    list.append("Index: ").append(i).append(" ").append(app).append("\n");
                }
            }
            return list.toString();
        }
        return list.toString();
    }

    /**
     * This method returns the amount of apps made by a specific developer.
     *
     * @param developer the developer whose apps will be searched for.
     * @return the number of apps made by the developer.
     */
    public int numberOfAppsByChosenDeveloper(Developer developer) {
        int numberOfApps = 0;
        for (App app : apps) {
            if (app.getDeveloper().equals(developer)) {
                numberOfApps++;
            }
        }
        return numberOfApps;
    }

    /**
     * This method returns a random app.
     *
     * @return a random stored app
     */
    public App randomApp() {
        int random = (int) (Math.random() * apps.size());
        return apps.get(random);
    }

    /**
     * This method sorts all apps by name, alphabetically ascending
     */
    public void sortAppsByNameAscending() {
        for (int i = apps.size() - 1; i >= 0; i--) {
            int highestIndex = 0;
            for (int z = 0; z <= i; z++) {
                if (apps.get(z).getAppName().compareTo(apps.get(highestIndex).getAppName()) > 0) {
                    highestIndex = z;
                }
            }
            swapApps(apps, i, highestIndex);
        }
    }

    /**
     * This method swaps the requested apps in the array list. This method is used in the sorting method.
     * @param apps    The array list of the apps that will be swapped
     * @param current the first app that will be swapped
     * @param highest the second app that will be swapped
     */
    public void swapApps(List<App> apps, int current, int highest) {
        App smaller = apps.get(current);
        App bigger = apps.get(highest);
        apps.set(current, bigger);
        apps.set(highest, smaller);
    }

    //---------------------
    // Method to simulate ratings (using the RatingUtility).
    // This will be called from the Driver (see skeleton code)
    //---------------------

    public void simulateRatings() {
        for (App app : apps) {
            app.addRating(generateRandomRating());
        }
    }

    //---------------------
    // Validation methods
    //---------------------

    public boolean isValidIndex(int index) {
        return (index >= 0) && (index < apps.size());
    }

    //---------------------
    // Persistence methods
    //---------------------

    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{App.class, EducationApp.class, GameApp.class, ProductivityApp.class, Rating.class};

        //setting up the x stream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(fileName()));
        apps = (List<App>) in.readObject();
        in.close();
    }

    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(fileName()));
        out.writeObject(apps);
        out.close();
    }

    public String fileName() {
        return "apps.xml";
    }

}
