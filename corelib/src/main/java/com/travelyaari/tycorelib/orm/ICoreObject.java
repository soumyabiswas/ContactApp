package com.travelyaari.tycorelib.orm;

import java.util.List;

/**
 * Created by Triode on 6/15/2016.
 */
public interface ICoreObject<T> {
    /**
     * function saves a list of passed {@code T} to the DB
     *
     * @param objects the list of items to be saved
     */
    void saveAll(final List<T> objects) throws Exception;

    /**
     * function which will remove the records from the DB
     *
     * @param objects the list of items to be removed
     */
    void deleteAll(final List<T> objects);

    /**
     * function delete all the records in the table
     * @throws Exception if the deletion fails
     */
    void deleteAll() throws Exception;
}
