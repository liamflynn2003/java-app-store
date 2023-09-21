package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameAppTest {

    private GameApp gmAppBelowBoundary, gmAppOnBoundary, gmAppAboveBoundary, gmAppInvalidData;
    private Developer developerLego = new Developer("Lego", "www.lego.com");
    private Developer developerSphero = new Developer("Sphero", "www.sphero.com");

    @BeforeEach
    void setUp() {
        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0), multiplayer(true/false).
        gmAppBelowBoundary = new GameApp(developerLego, "WeDo", 1, 1.0, 0, true);
        gmAppOnBoundary = new GameApp(developerLego, "Spike", 1000, 2.0, 1.99, false);
        gmAppAboveBoundary = new GameApp(developerLego, "EV3", 1001, 3.5, 2.99, true);
        gmAppInvalidData = new GameApp(developerLego, "", -1, 0, -1.00, false);
    }

    @AfterEach
    void tearDown() {
        gmAppBelowBoundary = gmAppOnBoundary = gmAppAboveBoundary = gmAppInvalidData = null;
        developerLego = developerSphero = null;
    }

    @Nested
    class Getters {
        @Test
        void getDeveloper() {
            assertEquals(developerLego, gmAppBelowBoundary.getDeveloper());
            assertEquals(developerLego, gmAppOnBoundary.getDeveloper());
            assertEquals(developerLego, gmAppAboveBoundary.getDeveloper());
            assertEquals(developerLego, gmAppInvalidData.getDeveloper());
        }

        @Test
        void getAppName() {
            assertEquals("WeDo", gmAppBelowBoundary.getAppName());
            assertEquals("Spike", gmAppOnBoundary.getAppName());
            assertEquals("EV3", gmAppAboveBoundary.getAppName());
            assertEquals("", gmAppInvalidData.getAppName());
        }

        @Test
        void getAppSize() {
            assertEquals(1, gmAppBelowBoundary.getAppSize());
            assertEquals(1000, gmAppOnBoundary.getAppSize());
            assertEquals(0, gmAppAboveBoundary.getAppSize());
            assertEquals(0, gmAppInvalidData.getAppSize());
        }

        @Test
        void getAppVersion() {
            assertEquals(1.0, gmAppBelowBoundary.getAppVersion());
            assertEquals(2.0, gmAppOnBoundary.getAppVersion());
            assertEquals(3.5, gmAppAboveBoundary.getAppVersion());
            assertEquals(1.0, gmAppInvalidData.getAppVersion());
        }

        @Test
        void getAppCost() {
            assertEquals(0, gmAppBelowBoundary.getAppCost());
            assertEquals(1.99, gmAppOnBoundary.getAppCost());
            assertEquals(2.99, gmAppAboveBoundary.getAppCost());
            assertEquals(0, gmAppInvalidData.getAppCost());
        }

        @Test
        void isMultiplayer() {
            assertTrue(gmAppBelowBoundary.isMultiplayer());
            assertFalse(gmAppOnBoundary.isMultiplayer());
            assertTrue(gmAppAboveBoundary.isMultiplayer());
            assertFalse(gmAppInvalidData.isMultiplayer());
        }

    }

    @Nested
    class Setters {

        @Test
        void setDeveloper() {
            //no validation in models
            assertEquals(developerLego, gmAppBelowBoundary.getDeveloper());
            gmAppBelowBoundary.setDeveloper(developerSphero);
            assertEquals(developerSphero, gmAppBelowBoundary.getDeveloper());
        }

        @Test
        void setAppName() {
            //no validation in models
            assertEquals("WeDo", gmAppBelowBoundary.getAppName());
            gmAppBelowBoundary.setAppName("Mindstorms");
            assertEquals("Mindstorms", gmAppBelowBoundary.getAppName());
        }

        @Test
        void setAppSize() {
            //Validation: appSize(1-1000)
            assertEquals(1, gmAppBelowBoundary.getAppSize());

            gmAppBelowBoundary.setAppSize(1000);
            assertEquals(1000, gmAppBelowBoundary.getAppSize()); //update

            gmAppBelowBoundary.setAppSize(1001);
            assertEquals(1000, gmAppBelowBoundary.getAppSize()); //no update

            gmAppBelowBoundary.setAppSize(2);
            assertEquals(2, gmAppBelowBoundary.getAppSize()); //update

            gmAppBelowBoundary.setAppSize(0);
            assertEquals(2, gmAppBelowBoundary.getAppSize()); //no update
        }

        @Test
        void setAppVersion() {
            //Validation: appVersion(>=1.0)
            assertEquals(1.0, gmAppBelowBoundary.getAppVersion());

            gmAppBelowBoundary.setAppVersion(2.0);
            assertEquals(2.0, gmAppBelowBoundary.getAppVersion()); //update

            gmAppBelowBoundary.setAppVersion(0.0);
            assertEquals(2.0, gmAppBelowBoundary.getAppVersion()); //no update

            gmAppBelowBoundary.setAppVersion(1.0);
            assertEquals(1.0, gmAppBelowBoundary.getAppVersion()); //update
        }

        @Test
        void setAppCost() {
            //Validation: appCost(>=0)
            assertEquals(0.0, gmAppBelowBoundary.getAppCost());

            gmAppBelowBoundary.setAppCost(1.0);
            assertEquals(1.0, gmAppBelowBoundary.getAppCost()); //update

            gmAppBelowBoundary.setAppCost(-1);
            assertEquals(1.0, gmAppBelowBoundary.getAppCost()); //no update

            gmAppBelowBoundary.setAppCost(0.0);
            assertEquals(0.0, gmAppBelowBoundary.getAppCost()); //update
        }

        @Test
        void setMultiplayer() {
            //Validation: level(1-10)
            assertTrue(gmAppBelowBoundary.isMultiplayer());

            gmAppBelowBoundary.setMultiplayer(false);
            assertFalse(gmAppBelowBoundary.isMultiplayer()); //update

        }

    }

    @Nested
    class ObjectStateMethods {

        @Test
        void appSummaryReturnsCorrectString() {
            GameApp gmApp = setupGameAppWithRating(3, 4);
            String stringContents = gmApp.appSummary();

            assertTrue(stringContents.contains(" Multiplayer: " + utils.Utilities.booleanToYN(gmApp.isMultiplayer())));
            assertTrue(stringContents.contains(gmApp.getAppName() + "(V" + gmApp.getAppVersion()));
            assertTrue(stringContents.contains(gmApp.getDeveloper().toString()));
            assertTrue(stringContents.contains("€" + gmApp.getAppCost()));
            assertTrue(stringContents.contains("Rating: " + gmApp.calculateRating()));
        }

        @Test
        void toStringReturnsCorrectString() {
            GameApp gmApp = setupGameAppWithRating(3, 4);
            String stringContents = gmApp.toString();

            assertTrue(stringContents.contains(gmApp.getAppName()));
            assertTrue(stringContents.contains("(Version " + gmApp.getAppVersion()));
            assertTrue(stringContents.contains(gmApp.getDeveloper().toString()));
            assertTrue(stringContents.contains(gmApp.getAppSize() + "MB"));
            assertTrue(stringContents.contains("Cost: " + gmApp.getAppCost()));
            assertTrue(stringContents.contains(" Multiplayer: " + utils.Utilities.booleanToYN(gmApp.isMultiplayer())));
            assertTrue(stringContents.contains("Ratings (" + gmApp.calculateRating()));

            //contains list of ratings too
            assertTrue(stringContents.contains("John Doe"));
            assertTrue(stringContents.contains("Very Good"));
            assertTrue(stringContents.contains("Jane Doe"));
            assertTrue(stringContents.contains("Excellent"));
        }

    }

    @Nested
    class RecommendedApp {

        @Test
        void appIsNotRecommendedWhenRatingIsLessThan4() {
            //setting all conditions to true with ratings of 4 and 4 (i.e. 3.0)
            GameApp gmApp = setupGameAppWithRating(2, 2);
            //verifying recommended app returns false (rating not high enough
            assertFalse(gmApp.isRecommendedApp());
        }

        @Test
        void appIsNotRecommendedWhenNoRatingsExist() {
            //setting all conditions to true with no ratings
            GameApp gmApp = new GameApp(developerLego, "WeDo", 1,
                    1.0, 1.00, true);
            //verifying recommended app returns true
            assertFalse(gmApp.isRecommendedApp());
        }

        @Test
        void appIsRecommendedWhenAllOfTheConditionsAreTrue() {
            //setting all conditions to true with ratings of 5 and 5 (i.e. 5)
            GameApp gmApp = setupGameAppWithRating(5, 5);
            //verifying recommended app returns true
            assertTrue(gmApp.isRecommendedApp());
        }

    }

    GameApp setupGameAppWithRating(int rating1, int rating2) {
        //setting all conditions to true
        GameApp gmApp = new GameApp(developerLego, "WeDo", 1,
                1.0, 1.00, true);
        gmApp.addRating(new Rating(rating1, "John Doe", "Very Good"));
        gmApp.addRating(new Rating(rating2, "Jane Doe", "Excellent"));

        //verifying all conditions are true for a recommended educational app]
        assertEquals(2, gmApp.getRatings().size());  //two ratings are added
        assertEquals(1.0, gmApp.getAppCost(), 0.01);
        assertEquals(((rating1 + rating2) / 2.0), gmApp.calculateRating(), 0.01);
        assertTrue(gmApp.isMultiplayer());

        return gmApp;
    }
}
