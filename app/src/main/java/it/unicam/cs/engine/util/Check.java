package it.unicam.cs.engine.util;


/**
 * A utility class that provides methods to check if value are in a valid range.
 * 
 * @author  Younes Rabeh
 * @version 1.0
 */
public final class Check {
    /**
     * Check if the numbers are non-positive. if so, throw an exception.
     * @param nums the numbers to check
     * @throws IndexOutOfBoundsException if one of the numbers is non-positive
     * @throws NullPointerException if one of the numbers is null
     */
    public static void checkNumbers(Number... nums) {
        for (Number num : nums) {
            if (num == null) throw new NullPointerException("[!!!] - null value");
            if (num.doubleValue() < 0){
                throw new IndexOutOfBoundsException("[!!!] - " + num.doubleValue() + " is non-positive");
            }
        }
    }

    /**
     * Check if the numbers are non-positive. if so, throw an exception.
     * @param nums the numbers to check
     * @throws IndexOutOfBoundsException if one of the numbers is non-positive
     * @throws NullPointerException if array of numbers is null
     */
    public static void checkNumbers(int[] nums) {
        if (nums == null) throw new NullPointerException("[!!!] - null value");
        for (int num : nums) {
            if (num < 0){
                throw new IndexOutOfBoundsException("[!!!] - " + num + " is non-positive");
            }
        }
    }

    /**
     * Check if the objects are null. if so, throw an exception.
     * @param objects the objects to check
     * @throws NullPointerException if one of the objects is null
     */
    public static void checkNull(Object... objects) {
        for (Object obj : objects) {
            if (obj == null) throw new NullPointerException("[!!!] - null value");
        }
    }
}
