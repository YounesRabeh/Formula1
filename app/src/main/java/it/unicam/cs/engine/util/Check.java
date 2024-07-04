package it.unicam.cs.engine.util;

public class Check {

    /**
     * Check if the numbers are non-positive. if so, throw an exception.
     * @param nums the numbers to check
     * @throws IllegalArgumentException if onr of the numbers is non-positive
     */
    @SafeVarargs
    public static <N extends Number> void checkNumbers(N... nums) throws IllegalArgumentException {
        for (N num : nums) {
            if (num.doubleValue() < 0){
                throw new IllegalArgumentException("[!!!] - " + num.doubleValue() + " is non-positive");
            }
        }
    }
}
