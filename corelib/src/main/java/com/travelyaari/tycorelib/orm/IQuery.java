package com.travelyaari.tycorelib.orm;

import java.util.List;

/**
 * Created by Triode on 5/12/2016.
 */
public interface IQuery {


    /**
     * function to set the not equals selection
     *
     * @param key the key against which the selection to be done
     * @param value the value to be compared
     */
    void whereNotEquals(String key, String value);

    /**
     * function to set equals selection
     *
     * @param key against which the selection to be done
     * @param value value to be compared
     */
    void whereEquals(String key, String value);

    /**
     * this compare an array of values against key provided and return the appropriate
     *
     * @param key against which the selection to be done
     * @param values the values to be compared against a key
     */
    void whereEquals(String key, String[] values);

    /**
     * Function compares a list of HLObject against key provided and return the proper values
     *
     * @param key of type String name of the column against which the selection should be performed
     * @param value of type List<CoreObject> the list of objects to checked against the field
     */
    void whereEquals(String key, List<CoreObject> value);

    /**
     * function to add a check on the respective column name {@code key}
     * has value starting with the provided {@code value}
     *
     * @param key the name of the column against the constraint to be applied
     * @param value the value to be added as a constraint
     */
    void startsWith(String key, String value);

}
