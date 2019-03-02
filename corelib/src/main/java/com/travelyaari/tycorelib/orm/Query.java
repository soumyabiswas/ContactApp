package com.travelyaari.tycorelib.orm;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.travelyaari.tycorelib.Constants;
import com.travelyaari.tycorelib.CoreLib;
import com.travelyaari.tycorelib.ultlils.ArrayUtils;
import com.travelyaari.tycorelib.ultlils.CoreLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Triode on 5/10/2016.
 */
public class Query implements IQuery {


    /**
     * holds the reference to the selection map
     */
    HashMap<String, String[]> mArraySelectionMap;
    int mLimit;
    int mOffSet;
    String[] mSortColumns;
    String mSortOrder;
    boolean order;
    /**
     * Exception raced on querying the database
     */
    Exceptions.QueryException mException;
    /**
     * To hold the instance of the queries which should be chained on load of the contents
     */
    private HashMap<String, Query> mChainedQueries;
    /**
     * Keep track of the not equals keys and entries for it
     */
    private HashMap<String, String> mWhereNotEqualsMap;
    /**
     * Keeps the reference list of string
     */
    private List<String> mReferenceList;
    /**
     * Holds the table name from which the data needs to be fetched
     */
    private String mClassName;
    /**
     * Holds the where clause map this can be set using whereEquals method
     */
    private HashMap<String, String> mWhereClauseMap;
    /**
     * The map holds the where clause for a list of CoreObjects
     */
    private HashMap<String, List<CoreObject>> mWhereClauseObjectMap;
    /**
     * Holds teh array of column names to be included in the result set
     */
    private String[] mColumns;
    private HashMap<String, String> mContainsMap;

    /**
     * Constructor function for the query
     * @param className the name of  the table from which the content should be fetched
     */
    public Query(String className) {
        mClassName = className;
    }

    /**
     * The function which chain the query with the existing query
     *
     * @param key         the foreign key in the chaining query
     * @param query       query to be chained on load of the actual query
     * @param isReference the value decide the key if foreign key or just a reference
     * @return returns the combined query
     */
    public Query chain(String key, Query query, boolean isReference) {
        mChainedQueries = mChainedQueries == null ? new HashMap<String, Query>() :
                mChainedQueries;
        mChainedQueries.put(key, query);
        if (isReference) {
            mReferenceList = (mReferenceList == null) ? new ArrayList<String>() : mReferenceList;
            if (!mReferenceList.contains(query.mClassName))
                mReferenceList.add(query.mClassName);
        }
        return this;
    }

    /**
     * function to set the not equals selection
     *
     * @param key   the key against which the selection to be done
     * @param value the value to be compared
     */
    @Override
    public void whereNotEquals(String key, String value) {
        mWhereNotEqualsMap = (mWhereNotEqualsMap == null) ? new HashMap<String, String>()
                : mWhereNotEqualsMap;
        mWhereNotEqualsMap.put(key, value);
    }

    /**
     * function to set equals selection
     *
     * @param key   against which the selection to be done
     * @param value value to be compared
     */
    @Override
    public void whereEquals(String key, String value) {
        mWhereClauseMap = (mWhereClauseMap == null) ? new HashMap<String, String>() :
                mWhereClauseMap;
        mWhereClauseMap.put(key, value);
    }

    /**
     * This compare an array of values against key
     * provided and return the appropriate
     *
     * @param key    against which the selection to be done
     * @param values the values to be compared against a key
     */
    @Override
    public void whereEquals(String key, String[] values) {
        mArraySelectionMap = (mArraySelectionMap == null) ? new HashMap<String, String[]>() :
                mArraySelectionMap;
        mArraySelectionMap.put(key, values);
    }

    /**
     * function to add a check on the respective column name {@code key}
     * has value starting with the provided {@code value}
     *
     * @param key   the name of the column against the constraint to be applied
     * @param value the value to be added as a constraint
     */
    @Override
    public void startsWith(String key, String value) {
        value = value + "%";
        mContainsMap = (mContainsMap == null) ? new HashMap<String, String>() : mContainsMap;
        mContainsMap.put(key, value);
    }

    /**
     * Funcltion compares a list of CoreObject against key provided and return the proper values
     *
     * @param key   of type String name of the column against which the selection should be performed
     * @param value of type List<CoreObject> the list of objects to checked against the field
     */
    @Override
    public void whereEquals(String key, List<CoreObject> value) {
        mWhereClauseObjectMap = (mWhereClauseObjectMap == null) ? new HashMap<String, List<CoreObject>>() :
                mWhereClauseObjectMap;
        mWhereClauseObjectMap.put(key, value);
    }

    /**
     * Set the columns to be reflected in the result set
     *
     * @param columns of type String[] extra columns to be reflected
     */
    public void setMselect(String[] columns) {
        mColumns = ArrayUtils.concat(getDefaultColumns(), columns);
    }

    /**
     * based on the class name columns will be returned
     *
     * @return the default column array will be returned
     */
    protected String[] getDefaultColumns() {
        if (mClassName.equals(Constants.USER)) {
            return new String[]{Constants._ID, Constants._createdAt, Constants._updatedAt,
                    Constants._user_phone, Constants._user_password, Constants._user_email,
                    Constants._user_name
            };
        } else {
            return new String[]{Constants._ID, Constants._createdAt, Constants._updatedAt};
        }
    }

    /**
     * @param offSet = row Number from  which  skip or fetch should start
     * @param limit = number of rows to be skipped or fetched
     * @param isSkip = boolean to to skip or fetch, if true skips the rows else fetch the rows
     */
    public void setLimit(int offSet, int limit, boolean isSkip) {
        mLimit = limit;
        mOffSet = offSet;
    }

    /**
     *
     * @param columns = includes columns by which sorting has to be done
     * @param isAscendingOrder if true Ascending Order else Descending order
     */
    public void sortAndFetch(String[] columns, boolean isAscendingOrder) {
        mSortColumns = columns;
        order = isAscendingOrder;
        if (order)
            mSortOrder = "ASC";
        else
            mSortOrder = "DESC";
        CoreLogger.d("SORT-ORDER", mSortOrder);
    }

    /**
     * function query the DB using the values mentioned and parse it to
     * a list of {@code IDBModel}
     *
     * @param modelClass the class name of the object to be created
     * @return the obtained result set
     * throws {@code QueryException}
     */
    public List<Object> query(final Class modelClass) throws Exceptions.QueryException{
        final QueryParameters params = new QueryParameters();
        params.parseWhereClauseMap(mWhereClauseMap);
        params.parseWhereNotEqualsMap(mWhereNotEqualsMap);
        params.parseStartWithMap(mContainsMap);
        params.orderBy(mSortColumns);
        params.sortOrder(mSortOrder);
        params.limit(mOffSet, mLimit);
        return (List<Object>) parseCursor(query(params), modelClass);
    }

    /**
     * function counts the number of records in the table
     *
     * @return the number of records
     * @throws Exceptions.QueryException
     */
    public long countRecords() throws Exceptions.QueryException {
        final SQLiteDatabase db = CoreLib.getDbHelper().getReadableDatabase();
        final long count = DatabaseUtils.queryNumEntries(db, mClassName);
        db.close();
        return count;
    }

    /**
     * function parse creates the model class object against the cursor
     * passed
     *
     * @param cursor against the models to be created
     * @param modelClass the class name of the model class s
     * @return the list of created model objects
     */
    Object parseCursor(final Cursor cursor, final Class modelClass) throws Exceptions.QueryException{
        List<IDBModel> result = null;
        result = new ArrayList<>(cursor.getCount());
        if(cursor != null && cursor.moveToFirst()){
            do{
                try{
                    final Object model = modelClass.newInstance();
                    ((IDBModel)model).parseCursor(cursor);
                    result.add((IDBModel)model);
                }catch (java.lang.InstantiationException e){
                    throw new Exceptions.QueryException(e.getCause());
                }catch (IllegalAccessException e){
                    throw new Exceptions.QueryException(e.getCause());
                }
            }while(cursor.moveToNext());
            cursor.close();
        }

        return result;
    }


    /**
     * function obtsain the result set {@link Cursor} against the params
     *
     * @param params the params containing constraints
     * @return the obtained cusor
     */
    Cursor query(final QueryParameters params) throws Exceptions.QueryException{
        final SQLiteDatabase db = CoreLib.getDbHelper().getReadableDatabase();
        Cursor cursor = null;
        try {
            final String[] whereArgs = params.whereArgs.length == 0 ? null : params.whereArgs;
            cursor = db.query(mClassName, mColumns, params.where, whereArgs , null, null, null, null);
        }catch(Exception e){
            mException = new Exceptions.QueryException(e.getLocalizedMessage());
        }
        return cursor;
    }

}
