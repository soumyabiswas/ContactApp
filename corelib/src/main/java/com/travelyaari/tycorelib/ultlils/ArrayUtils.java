package com.travelyaari.tycorelib.ultlils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Triode on 5/13/2016.
 */
public final class ArrayUtils {

    /**
     * Function combine two arrays
     * @param first the array needs to be merged
     * @param second the second array to be merged
     * @param <T> the type of value the array is holding
     *
     * @return the merged array
     */
    public static <T> T[] concat(T[] first, T[] second) {
        if(first == null)
            return second;
        if(second == null)
            return first;
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * function which converts the {@code inputArray} to array of int
     *
     * @param inputArray the array to be mapped
     * @return the outputArray
     */
    public static int[] copyOf(final Integer[] inputArray){
        final int[] outputArray = new int[inputArray.length];
        for(int index = 0; index < inputArray.length; index++){
            outputArray[index] = inputArray[index];
        }
        return outputArray;
    }

    /**
     * function which converts the {@code inputArray} to array of int
     *
     * @param input the List to be mapped
     * @return the outputArray
     */
    public static int[] copyOf(final List<Integer> input){
        final int[] outputArray = new int[input.size()];
        for(int index = 0; index < input.size(); index++){
            outputArray[index] = (int)input.get(index);
        }
        return outputArray;
    }

    /**
     * function which will check array contains all the elements in
     * child array or not
     *
     * @param parent the parent array against the comparison to be made
     * @param child the arrays's elements to be compared
     * @return true if contains all the items in child array false otherwise
     */
    public static final boolean containsAll(final int[] parent, final int[] child){
        for(int item : child){
            if(!contains(parent, item)){
                return false;
            }
        }
        return true;
    }

    /**
     * function check the item present in the array or not
     * @param parent the array against the item to be mapped
     * @param item the item to be searched
     * @return true if found else false
     */
    public static final boolean contains(final int[] parent, final int item){
        for(int arrayItem : parent){
            if(arrayItem == item) {
                CoreLogger.log("", "");
                return true;
            }
        }
        return false;
    }


    /**
     * function check the item present in the array or not
     * @param parent the array against the item to be mapped
     * @param item the item to be searched
     * @return true if found else false
     */
    public static final boolean contains(final String[] parent, final String item){
        for(final String arrayItem : parent){
            if(arrayItem.equals(item)) {
                CoreLogger.log("", "");
                return true;
            }
        }
        return false;
    }


    /**
     * function returns array containing a range of values
     *
     * @param min the minimum range
     * @param max the maximum range
     * @return the created array
     */
    public static String[] createRangedArray(final int min, final int max){
        final String[] array = new String[(max - min) + 1];
        for(int index = min; index <= max; index++){
            array[index - min] = index+"";
        }
        return array;
    }

}




