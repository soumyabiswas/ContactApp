package com.travelyaari.tycorelib.orm;

import java.util.Map;

/**
 * Created by Triode on 6/15/2016.
 */
public interface IQueryMap {


    /**
     * function parse the mWhereClauseMap, Create the selection key and array
     * of selection args
     *
     * @param map from which the keys and values to be obtained
     */
    void parseWhereClauseMap(final Map<String, String> map);

    /**
     * function parse the where not equals map
     *
     * @param map the map which contains the key value pair
     */
    void parseWhereNotEqualsMap(final Map<String, String> map);

    /**
     * function parse the where not equals map
     *
     * @param map the map which contains the key value pair
     */
    void parseStartWithMap(final Map<String, String> map);

    /**
     * function appends the sort order to the where clause
     *
     * @param columns the columns constraint to be added against the
     *        sort order
     */
    void orderBy(final String[] columns);

    /**
     * appends the sort order for the query
     *
     * @param sortOrder the sort order to be appended
     */
    void sortOrder(final String sortOrder);

    /**
     * function sets the limit and the offset against the where clause
     *
     * @param offset the number of records to be skipped
     * @param limit the number of rows to be present in the result set
     */
    void limit(final int offset, final int limit);

}
