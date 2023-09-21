package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EducationAppTest {

    private EducationApp edAppBelowBoundary, edAppOnBoundary, edAppAboveBoundary, edAppInvalidData;
    private Developer developerLego = new Developer("Lego", "www.lego.com");
    private Developer developerSphero = new Developer("Sphero", "www.sphero.com");

    @BeforeEach
    void setUp() {
        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0), level(1-10).
        edAppBelowBoundary = new EducationApp(developerLego, "WeDo", 1, 1.0, 0, 1);
        edAppOnBoundary = new EducationApp(developerLego, "Spike", 1000, 2.0, 1.99, 10);
        edAppAboveBoundary = new EducationApp(developerLego, "EV3", 1001, 3.5, 2.99, 11);
        edAppInvalidData = new EducationApp(developerLego, "", -1, 0, -1.00,0);
    }

    @AfterEach
    void tearDown() {
        edAppBelowBoundary = edAppOnBoundary = edAppAboveBoundary = edAppInvalidData = null;
        developerLego = developerSphero = null;
    }

    @Nested
    class Getters {

        @Test
        void getDeveloper() {
            assertEquals(developerLego, edAppBelowBoundary.getDeveloper());
            assertEquals(developerLego, edAppOnBoundary.getDeveloper());
            assertEquals(developerLego, edAppAboveBoundary.getDeveloper());
            assertEquals(developerLego, edAppInvalidData.getDeveloper());
        }

        @Test
        void getAppName() {
            assertEquals("WeDo", edAppBelowBoundary.getAppName());
            assertEquals("Spike", edAppOnBoundary.getAppName());
            assertEquals("EV3", edAppAboveBoundary.getAppName());
            assertEquals("", edAppInvalidData.getAppName());
        }

        @Test
        void getAppSize() {
            assertEquals(1, edAppBelowBoundary.getAppSize());
            assertEquals(1000, edAppOnBoundary.getAppSize());
            assertEquals(0, edAppAboveBoundary.getAppSize());
            assertEquals(0, edAppInvalidData.getAppSize());
        }

        @Test
        void getAppVersion() {
            assertEquals(1.0, edAppBelowBoundary.getAppVersion());
            assertEquals(2.0, edAppOnBoundary.getAppVersion());
            assertEquals(3.5, edAppAboveBoundary.getAppVersion());
            assertEquals(1.0, edAppInvalidData.getAppVersion());
        }

        @Test
        void getAppCost() {
            assertEquals(0, edAppBelowBoundary.getAppCost());
            assertEquals(1.99, edAppOnBoundary.getAppCost());
            assertEquals(2.99, edAppAboveBoundary.getAppCost());
            assertEquals(0, edAppInvalidData.getAppCost());
        }

        @Test
        void getLevel() {
            assertEquals(1, edAppBelowBoundary.getLevel());
            assertEquals(10, edAppOnBoundary.getLevel());
            assertEquals(0, edAppAboveBoundary.getLevel());
            assertEquals(0, edAppInvalidData.getLevel());
        }

    }

    @Nested
    class Setters {

        @Test
        void setDeveloper() {
            //no validation in models
            assertEquals(developerLego, edAppBelowBoundary.getDeveloper());
            edAppBelowBoundary.setDeveloper(developerSphero);
            assertEquals(developerSphero, edAppBelowBoundary.getDeveloper());
        }

        @Test
        void setAppName() {
            //no validation in models
            assertEquals("WeDo", edAppBelowBoundary.getAppName());
            edAppBelowBoundary.setAppName("Mindstorms");
            assertEquals("Mindstorms", edAppBelowBoundary.getAppName());
        }

        @Test
        void setAppSize() {
            //Validation: appSize(1-1000)
            assertEquals(1, edAppBelowBoundary.getAppSize());

            edAppBelowBoundary.setAppSize(1000);
            assertEquals(1000, edAppBelowBoundary.getAppSize()); //update

            edAppBelowBoundary.setAppSize(1001);
            assertEquals(1000, edAppBelowBoundary.getAppSize()); //no update

            edAppBelowBoundary.setAppSize(2);
            assertEquals(2, edAppBelowBoundary.getAppSize()); //update

            edAppBelowBoundary.setAppSize(0);
            assertEquals(2, edAppBelowBoundary.getAppSize()); //no update
        }

        @Test
        void setAppVersion() {
            //Validation: appVersion(>=1.0)
            assertEquals(1.0, edAppBelowBoundary.getAppVersion());

            edAppBelowBoundary.setAppVersion(2.0);
            assertEquals(2.0, edAppBelowBoundary.getAppVersion()); //update

            edAppBelowBoundary.setAppVersion(0.0);
            assertEquals(2.0, edAppBelowBoundary.getAppVersion()); //no update

            edAppBelowBoundary.setAppVersion(1.0);
            assertEquals(1.0, edAppBelowBoundary.getAppVersion()); //update
        }

        @Test
        void setAppCost() {
            //Validation: appCost(>=0)
            assertEquals(0.0, edAppBelowBoundary.getAppCost());

            edAppBelowBoundary.setAppCost(1.0);
            assertEquals(1.0, edAppBelowBoundary.getAppCost()); //update

            edAppBelowBoundary.setAppCost(-1);
            assertEquals(1.0, edAppBelowBoundary.getAppCost()); //no update

            edAppBelowBoundary.setAppCost(0.0);
            assertEquals(0.0, edAppBelowBoundary.getAppCost()); //update
        }

        @Test
        void setLevel() {
            //Validation: level(1-10)
            assertEquals(1, edAppBelowBoundary.getLevel());

            edAppBelowBoundary.setLevel(10);
            assertEquals(10, edAppBelowBoundary.getLevel()); //update

            edAppBelowBoundary.setLevel(11);
            assertEquals(10, edAppBelowBoundary.getLevel()); //no update

            edAppBelowBoundary.setLevel(1);
            assertEquals(1, edAppBelowBoundary.getLevel()); //update
        }

    }

    @Nested
    class ObjectStateMethods {

        @Test
        void appSummaryReturnsCorrectString() {
            EducationApp edApp = setupEducationAppWithRating(3, 4);
            String stringContents = edApp.appSummary();

            assertTrue(stringContents.contains("level " + edApp.getLevel()));
            assertTrue(stringContents.contains(edApp.getAppName() + "(V" + edApp.getAppVersion()));
            assertTrue(stringContents.contains(edApp.getDeveloper().toString()));
            assertTrue(stringContents.contains("€" + edApp.getAppCost()));
            assertTrue(stringContents.contains("Rating: " + edApp.calculateRating()));
        }

        @Test
        void toStringReturnsCorrectString() {
            EducationApp edApp = setupEducationAppWithRating(3, 4);
            String stringContents = edApp.toString();

            assertTrue(stringContents.contains(edApp.getAppName()));
            assertTrue(stringContents.contains("(Version " + edApp.getAppVersion()));
            assertTrue(stringContents.contains(edApp.getDeveloper().toString()));
            assertTrue(stringContents.contains(edApp.getAppSize() + "MB"));
            assertTrue(stringContents.contains("Cost: " + edApp.getAppCost()));
            assertTrue(stringContents.contains("Level: " + edApp.getLevel()));
            assertTrue(stringContents.contains("Ratings (" + edApp.calculateRating()));

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
        void appIsNotRecommendedWhenInAppCostIs99c() {
            //setting all conditions to true with ratings of 3 and 4 (i.e. 3.5)
            EducationApp edApp = setupEducationAppWithRating(3, 4);

            //now setting appCost to 0.99 so app should not be recommended now
            edApp.setAppCost(0.99);
            assertFalse(edApp.isRecommendedApp());
        }

        @Test
        void appIsNotRecommendedWhenRatingIsLessThan3AndAHalf() {
            //setting all conditions to true with ratings of 3 and 3 (i.e. 3.0)
            EducationApp edApp = setupEducationAppWithRating(3, 3);
            //verifying recommended app returns false (rating not high enough
            assertFalse(edApp.isRecommendedApp());
        }

        @Test
        void appIsNotRecommendedWhenNoRatingsExist() {
            //setting all conditions to true with no ratings
            EducationApp edApp = new EducationApp(developerLego, "WeDo", 1,
                    1.0, 1.00,  3);
            //verifying recommended app returns true
            assertFalse(edApp.isRecommendedApp());
        }

        @Test
        void appIsRecommendedWhenAllOfTheConditionsAreTrue() {
            //setting all conditions to true with ratings of 3 and 4 (i.e. 3.5)
            EducationApp edApp = setupEducationAppWithRating(3, 4);

            //verifying recommended app returns true
            assertTrue(edApp.isRecommendedApp());
        }

    }

    EducationApp setupEducationAppWithRating(int rating1, int rating2) {
        //setting all conditions to true
        EducationApp edApp = new EducationApp(developerLego, "WeDo", 1,
                1.0, 1.00,  3);
        edApp.addRating(new Rating(rating1, "John Doe", "Very Good"));
        edApp.addRating(new Rating(rating2, "Jane Doe", "Excellent"));

        //verifying all conditions are true for a recommended educational app]
        assertEquals(2, edApp.getRatings().size());  //two ratings are added
        assertEquals(1.0, edApp.getAppCost(), 0.01);
        assertEquals(((rating1 + rating2) / 2.0), edApp.calculateRating(), 0.01);
        assertEquals(3, edApp.getLevel());

        return edApp;
    }
}