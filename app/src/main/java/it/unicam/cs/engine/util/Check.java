package it.unicam.cs.engine.util;


import it.unicam.cs.api.exception.NotEnoughParametersException;

/**
 * A utility class that provides methods to check certain conditions.
 * 
 * @author  Younes Rabeh
 * @version 1.1
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
                throw new IndexOutOfBoundsException("[!!] - " + num.doubleValue() + " is non-positive");
            }
        }
    }

    /**
     * Check if the numbers are smaller than the given value. if so, throw an exception.
     * @param value the value to compare
     * @param nums the numbers to check
     * @throws IndexOutOfBoundsException if one of the numbers is smaller than the given value
     */
    public static void checkNumbersMin(double value, Number... nums) {
        for (Number num : nums) {
            if (num == null) throw new NullPointerException("[!!!] - null value");
            if (num.doubleValue() < value){
                throw new IndexOutOfBoundsException("[!!] - " + num.doubleValue() + " is non-positive");
            }
        }
    }

    /**
     * Check if the numbers are non-positive. if so, throw an exception.
     * @param nums the numbers to check
     * @throws IndexOutOfBoundsException if one of the numbers is non-positive
     */
    public static void checkNumbers(int[] nums) {
        for (int num : nums) {
            if (num < 0){
                throw new IndexOutOfBoundsException("[!!] - " + num + " is non-positive");
            }
        }
    }

    /**
     * Check if the length of the params array is at least the expected length,
     * and check if each parameter is non-positive.
     * @param params the parameters to check
     * @param expectedParamNumber the expected number of parameters
     * @throws NotEnoughParametersException if the length of the params array is less than the expected length
     * @throws IndexOutOfBoundsException if one of the parameters is non-positive
     */
    public static void checkParams(int[] params, int expectedParamNumber) {
        if (params.length >= expectedParamNumber){
            throw new NotEnoughParametersException(params.length);
        }
        checkNumbers(params);
    }


    /**
     * Check if the objects are null. if so, throw an exception.
     * @param objects the objects to check
     * @throws NullPointerException if one of the objects is null
     */
    public static void checkNull(Object... objects) {
        for (Object obj : objects) {
            if (obj == null) throw new NullPointerException("[!!!] -null value");
        }
    }

}
