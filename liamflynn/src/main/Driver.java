package main;

import controllers.AppStoreAPI;
import controllers.DeveloperAPI;
import models.*;
import utils.ScannerInput;
import static utils.Utilities.YNtoBoolean;

/**
 * The Driver class runs the main menu of the program, facilitating the many menus for the different operations.
 * These menus are outputting my printing them and are navigated via the user inputting numbers to choose options.
 * There are several menus:
 *  -Main Menu: Allows the user to access the Developer Management menu ,
 *      App Management Menu, Reports Menu and Search Functions.
 *      Users can also sort apps alphabetically, view Recommended Apps,
 *      view a "Random App of the Day", simulate ratings for all apps
 *      and save/load the program.
 *
 *  -Developer Management Menu: Allows the user to add developers, update developers,
 *      delete developers and view all developers.
 *
 *  -Reports Menu: Allows the user to view Overviews of all the Apps and Developers
 *      added to the program (As the Apps assigned toString)
 *
 * @author Liam FLynn
 * @version 1.0
 */
public class Driver {

    private final DeveloperAPI developerAPI = new DeveloperAPI();
    private final AppStoreAPI appStoreAPI = new AppStoreAPI();

    public static void main(String[] args) {
        new Driver().start();
    }

    public void start() {
        //loadAllData();
        runMainMenu();
    }

    private int mainMenu() {
        System.out.println("""
                 -------------App Store------------
                |  1) Developer - Management MENU  |
                |  2) App - Management MENU        |
                |  3) Reports MENU                 |
                |----------------------------------|
                |  4) Search                       |
                |  5) Sort                         |
                |----------------------------------|
                |  6) Recommended Apps             |
                |  7) Random App of the Day        |
                |  8) Simulate ratings             |
                |----------------------------------|
                |  20) Save all                    |
                |  21) Load all                    |
                |----------------------------------|
                |  0) Exit                         |
                 ----------------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runMainMenu() {
        int option = mainMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> runDeveloperMenu();
                case 2 -> runAppMenu();
                case 3 -> runReportMenu();
                case 4 -> searchAppsBySpecificCriteria();
                case 5 -> sortAppsByName();
                case 6 -> runRecommendedAppsMenu();
                case 7 -> randomAppOfTheDay();
                case 8 -> simulateRatings();
                case 20 -> saveAllData();
                case 21 -> loadAllData();
                default -> System.out.println("Invalid option entered: " + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = mainMenu();
        }
        exitApp();
    }

    private void exitApp() {
        //saveAllData();
        System.out.println("Exiting....");
        System.exit(0);
    }

    //--------------------------------------------------
    //  Developer Management - Menu Items
    //--------------------------------------------------
    private int developerMenu() {
        System.out.println("""
                 -------Developer Menu-------
                |   1) Add a developer       |
                |   2) List developer        |
                |   3) Update developer      |
                |   4) Delete developer      |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runDeveloperMenu() {
        int option = developerMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addDeveloper();
                case 2 -> System.out.println(developerAPI.listDevelopers());
                case 3 -> updateDeveloper();
                case 4 -> deleteDeveloper();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = developerMenu();
        }
    }

    private void addDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        String developerWebsite = ScannerInput.validNextLine("Please enter the developer website: ");

        if (developerAPI.addDeveloper(new Developer(developerName, developerWebsite))) {
            System.out.println("Add successful");
        } else {
            System.out.println("Add not successful");
        }
    }

    private void updateDeveloper() {
        System.out.println(developerAPI.listDevelopers());
        Developer developer = readValidDeveloperByName();
        if (developer != null) {
            String developerWebsite = ScannerInput.validNextLine("Please enter new website: ");
            if (developerAPI.updateDeveloperWebsite(developer.getDeveloperName(), developerWebsite))
                System.out.println("Developer Website Updated");
            else
                System.out.println("Developer Website NOT Updated");
        } else
            System.out.println("Developer name is NOT valid");
    }

    private void deleteDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        if (developerAPI.removeDeveloper(developerName) != null) {
            System.out.println("Delete successful");
        } else {
            System.out.println("Delete not successful");
        }
    }

    private Developer readValidDeveloperByName() {
        String developerName = ScannerInput.validNextLine("Please enter the developer's name: ");
        if (developerAPI.isValidDeveloper(developerName)) {
            return developerAPI.getDeveloperByName(developerName);
        } else {
            return null;
        }
    }

    //--------------------------------------------------
    //  App Management - Menu Items
    //--------------------------------------------------
    private int appMenu() {
        System.out.println("""
                 --------App Store Menu-------
                |   1) Add an app            |
                |   2) Update apps           |
                |   3) Delete apps           |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runAppMenu() {
        int option = appMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addApp();
                case 2 -> updateApp();
                case 3 -> deleteApp();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = appMenu();
        }
    }

    /**
     * This method creates an app object using values that the program user submits.
     * The app is added to the App array in AppStoreAPI.
     * All types of Apps (Education, Game and Productivity) are accounted for.
     */

    private void addApp() {
        String appDeveloper = ScannerInput.validNextLine("Please enter the name of the developer of the app: ");
        if (developerAPI.getDeveloperByName(appDeveloper) == null) {
            System.out.println("No developer of this name is registered yet. \n App register unsuccessful.");
        } else {
            String appType = ScannerInput.validNextLine("Please enter what type of App this is (Education/Game/Productivity):");
            if (!appType.equalsIgnoreCase("Education") && !appType.equalsIgnoreCase("Game") && !appType.equalsIgnoreCase("Productivity")) {
                System.out.println("This is not a valid app type.");
            } else {
                String appName = ScannerInput.validNextLine("Please enter the name of the App:");
                double appSize = ScannerInput.validNextDouble("Please enter the App Size in MB (Max 1000 MB):");
                double appVersion = ScannerInput.validNextDouble("Please enter the App Version:");
                double appCost = ScannerInput.validNextDouble("Please enter the Cost of the App:");

                if (appType.equalsIgnoreCase("Education")) {
                    int level = ScannerInput.validNextInt("Please enter the level of the App:");
                    Developer dev = developerAPI.getDeveloperByName(appDeveloper);
                    App newApp = new EducationApp(dev, appName, appSize, appVersion, appCost, level);
                    if (appStoreAPI.addApp(newApp)) {
                        System.out.println("Education App " + appName + " registered successfully.");
                    }
                }

                if (appType.equalsIgnoreCase("Game")) {
                    char yn = ScannerInput.validNextChar("Is this Game App multiplayer? (Y/N):");
                    if (YNtoBoolean(yn)) {
                        Developer dev = developerAPI.getDeveloperByName(appDeveloper);
                        App newApp = new GameApp(dev, appName, appSize, appVersion, appCost, utils.Utilities.YNtoBoolean(yn));
                        appStoreAPI.addApp(newApp);
                        System.out.println("Game App " + appName + " registered successfully.");
                    }
                }

                if (appType.equalsIgnoreCase("Productivity")) {
                    Developer dev = developerAPI.getDeveloperByName(appDeveloper);
                    App newApp = new ProductivityApp(dev, appName, appSize, appVersion, appCost);
                    if (appStoreAPI.addApp(newApp)) {
                        System.out.println("Productivity App " + appName + " registered successfully.");
                    }
                }
            }
        }
    }

    /**
     * This method updates an existing app object using new values that the program user submits.
     * The user first chooses what type of app they want to update (Education, Productivity, Game).
     *The values of the app objects are changed in their respective arrays.
     * Non - existing apps are accounted for ie. if no education app exits the program will tell the user and return.
     */
    private void updateApp() {
        if (appStoreAPI.numberOfApps() != 0) {
            boolean isUpdated = false;

            int option = ScannerInput.validNextInt("""
                    -----------------------------------
                    |   1) Update an Education App    |
                    |   2) Update a Game App          |
                    |   3) Update a Productivity App  |
                    -----------------------------------
                    ==>>""");

            switch (option) {
                case 1 -> {
                    if (appStoreAPI.numberOfEducationApps() == 0) {
                        System.out.println("No Education Apps Registered Yet!");
                        return;
                    }
                    appStoreAPI.listAllEducationApps();
                    int edIndex = ScannerInput.validNextInt("Enter the index number of the education app you wish to update:");
                    if (!appStoreAPI.isValidIndex(edIndex)) {
                        System.out.println("There is no education app registered to this index number");
                        return;
                    }
                    String appDeveloper = ScannerInput.validNextLine("Please enter the new developer of the app: ");
                    if (!developerAPI.isValidDeveloper(appDeveloper)) {
                        System.out.println("Developer not registered.");
                        return;
                    }
                    Developer dev = developerAPI.getDeveloperByName(appDeveloper);
                    String appName = ScannerInput.validNextLine("Please enter the new name of the App:");
                    double appSize = ScannerInput.validNextDouble("Please enter the new App Size in MB (Max 1000 MB):");
                    double appVersion = ScannerInput.validNextDouble("Please enter the new App Version:");
                    double appCost = ScannerInput.validNextDouble("Please enter the new Cost of the App:");
                    int level = ScannerInput.validNextChar("Please enter the new Level of the App:");
                    isUpdated = appStoreAPI.updateEducationApp(edIndex, dev, appName, appSize, appVersion, appCost, level);
                }
                case 2 -> {
                    if (appStoreAPI.numberOfGameApps() == 0) {
                        System.out.println("No Game Apps Registered Yet!");
                        return;
                    }
                    appStoreAPI.listAllGameApps();
                    int gameIndex = ScannerInput.validNextInt("Enter the index number of the game app you wish to update:");
                    if (!appStoreAPI.isValidIndex(gameIndex)) {
                        System.out.println("There is no game app registered to this index number");
                        return;
                    }
                    String appDeveloper = ScannerInput.validNextLine("Please enter the new developer of the app: ");
                    if (!developerAPI.isValidDeveloper(appDeveloper)) {
                        System.out.println("Developer not registered.");
                        return;
                    }
                    Developer dev = developerAPI.getDeveloperByName(appDeveloper);
                    String appName = ScannerInput.validNextLine("Please enter the new name of the App:");
                    double appSize = ScannerInput.validNextDouble("Please enter the new App Size in MB (Max 1000 MB):");
                    double appVersion = ScannerInput.validNextDouble("Please enter the new App Version:");
                    double appCost = ScannerInput.validNextDouble("Please enter the new Cost of the App:");
                    char yn = ScannerInput.validNextChar("Is this Game App multiplayer? (Y/N):");
                    isUpdated = appStoreAPI.updateGameApp(gameIndex, dev, appName, appSize, appVersion, appCost, YNtoBoolean(yn));
                }

                case 3 -> {
                    if (appStoreAPI.numberOfProductivityApps() == 0) {
                        System.out.println("No Productivity Apps Registered Yet!");
                        return;
                    }
                    appStoreAPI.listAllProductivityApps();
                    int prodIndex = ScannerInput.validNextInt("Enter the index number of the productivity app you wish to update:");
                    if (!appStoreAPI.isValidIndex(prodIndex)) {
                        System.out.println("There is no productivity app registered to this index number");
                        return;
                    }
                    String appDeveloper = ScannerInput.validNextLine("Please enter the new developer of the app: ");
                    if (!developerAPI.isValidDeveloper(appDeveloper)) {
                        System.out.println("Developer not registered.");
                        return;
                    }
                    Developer dev = developerAPI.getDeveloperByName(appDeveloper);
                    String appName = ScannerInput.validNextLine("Please enter the new name of the App:");
                    double appSize = ScannerInput.validNextDouble("Please enter the new App Size in MB (Max 1000 MB):");
                    double appVersion = ScannerInput.validNextDouble("Please enter the new App Version:");
                    double appCost = ScannerInput.validNextDouble("Please enter the new Cost of the App:");
                    isUpdated = appStoreAPI.updateProductivityApp(prodIndex, dev, appName, appSize, appVersion, appCost);
                }
                default -> System.out.println("Invalid option entered: " + option);
            }
            if (isUpdated) {
                System.out.println("App Updated Successfully");
            } else {
                System.out.println("No App Updated");
            }
            return;
        }
        System.out.println("No Apps Registered Yet");
    }

    /**
     * This method deletes an existing app object that the program user chooses.
     * The app is removed from the App array in AppStoreAPI.
     * If the app is deleted successfully the user is notified.
     * If the app is not deleted successfully the user is also notified.
     */
    private void deleteApp() {
        System.out.println(appStoreAPI.listAllApps());
        if (appStoreAPI.numberOfApps() > 0) {
            int indexToDelete = ScannerInput.validNextInt("Enter the index of the app you want delete ==> ");
            App appToDelete = appStoreAPI.deleteAppByIndex(indexToDelete);
            if (appToDelete != null) {
                System.out.println("Delete Successful! Deleted post: " + appToDelete.appSummary());
            } else {
                System.out.println("Delete NOT Successful");
            }
        }
    }

    //--------------------------------------------------
    //  Reports Management - Menu Items
    //--------------------------------------------------
    /**
     * The report menu allows users to view apps in lists,
     * search for apps with specific values (name/developer/rating),
     * and see how many apps developers have created.
     */
    private int reportMenu() {
        System.out.println("""
                 ---------Reports Menu--------
                |   1) Apps Overview         |
                |   2) Developers Overview   |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runReportMenu() {
        int option = reportMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> appsOverview();
                case 2 -> developersOverview();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = reportMenu();
        }
    }
    /**
     * This method returns all apps, seperated into Game Apps, Education Apps and Productivity Apps as a list.
     */
    private void appsOverview() {
        System.out.println(appStoreAPI.numberOfGameApps() + "Game App(s):" + "\n" + appStoreAPI.listAllGameApps() + "\n");
        System.out.println("-----------------------------------------");
        System.out.println(appStoreAPI.numberOfEducationApps() + " Education App(s):" + "\n" + appStoreAPI.listAllEducationApps() + "\n");
        System.out.println("-----------------------------------------");
        System.out.println(appStoreAPI.numberOfProductivityApps() + " Productivity App(s):" + "\n" + appStoreAPI.listAllProductivityApps() + "\n");
        System.out.println("-----------------------------------------");
    }
    /**
     * This method returns a list of all registered developers.
     */
    private void developersOverview() {
        System.out.println("List of registered developers: \n" + developerAPI.listDevelopers());
        System.out.println("-----------------------------------------");
    }

    //--------------------------------------------------

    //--------------------------------------------------
    /**
     * This method allows users to search for specific apps using certain criteria:
     * app name, developer name, rating.
     * It also allows users to see the total number of apps a developer has made.
     * It returns a list of apps that match the criteria.
     */
    private void searchAppsBySpecificCriteria() {
        System.out.println("""
                What criteria would you like to search by:
                  1) App Name
                  2) Developer Name
                  3) Rating (all apps of that rating or above)
                  4) Number of Apps by a Specific Developer
                  """);
        int option = ScannerInput.validNextInt("==>> ");
        switch (option) {
            case 1 -> searchAppsByName();
            case 2 -> searchAppsByDeveloper();
            case 3 -> searchAppsEqualOrAboveAStarRating();
            case 4 -> numberOfAppsByChosenDeveloper();
            default -> System.out.println("Invalid option");
        }
    }

    private void numberOfAppsByChosenDeveloper() {
        if (appStoreAPI.numberOfApps() > 0) {
            System.out.println(appStoreAPI.numberOfAppsByChosenDeveloper(developerAPI.getDeveloperByName(ScannerInput.validNextLine("Enter the name of a developer:"))));
        } else {
            System.out.println("No Apps Registered Yet");
        }
    }

    private void searchAppsByName() {
        if (appStoreAPI.numberOfApps() > 0) {
            String appName = ScannerInput.validNextLine("Enter the name of the app you're looking for:");
            appStoreAPI.listAllAppsByName(appName);
            if (appStoreAPI.listAllAppsByName(appName) == null) {
                System.out.println("No apps with that name registered currently.");
            }
        }
        System.out.println("No Apps Registered Yet");
    }

    private void searchAppsByDeveloper() {
        if (appStoreAPI.numberOfApps() > 0) {
            String appDeveloper = ScannerInput.validNextLine("Enter the name of the developer whose apps you're looking for:");
            appStoreAPI.listAllAppsByChosenDeveloper(developerAPI.getDeveloperByName(appDeveloper));
            if (appStoreAPI.listAllAppsByChosenDeveloper(developerAPI.getDeveloperByName(appDeveloper)) == null) {
                System.out.println("No apps by that developer registered currently.");
            }
        }
        System.out.println("Developer not registered yet.");
    }

    private void searchAppsEqualOrAboveAStarRating() {
        if (appStoreAPI.numberOfApps() > 0) {
            int rating = ScannerInput.validNextInt("Enter the lowest possible star rating for apps you want to see:");
            appStoreAPI.listAllAppsAboveOrEqualAGivenStarRating(rating);
            if (appStoreAPI.listAllAppsAboveOrEqualAGivenStarRating(rating) == null) {
                System.out.println("No apps of that rating or higher registered currently.");
            }
        }
        System.out.println("No apps registered yet.");
    }

    private void sortAppsByName() {
        if (appStoreAPI.numberOfApps() > 0) {
            appStoreAPI.listAllApps();
            int choice = ScannerInput.validNextInt("Press 1 to sort this list alphabetically , press 0 to exit):");
            if (choice == 1) {
                appStoreAPI.sortAppsByNameAscending();
                System.out.println("New list of apps: \n" + appStoreAPI.listAllApps());
            }
        }
        System.out.println("No apps registered yet.");
    }
    /**
     * This method recommends users apps depending on what type of app they want to be recommended.
     * The qualifications for being "recommended" are different depending on the app type.
     */
    private void runRecommendedAppsMenu() {
        System.out.println("""
                What apps would you like to be recommended:
                  1) All apps
                  2) Game Apps
                  3) Education Apps
                  4) Productivity Apps""");
        int option = ScannerInput.validNextInt("==>> ");
        switch (option) {
            case 1 -> printRecommendedApps();
            case 2 -> printRecommendedGameApps();
            case 3 -> printRecommendedEducationApps();
            case 4 -> printRecommendedProductivityApps();
            default -> System.out.println("Invalid option");
        }
    }
    /**
     * This method prints recommended apps of all types.
     * Whether an app is recommended is decided by different criteria depending on the app type (education, game, productivity).
     */
    private void printRecommendedApps() {
        if (appStoreAPI.numberOfApps() > 0) {
            System.out.println("All recommended Apps: \n" + appStoreAPI.listAllRecommendedApps());
        } else {
            System.out.println("No apps registered yet.");
        }
    }

    private void printRecommendedGameApps() {
        if (appStoreAPI.numberOfApps() > 0) {
            System.out.println("All recommended Game Apps: \n" + appStoreAPI.listAllRecommendedGameApps());
        } else {
            System.out.println("No game apps registered yet.");
        }
    }

    private void printRecommendedEducationApps() {
        if (appStoreAPI.numberOfApps() > 0) {
            System.out.println("All recommended Education Apps: \n" + appStoreAPI.listAllRecommendedEducationApps());
        } else {
            System.out.println("No education apps registered yet.");
        }
    }

    private void printRecommendedProductivityApps() {
        if (appStoreAPI.numberOfApps() > 0) {
            System.out.println("All recommended Game Apps: \n" + appStoreAPI.listAllRecommendedProductivityApps());
        }
        System.out.println("No productivity apps registered yet.");
    }

    private void randomAppOfTheDay() {
        if (appStoreAPI.numberOfApps() > 0) {
            System.out.println("Random App of the day: \n" + appStoreAPI.randomApp().appSummary());
        }
        System.out.println("No apps registered yet.");
    }

    //--------------------------------------------------
    //    Simulate Ratings
    //--------------------------------------------------

    /**
    *This method simulates random ratings for all apps (to give data for recommended apps and reports etc).
    */
    private void simulateRatings() {

        if (appStoreAPI.numberOfApps() > 0) {
            System.out.println("Simulating ratings...");
            appStoreAPI.simulateRatings();
            System.out.println(appStoreAPI.listSummaryOfAllApps());
        } else {
            System.out.println("No apps");
        }
    }

    //--------------------------------------------------
    //  Persistence Menu Items
    //--------------------------------------------------

    private void saveAllData() {
        try {
            appStoreAPI.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            developerAPI.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAllData() {
        try {
            appStoreAPI.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            developerAPI.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}