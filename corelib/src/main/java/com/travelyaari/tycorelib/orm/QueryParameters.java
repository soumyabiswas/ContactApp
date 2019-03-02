package com.travelyaari.tycorelib.orm;

import android.text.TextUtils;

import com.travelyaari.tycorelib.ultlils.ArrayUtils;

import java.util.Map;

/**
 * Created by Triode on 6/15/2016.
 */
public class QueryParameters implements IQueryMap{
    String where = "";
    String[] whereArgs = new String[]{};

    /**
     * function appends the where and whereArgs passed to the
     * existing values
     *
     * @param where the where fields to be appended
     * @param whereArgs the where args to be appended
     */
    void appendQueryParams(final String where, final String[] whereArgs){
        if(where.isEmpty())
            return;
        if(this.where.isEmpty()){
            this.where = where;
            this.whereArgs = whereArgs;
        }else{
            this.where += " AND " + where;
            this.whereArgs = ArrayUtils.concat(this.whereArgs, whereArgs);
        }
    }


    /**
     * function parse the mWhereClauseMap, Create the selection key and array
     * of selection args
     *
     * @param map from which the keys and values to be obtained
     */
    @Override
    public void parseWhereClauseMap(Map<String, String> map) {
        parseMap(map, "=");
    }

    /**
     * function parse the where not equals map
     *
     * @param map the map which contains the key value pair
     */
    @Override
    public void parseWhereNotEqualsMap(Map<String, String> map) {
        parseMap(map, "!=");
    }

    /**
     * function parse the start with map, this uses LIKE operator of
     * SQLite
     *
     * @param map the map which contains the key value pair
     */
    @Override
    public void parseStartWithMap(Map<String, String> map) {
        parseMap(map, "LIKE");
    }


    /**
     * function appends the sort order to the where clause
     *
     * @param columns the columns constraint to be added against the
     *                sort order
     */
    @Override
    public void orderBy(String[] columns) {
        if(columns != null && columns.length > 0){
            where += " ORDER BY " + TextUtils.join(",", columns);
        }
    }

    /**
     * appends the sort order for the query
     *
     * @param sortOrder the sort order to be appended
     */
    @Override
    public void sortOrder(String sortOrder) {
        if (sortOrder != null){
            where += " " + sortOrder;
        }
    }

    /**
     * function sets the limit and the offset against the where clause
     *
     * @param offset the number of records to be skipped
     * @param limit  the number of rows to be present in the result set
     */
    @Override
    public void limit(int offset, int limit) {
        if (limit>0){
            where += " LIMIT "+limit+" OFFSET "+offset;
        }
    }

    /**
     * function act as a common entry point to parse the map
     * @param map
     * @param operator
     */
    void parseMap(final Map<String, String> map, final String operator){
        if(map != null && map.size() > 0){
            String where = "";
            final String[] whereArgs = new String[map.size()];
            int count = 0;
            for(String key : map.keySet()){
                if(this.where.indexOf(key + " ") == -1) {
                    where += ((count == 0) ? "" : " AND ") + key + " " + operator + " ?";
                    whereArgs[count] = map.get(key);
                    count++;
                }
            }
            appendQueryParams(where, whereArgs);
        }
    }



}
