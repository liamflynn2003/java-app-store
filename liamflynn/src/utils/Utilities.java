package utils;

import java.util.regex.*;
import java.util.List;

public class Utilities {

    /**
     * This method takes in a decimal point number and truncates it to two decimal places.  Note
     * that the method does NOT round when truncating; the numbers after the two decimal places are
     * just removed.
     * <p>
     * The method does the truncating in this manner:
     * - multiply the number by 100 e.g. 16.543235523 * 100 = 1654.3235523
     * - cast the multiplied number as an in e.g. 1654.3235523 = 1654
     * - finally, the multiplied and casted number is divided by 100 and returned e.g. 1654 = 16.54
     *
     * @param number Number to be truncated to two decimal places
     * @return the number, passed as a parameter, truncated to two decimal places (note: not rounded)
     */
    public static double toTwoDecimalPlaces(double number) {
        return (int) (number * 100) / 100.0;
    }

    /**
     * This method returns Y if the booleanToConvert value is true. Returns N otherwise.
     *
     * @param booleanToConvert The boolean value that will be used to determine Y/N
     * @return Returns Y if the booleanToConvert value is true. Returns N otherwise.
     */
    public static char booleanToYN(boolean booleanToConvert) {
        return booleanToConvert ? 'Y' : 'N';
    }

    /**
     * This method returns true if the charToConvert value is Y or y. Returns false in all other cases.
     *
     * @param charToConvert The char value that will be used to determine true/false.
     * @return Returns true if the charToConvert value is Y or y. Returns false otherwise.
     */
    public static boolean YNtoBoolean(char charToConvert) {
        return ((charToConvert == 'y') || (charToConvert == 'Y'));
    }

    /**
     * This method returns true if the numberToCheck is between min and max (both inclusive)
     *
     * @param numberToCheck The number whose range is being checked.
     * @param min           The minimum range number to check against (inclusive)
     * @param max           The maximum range number to check against (inclusive)
     * @return Returns true if the numberToCheck is between min and max (both inclusive), false otherwise.
     */
    public static boolean validRange(int numberToCheck, int min, int max) {
        return ((numberToCheck >= min) && (numberToCheck <= max));
    }

    public static boolean validRange(double numberToCheck, int min, int max) {
        return ((numberToCheck >= min) && (numberToCheck <= max));
    }

    /**
     * This method returns true if the numberToCheck is zero or more.
     *
     * @param numberToCheck The number whose range is being checked.
     * @return Returns true if the numberToCheck is between zero or more, false otherwise.
     */
    public static boolean greaterThanOrEqualTo(int numberToCheck, int value) {
        return (numberToCheck >= value);
    }

    public static boolean greaterThanOrEqualTo(double numberToCheck, double value) {
        return (numberToCheck >= value);
    }

    /**
     * This method returns true if the numberToCheck is between min (exclusive) and max (inclusive)
     *
     * @param numberToCheck The number whose range is being checked.
     * @param min           The minimum range number to check against (exclusive)
     * @param max           The maximum range number to check against (inclusive)
     * @return Returns true if the numberToCheck is between min (exclusive) and max (inclusive), false otherwise.
     */
    public static boolean validRangeExclIncl(double numberToCheck, double min, double max) {
        return ((numberToCheck > min) && (numberToCheck <= max));
    }

    /**
     * This method returns a string that was passed as a parameter, truncated to a specific length, also
     * passed as a parameter.  If the original String is less than the passed length, then the original string
     * is just returned.
     *
     * @param stringToTruncate The string that will be truncated to a specific length
     * @param length           The length to which to truncate the string to
     * @return The string truncated to a specific length
     */
    public static String truncateString(String stringToTruncate, int length) {
        if (stringToTruncate.length() <= length) {
            return stringToTruncate;
        } else {
            return stringToTruncate.substring(0, length);
        }
    }

    /**
     * This method takes in a string, passed as a parameter and valdiates whether it is less than or equal to
     * a specific length or not.
     *
     * @param strToCheck The string that will be checked to see if it is a specific length
     * @param maxLength  The length that the string will be validated against
     * @return True if the string is less than or equal the max length and false otherwise.
     */
    public static boolean validateStringLength(String strToCheck, int maxLength) {
        return strToCheck.length() <= maxLength;
    }

    public static boolean isValidIndex(List list, int indexToCheck) {
        return ((indexToCheck >= 0) && (indexToCheck < list.size()));
    }

    public static boolean isValidURL(String url) {
        //This utility method uses the regex library.
        String regex = "((http|https)://)(www.)?"
                + "[a-zA-Z0-9@:%._\\+~#?&//=]"
                + "{2,256}\\.[a-z]"
                + "{2,6}\\b([-a-zA-Z0-9@:%"
                + "._\\+~#?&//=]*)";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(url);
        return m.matches();


    }
}