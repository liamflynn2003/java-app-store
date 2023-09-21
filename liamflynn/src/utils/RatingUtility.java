package utils;

import models.Rating;

import java.util.*;

import static java.lang.Math.random;

public class RatingUtility {

    private static List<String> listOfAuthors = new ArrayList<>() {{
        add("JohnD");
        add("Adam101");
        add("Eve101");
        add("Cary1987");
        add("RickyW");
        add("MarkD");
        add("Scotty");
        add("Mary3");
        add("Flynn121");
    }};

    private static List<String> listOfComments = new ArrayList<>() {{
        add("Loved the UX");
        add("Great App");
        add("Poor App");
        add("Couldn't stop using app");
        add("Used once, never again");
        add("Too expensive");
        add("Too slow");
        add("Really intuitive");
        add("Not for me");
    }};

    public static Rating generateRandomRating() {
        return new Rating((int) (random() * (5)),
                listOfAuthors.get((int) (random() * (listOfAuthors.size() - 1))),
                listOfComments.get((int) (random() * (listOfComments.size() - 1)))
        );
    }
}