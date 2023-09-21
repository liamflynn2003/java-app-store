package controllers;

import models.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppStoreAPITest {

    private EducationApp edAppBelowBoundary, edAppOnBoundary, edAppAboveBoundary, edAppInvalidData;
    private ProductivityApp prodAppBelowBoundary, prodAppOnBoundary, prodAppAboveBoundary, prodAppInvalidData;
    private GameApp gameAppBelowBoundary, gameAppOnBoundary, gameAppAboveBoundary, gameAppInvalidData;

    private Developer developerLego = new Developer("Lego", "www.lego.com");
    private Developer developerSphero = new Developer("Sphero", "www.sphero.com");
    private Developer developerEAGames = new Developer("EA Games", "www.eagames.com");
    private Developer developerKoolGames = new Developer("Kool Games", "www.koolgames.com");
    private Developer developerApple = new Developer("Apple", "www.apple.com");
    private Developer developerMicrosoft = new Developer("Microsoft", "www.microsoft.com");

    private AppStoreAPI appStore = new AppStoreAPI();
    private AppStoreAPI emptyAppStore = new AppStoreAPI();

    @BeforeEach
    void setUp() {

        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0), level(1-10).
        edAppBelowBoundary = new EducationApp(developerLego, "WeDo", 1, 1.0, 0,  1);

        edAppOnBoundary = new EducationApp(developerLego, "Spike", 1000, 2.0,
                1.99, 10);

        edAppAboveBoundary = new EducationApp(developerLego, "EV3", 1001, 3.5,  2.99,  11);

        edAppInvalidData = new EducationApp(developerLego, "", -1, 0, -1.00,  0);


        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0),
        prodAppBelowBoundary = new ProductivityApp(developerApple, "NoteKeeper", 1, 1.0, 0.0);

        prodAppOnBoundary = new ProductivityApp(developerMicrosoft, "Outlook", 1000, 2.0, 1.99);

        prodAppAboveBoundary = new ProductivityApp(developerApple, "Pages", 1001, 3.5, 2.99);

        prodAppInvalidData = new ProductivityApp(developerMicrosoft, "", -1, 0, -1.00);


        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0),
        gameAppBelowBoundary = new GameApp(developerEAGames, "Tetris", 1, 1.0, 0.0,  false);

        gameAppOnBoundary = new GameApp(developerKoolGames, "CookOff", 1000, 2.0, 1.99,  true);

        gameAppAboveBoundary = new GameApp(developerEAGames, "Empires", 1001, 3.5,  2.99, false);

        gameAppInvalidData = new GameApp(developerKoolGames, "", -1, 0,  -1.00,  true);

        //not included - edAppOnBoundary, edAppInvalidData, prodAppBelowBoundary, gameAppBelowBoundary, gameAppInvalidData.
        appStore.addApp(edAppBelowBoundary);
        appStore.addApp(prodAppOnBoundary);
        appStore.addApp(gameAppAboveBoundary);
        appStore.addApp(prodAppBelowBoundary);
        appStore.addApp(edAppAboveBoundary);
        appStore.addApp(prodAppInvalidData);
        appStore.addApp(gameAppOnBoundary);
    }

    @AfterEach
    void tearDown() {
        edAppBelowBoundary = edAppOnBoundary = edAppAboveBoundary = edAppInvalidData = null;
        gameAppBelowBoundary = gameAppOnBoundary = gameAppAboveBoundary = gameAppInvalidData = null;
        prodAppBelowBoundary = prodAppOnBoundary = prodAppAboveBoundary = prodAppInvalidData = null;
        developerApple = developerEAGames = developerKoolGames = developerLego = developerMicrosoft = null;
        appStore = emptyAppStore = null;
    }

    @Nested
    class GettersAndSetters {

    }

    @Nested
    class CRUDMethods {

    }

    @Nested
    class ListingMethods {

        @Test
        void listAllAppsReturnsNoAppsStoredWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listAllApps().toLowerCase().contains("no apps"));
        }

        @Test
        void listAllAppsReturnsAppsStoredWhenArrayListHasAppsStored() {
            assertEquals(7, appStore.numberOfApps());
            String apps = appStore.listAllApps();
            //checks for objects in the string
            assertTrue(apps.contains("WeDo"));
            assertTrue(apps.contains("Outlook"));
            assertTrue(apps.contains("Empires"));
            assertTrue(apps.contains("NoteKeeper"));
            assertTrue(apps.contains("EV3"));
            assertTrue(apps.contains("CookOff"));
        }

        @Test
        void listRecommendedAppsReturnsNoAppsWhenRecommendedAppsDoNotExist() {
            assertEquals(7, appStore.numberOfApps());

            String apps = appStore.listAllRecommendedApps();
            //checks for the three objects in the string
            assertTrue(apps.contains("No recommended apps"));
        }

        @Test
        void listRecommendedAppsReturnsRecommendedAppsWhenTheyExist() {
            assertEquals(7, appStore.numberOfApps());

            //adding recommended apps to the list
            appStore.addApp(setupGameAppWithRating(5,5));
            appStore.addApp(setupEducationAppWithRating(3,4));
            appStore.addApp(setupProductivityAppWithRating(3,4));
            assertEquals(10, appStore.numberOfApps());

            String apps = appStore.listAllRecommendedApps();
            System.out.println(apps);
            //checks for the three objects in the string
            assertTrue(apps.contains("MazeRunner"));
            assertTrue(apps.contains("Evernote"));
            assertTrue(apps.contains("WeDo"));
        }


    }

    @Nested
    class ReportingMethods {

    }

    @Nested
    class SearchingMethods {

    }

    @Nested
    class SortingMethods {

        @Test
        void sortByNameAscendingReOrdersList() {
            assertEquals(7, appStore.numberOfApps());
            //checks the order of the objects in the list
            assertEquals(edAppBelowBoundary, appStore.getAppByIndex(0));
            assertEquals(prodAppOnBoundary, appStore.getAppByIndex(1));
            assertEquals(gameAppAboveBoundary, appStore.getAppByIndex(2));
            assertEquals(prodAppBelowBoundary, appStore.getAppByIndex(3));
            assertEquals(edAppAboveBoundary, appStore.getAppByIndex(4));
            assertEquals(prodAppInvalidData, appStore.getAppByIndex(5));
            assertEquals(gameAppOnBoundary, appStore.getAppByIndex(6));

            appStore.sortAppsByNameAscending();
            assertEquals(prodAppInvalidData, appStore.getAppByIndex(0));
            assertEquals(gameAppOnBoundary, appStore.getAppByIndex(1));
            assertEquals(edAppAboveBoundary, appStore.getAppByIndex(2));
            assertEquals(gameAppAboveBoundary, appStore.getAppByIndex(3));
            assertEquals(prodAppBelowBoundary, appStore.getAppByIndex(4));
            assertEquals(prodAppOnBoundary, appStore.getAppByIndex(5));
            assertEquals(edAppBelowBoundary, appStore.getAppByIndex(6));
        }

        @Test
        void sortByNameAscendingDoesntCrashWhenListIsEmpty() {
            assertEquals(0,emptyAppStore.numberOfApps());
            emptyAppStore.sortAppsByNameAscending();
        }

    }

    //--------------------------------------------
    // Helper Methods
    //--------------------------------------------
    EducationApp setupEducationAppWithRating(int rating1, int rating2) {
        //setting all conditions to true
        EducationApp edApp = new EducationApp(developerLego, "WeDo", 1,
                1.0, 1.00, 3);
        edApp.addRating(new Rating(rating1, "John Doe", "Very Good"));
        edApp.addRating(new Rating(rating2, "Jane Doe", "Excellent"));

        return edApp;
    }

    GameApp setupGameAppWithRating(int rating1, int rating2) {
        GameApp gameApp = new GameApp(developerEAGames, "MazeRunner", 1,
                1.0, 1.00, false);
        gameApp.addRating(new Rating(rating1, "John Soap", "Exciting Game"));
        gameApp.addRating(new Rating(rating2, "Jane Soap", "Nice Game"));
        return gameApp;
    }

    ProductivityApp setupProductivityAppWithRating(int rating1, int rating2) {
        ProductivityApp productivityApp = new ProductivityApp(developerApple, "Evernote", 1,
                1.0, 1.99);

        productivityApp.addRating(new Rating(rating1, "John101", "So easy to add a note"));
        productivityApp.addRating(new Rating(rating2, "Jane202", "So useful"));
        return productivityApp;
    }

}