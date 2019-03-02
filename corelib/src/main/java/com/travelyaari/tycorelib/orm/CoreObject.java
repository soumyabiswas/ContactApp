package com.travelyaari.tycorelib.orm;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.travelyaari.tycorelib.Constants;
import com.travelyaari.tycorelib.CoreLib;
import com.travelyaari.tycorelib.primitives.IGetSet;
import com.travelyaari.tycorelib.ultlils.LocaleStore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Triode on 5/10/2016.
 */
public class CoreObject implements IGetSet, Parcelable{


    /**
     *
     */
    public static final Parcelable.Creator<CoreObject> CREATOR
            = new Parcelable.Creator<CoreObject>() {
        /**
         *
         * @param in
         * @return
         */
        public CoreObject createFromParcel(Parcel in) {
            return new CoreObject(in);
        }

        /**
         * @param size
         * @return
         */
        public CoreObject[] newArray(int size) {
            return new CoreObject[size];
        }
    };
    /**
     * Holds the unique id of the object
     */
    protected String mObjectId;
    /*
     * Switch to indicate the object is backed or not, means if this is false that means this object has
     * changes which needs to be saved to the permenent storage(Database, File or SharedPreference)
     */
    protected boolean mIsDirty;
    /**
     * Reference to the time which the object created, this will be system time in milliseconds
     */
    protected long mCreatedTime;
    /**
     * The time when a change happened to any of the object properties this will be time is milli seconds
     */
    protected long mUpdatedTime;
    /**
     * Decides the object is synced with the db or not
     */
    boolean mIsSynced;
    /**
     * Map keep track of the unique key's
     */
    HashMap<String, Boolean> mUniqueKeyMap;
    /**
     * Decides if the content is fetched from the database or not,
     * if not you can call fetch method to obtain the whole content
     */
    boolean mFetched;
    /**
     * This will be the class name or name of the object, if the object is saved in SQLite this indicates the
     * table name of the object
     */
    private String mClassName;
    /**
     * Holds the string values, each values will be saved agaimst a key
     */
    private HashMap<String, String> mStringMap;
    /**
     * Holds the integer values, each values will be saved agaimst a key
     */
    private HashMap<String, Integer> mIntegerMap;
    /**
     * Holds the float values, each values will be saved agaimst a key
     */
    private HashMap<String, Float> mFloatMap;
    /**
     * Holds the collections of CoreObject against a key
     */
    private HashMap<String, List<CoreObject>> mArrayMap;
    /**
     * Holds the collection of CoreObject against a key
     */
    private HashMap<String, CoreObject> mObjectMap;


    /**
     * Constructor function, the name should be passed this indicates from which table the obejct to be
     * fetched or saved to
     * @param className of type String, Table to which the object should be saved
     */
    public CoreObject(String className){
        this.mClassName = className;
    }

    /**
     * function which recreate the object from the Parcel
     * @param in of type Parcel
     */
    CoreObject(Parcel in) {
        mClassName = in.readString();
        mObjectId  = in.readString();
        mUpdatedTime = in.readLong();
        mCreatedTime = in.readLong();
        mIsDirty     = (in.readInt() == 1);
        mIsSynced    = (in.readInt() == 1);
    }

    /**
     * function deletes all the records matches the ids passed
     *
     * @throws com.travelyaari.tycorelib.orm.Exceptions.DeleteException
     * @param ids the ids of the object to be deleted
     * @return true if success else false
     */
    static int delete(String[] ids, String mClassName) throws Exceptions.DeleteException {
        SQLiteDatabase db = CoreLib.getDbHelper().getWritableDatabase();
        String args = TextUtils.join(", ", ids);
        try {
            String whereClause = Constants._ID + "=?";
            return db.delete(mClassName, whereClause, ids);
        }catch(SQLException e){
            throw new Exceptions.DeleteException(e.getLocalizedMessage());
        }
    }

    /**
     * function delete list of objects from the database
     * <b>NOTE provide the object with same class name, otherwise throws an exception <b/>
     *
     * @param list the list of objects to be deleted
     * @throws com.travelyaari.tycorelib.orm.Exceptions.DeleteException
     */
    public static void deleteAll(@NonNull List<CoreObject> list,
                                 ORMCallbacks.DeleteCallBack callback){
        DBOperation.acquire().deleteAll(list, callback);
    }

    /**
     * Create object from the cursor record
     * @param cursor from which the value should be fetched
     * @param className the name of the Object name
     * @return newly created object
     */
    static CoreObject obtain(Cursor cursor, String className){
        boolean isUser = className.endsWith(Constants.USER);
        final CoreObject object = new CoreObject(className);
        for(int i = 0; i < cursor.getColumnCount(); i++){
            if(!object.isDefaultColumns(cursor.getColumnNames()[i])) {
                if (cursor.getType(i) == Cursor.FIELD_TYPE_FLOAT) {
                    object.put(cursor.getColumnName(i), cursor.getFloat(i));
                } else if (cursor.getType(i) == Cursor.FIELD_TYPE_INTEGER) {
                    object.put(cursor.getColumnName(i), cursor.getInt(i));
                } else {
                    object.put(cursor.getColumnName(i), cursor.getString(i));
                }
            }
        }
        object.mObjectId = cursor.getString(cursor.getColumnIndex(Constants._ID));
        object.mUpdatedTime = cursor.getInt(cursor.getColumnIndex(Constants._updatedAt));
        object.mCreatedTime = cursor.getInt(cursor.getColumnIndex(Constants._createdAt));
        object.mIsSynced = true;
        object.mIsDirty  = false;
        return object;
    }

    /**
     * function save a list of CoreObject to the database, this internally initiate one DB transaction
     * to make the save faster
     *
     * @param list the list object to be saved
     * @return the list of saved objects
     */
    public static List<CoreObject> saveAll(List<CoreObject> list){
        if(list == null)
            return null;
        final List<CoreObject> result = new ArrayList<>(list.size());
        for(CoreObject object : list){
            try {
                if(object.save()){
                    result.add(object);
                }
            }catch (TableModificationException e){
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * function initiate a background thread to do the save operation
     *
     * @param list the list object to be saved
     * @param callback the callback to listen for the operation
     */
    public static void saveAll(@NonNull List<CoreObject> list, ORMCallbacks.SaveAllCallBack callback){
        DBOperation.acquire().saveAll(list, callback);
    }

    /**
     * function delete all the entries with the class name
     *
     * @param mClassName the name of the objects to be deleted
     * @return true if deleted else false
     */
    public static boolean deleteAll(@NonNull String mClassName){
        final SQLiteDatabase db = CoreLib.getDbHelper().getWritableDatabase();
        try {
            db.execSQL("DROP TABLE IF EXISTS " + mClassName);
            LocaleStore.obtain().put(mClassName, "");
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public String getmClassName() {
        return mClassName;
    }

    /**
     * Getter function for mObjectId
     * @return returns the unique id of the object
     */
    public String getmObjectId() {
        return mObjectId;
    }

    public void setmObjectId(String mObjectId) {
        this.mObjectId = mObjectId;
        mIsSynced = true;
    }

    /**
     * Function set the unique key map, this further calls {@link #put(String, String)}
     * @param isUnique decides the key is unique or not
     */
    public void put(String key, String value, boolean isUnique){
        if(mUniqueKeyMap == null){
            mUniqueKeyMap = new HashMap<>();
        }
        mUniqueKeyMap.put(key, isUnique);
        put(key, value);
    }

    /**
     * Function set the List<CoreObject> to the #mArrayMap
     *
     * @param key the key against which the object will be put
     * @param value the objects to be saved against the key
     */
    public void put(String key, List<CoreObject> value){
        if(mArrayMap == null){
            mArrayMap = new HashMap<String, List<CoreObject>>();
        }
        mArrayMap.put(key, value);
    }

    /**
     * function returns the list of objects against the key
     *
     * @param key the key against which the list needs to be fetched
     * @return returns the list of objects saved against key
     */
    public List<CoreObject> getList(String key){
        if(mArrayMap == null)
            return null;
        return mArrayMap.get(key);
    }

    /**
     * Function save the value against the key provided update the values only if the content is not matching with the
     * existing content otherwise returned false
     * @param key of type String, The key against which the value should be saved
     * @param value of type Int, The value which needs to be saved against key
     */
    @Override
    public boolean put(String key, int value){
        mIntegerMap = (mIntegerMap == null) ? new HashMap<String, Integer>() :
                mIntegerMap;
        if(mIntegerMap.get(key) != null && mIntegerMap.get(key).equals(value)){
            return false;
        }
        mIntegerMap.put(key, value);
        mIsDirty = true;
        return true;
    }

    /**
     * Function save the value against the key provided update the values only if the content is not matching with the
     * existing content otherwise returned false
     * @param key of type String, The key against which the value should be saved
     * @param value of type Float, The value which needs to be saved against key
     */
    @Override
    public boolean put(String key, float value){
        mFloatMap = (mFloatMap == null) ? new HashMap<String, Float>() :
                mFloatMap;
        if(mFloatMap.get(key) != null && mFloatMap.get(key).equals(value)) {
            return false;
        }
        mFloatMap.put(key, value);
        mIsDirty = true;
        return true;
    }

    /**
     * Function save the value against the key provided,
     * update the values only if the content is not matching with the
     * existing content otherwise returned false
     * @param key of type String, The key against which the value should be saved
     * @param value of type String, The value which needs to be saved against key
     */
    @Override
    public boolean put(String key, String value){
        mStringMap = (mStringMap == null) ? new HashMap<String, String>() :
                mStringMap;
        if(mStringMap.get(key) != null && mStringMap.get(key).equals(value)) {
            return false;
        }
        mStringMap.put(key, value);
        mIsDirty = true;
        return true;
    }

    /**
     * Function save the value against the key provided
     * @param key of type String, The key against which the value should be saved
     * @param value of type CoreObject, The value which needs to be saved against key
     */
    public boolean put(String key, CoreObject value){
        mObjectMap = (mObjectMap == null) ? new HashMap<String, CoreObject>() :
                mObjectMap;
        mObjectMap.put(key, value);
        mIsDirty = true;
        return true;
    }

    /**
     * Function save the value against the key provided
     *
     * @param key   of type String, The key against which the value should be saved
     * @param value of type boolean, The value which needs to be saved against key
     */
    @Override
    public boolean put(String key, boolean value) {
        return false;
    }

    /**
     * Function return the value which is saved against the key
     *
     * @param key      of type String
     * @param fallBack value needs to be send if the value not present
     * @return of type String
     */
    @Override
    public String getString(String key, String fallBack) {
        return (mStringMap == null) ? fallBack : mStringMap.get(key);
    }

    /**
     * Function return the value which is saved against the key
     *
     * @param key      of type String
     * @param fallBack value needs to be send if the value not present
     * @return of type float
     */
    @Override
    public float getFloat(String key, float fallBack) {
        return (mFloatMap == null) ? fallBack : mFloatMap.get(key);
    }

    /**
     * Function return the value which is saved against the key
     *
     * @param key      of type String
     * @param fallBack value needs to be send if the value not present
     * @return of type boolean
     */
    @Override
    public boolean getBoolean(String key, boolean fallBack) {
        return false;
    }

    /**
     * Function return the value which is saved against the key
     *
     * @param key      of type String
     * @param fallBack value needs to be send if the value not present
     * @return of type int
     */
    @Override
    public int getInteger(String key, int fallBack) {
        return (mIntegerMap == null) ? null : mIntegerMap.get(key);
    }

    /**
     * function returns the mapped object from  the mObjectMap
     *
     * @param key against the object should be obtained
     * @return the mapped object
     */
    public CoreObject getObject(String key){
        return (mObjectMap == null) ? null : mObjectMap.get(key);
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mClassName);
        dest.writeString(mObjectId);
        dest.writeLong(mUpdatedTime);
        dest.writeLong(mCreatedTime);
        dest.writeInt(mIsDirty ? 1 : 0);
        dest.writeInt(mIsSynced ? 1 : 0);
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * function which used by child classes to add default column values
     *
     * @param cv in which the values to be saved
     * @return return the modified ContentValues
     */
    protected ContentValues overrideColumValues(ContentValues cv){
        return cv;
    }

    /**
     * function return all the columns to be inserted or updated
     *
     * @return the total number of column for the db transaction
     */
    int getAllColumnCount(){
        int count = 0;
        if(mObjectMap != null)
            count += mObjectMap.size();
        if(mStringMap != null)
            count += mStringMap.size();
        return count;
    }

    /**
     * function which decides to create the table for the object or not
     *
     * @param db the DataBase against which the table to be added
     * @throws {@code com.travelyaari.tycorelib.orm.TableModificationException}
     */
    private void createOrAlterTable(SQLiteDatabase db) throws TableModificationException{
        String columns = LocaleStore.obtain().getString(mClassName, "");
        if(columns.isEmpty()){
            String table = "create table if not exists " + mClassName + " (_id varchar primary key not null unique";
            if(mFloatMap != null){
                for(String key : mFloatMap.keySet()){
                    table += " , " + key + " real";
                }
            }
            if(mStringMap != null){
                for(String key : mStringMap.keySet()){
                    boolean unique = (mUniqueKeyMap != null && mUniqueKeyMap.containsKey(key)
                            && mUniqueKeyMap.get(key));
                    table += " , " + key + " text" + (!unique ? "" : " unique");
                }
            }

            if(mIntegerMap != null){
                for(String key : mIntegerMap.keySet()){
                    table += " , " + key + " integer";
                }
            }



            table = addDefaultColumns(table);
            if(mObjectMap != null){
                String keyMap = "";
                String foreignKeyMap = "";
                for(String key : mObjectMap.keySet()){
                    CoreObject object = mObjectMap.get(key);
                    keyMap += ", " + key + " varchar";
                    foreignKeyMap += " , FOREIGN KEY (" + key + ") REFERENCES " + object.getmClassName() + "("
                            + Constants._ID   + ") ON DELETE CASCADE";
                }
                table += keyMap + foreignKeyMap + ")";
            }else{
                table += ")";
            }
            try {
                db.execSQL(table);
                LocaleStore.obtain().put(mClassName, getObjectColumns());
            }catch (SQLException e){
                throw new TableModificationException(e.getMessage());
            }
        }else{
            String alterStatement = "";
            if(mIntegerMap != null) {
                for (String key : mIntegerMap.keySet()) {
                    columns = alterOrAppendColumn(columns, key, Cursor.FIELD_TYPE_INTEGER, db);
                }
            }
            if(mStringMap != null) {
                for (String key : mStringMap.keySet()) {
                    columns = alterOrAppendColumn(columns, key, Cursor.FIELD_TYPE_STRING, db);
                }
            }
            if(mFloatMap != null) {
                for (String key : mFloatMap.keySet()) {
                    columns = alterOrAppendColumn(columns, key, Cursor.FIELD_TYPE_FLOAT, db);
                }
            }
            if(mObjectMap != null) {
                for (String key : mObjectMap.keySet()) {
//                    if(!columns.contains(key)) {
                    if(!columnsContainsKey(columns,key)) {
                        columns += (columns.isEmpty()) ? key : "," + key;
                        alterStatement = "ALTER TABLE " + mClassName + " ADD COLUMN ";
                        CoreObject object = mObjectMap.get(key);
                        String keyMap = key + " varchar";
                        String foreignKeyMap = " FOREIGN KEY (" + key + ") REFERENCES " + object.getmClassName() + "("
                                + Constants._ID + ")";
                        alterStatement += keyMap + foreignKeyMap + ")";
                        alterTable(alterStatement, db, columns);
                    }
                }
            }
        }
    }

    private boolean columnsContainsKey (String columns,String key){
        boolean contains = false;
        String arr[] = columns.split(",");
        for (int i=0;i<arr.length;i++){
            if (arr[i].equals(key)){
                contains = true;
            }
        }
        return contains;
    }

    /**
     * Function execute the db statement
     *
     * @param statement the statement to be executed
     * @param db the db instance against which statement to be executed
     * @param columns the columns to be mapped to {@link android.content.SharedPreferences}
     *                to compare latter
     * @throws {@code TableModificationException}
     */
    private void alterTable(String statement, SQLiteDatabase db, String columns) throws TableModificationException{
        try{
            db.execSQL(statement);
            LocaleStore.obtain().put(mClassName, columns);
        }catch (SQLException e){
            throw new TableModificationException(e.getMessage());
        }
    }

    /**
     * Function create a alter command or append columns to it
     * @param columns the existing columns
     * @param key the key to be checked against the existing columns
     * @param type type of the column
     * @throws {@code TableModificationException}
     */
    private String alterOrAppendColumn(String columns,
                                       String key, int type, SQLiteDatabase db) throws TableModificationException{
//        if(!columns.contains(key)){
        if (!columnsContainsKey(columns, key)) {
            columns += (columns.isEmpty()) ? key : "," + key;
            String statement = "ALTER TABLE " +
                    mClassName + " ADD COLUMN ";
            switch (type) {
                case Cursor.FIELD_TYPE_INTEGER: {
                    statement += key + " integer";
                    break;
                }
                case Cursor.FIELD_TYPE_FLOAT: {
                    statement += key + " real";
                    break;
                }
                case Cursor.FIELD_TYPE_STRING: {
                    boolean unique = (mUniqueKeyMap != null && mUniqueKeyMap.containsKey(key)
                            && mUniqueKeyMap.get(key));
                    statement += key + " text" + (!unique ? "" : " unique");
                    break;
                }
            }
            alterTable(statement, db, columns);
        }
        return columns;
    }

    /**
     * function which delete the record from the Database
     *
     * @throws com.travelyaari.tycorelib.orm.Exceptions.DeleteException
     */
    public void delete() throws Exceptions.DeleteException {
        delete(new String[]{mObjectId}, mClassName);
    }

    /**
     * Save the content to the DB, check the existence of the table and create if not created
     * if the object is not dirty this would not initiate the save
     * @return true on save success otherwise false
     */
    public boolean save() throws TableModificationException{
        if(mIsDirty){
            SQLiteDatabase db = CoreLib.getDbHelper().getWritableDatabase();
            createOrAlterTable(db);
            ContentValues cv = new ContentValues();
            try {
                if (!mIsSynced) {
                    mCreatedTime = new Date().getTime();
                    mUpdatedTime = new Date().getTime();
                    mObjectId = Long.toHexString(System.nanoTime());
                    cv.put(Constants._ID, mObjectId);
                    cv = setMapValues(cv);
                    cv.put(Constants._createdAt, mCreatedTime);
                    cv.put(Constants._updatedAt, mUpdatedTime);
                    cv = overrideColumValues(cv);
                    mIsSynced = (db.insertOrThrow(mClassName, null, cv) > 0);
                    mIsDirty = !mIsSynced;
                    return mIsSynced;
                } else {
                    mUpdatedTime = new Date().getTime();
                    cv = setMapValues(cv);
                    cv.put(Constants._updatedAt, mUpdatedTime);
                    cv = overrideColumValues(cv);
                    mIsSynced = (db.update(mClassName, cv, Constants._ID + "  = ? ", new String[]{mObjectId}) > 0);
                    mIsDirty = !mIsSynced;
                    return mIsSynced;
                }
            }catch(SQLiteConstraintException e){
                throw new TableModificationException(e.getMessage());
            }

        }
        return false;
    }

    /**
     * Function add the default columns to the table creation query
     *
     * @param tableQuery the query to which the default columns to be appended
     * @return return the cobined string with default columns
     */
    protected String addDefaultColumns(String tableQuery){
        return tableQuery + ", " + Constants._createdAt + " integer not null, " +
                Constants._updatedAt + " integer not null";
    }

    /**
     * Function set the values to the content values from the
     * primitive hashmaps
     * @param cv
     * @return
     */
    private ContentValues setMapValues(ContentValues cv){
        if(mFloatMap != null) {
            for (String key : mFloatMap.keySet()) {
                cv.put(key, mFloatMap.get(key));
            }
        }
        if(mStringMap != null) {
            for (String key : mStringMap.keySet()) {
                cv.put(key, mStringMap.get(key));
            }
        }
        if(mIntegerMap != null) {
            for (String key : mIntegerMap.keySet()) {
                cv.put(key, mIntegerMap.get(key));
            }
        }
        if(mObjectMap != null){
            for (String key : mObjectMap.keySet()) {
                cv.put(key, mObjectMap.get(key).mObjectId);
            }
        }
        return cv;
    }

    /**
     *
     * @return the all columns belongs to this object
     */
    protected String getObjectColumns(){
        String columns = "";
        if(mIntegerMap != null) {
            for (String key : mIntegerMap.keySet()) {
                columns += (columns.isEmpty()) ? key : "," + key;
            }
        }
        if(mStringMap != null) {
            for (String key : mStringMap.keySet()) {
                columns += (columns.isEmpty()) ? key : "," + key;
            }
        }
        if(mFloatMap != null) {
            for (String key : mFloatMap.keySet()) {
                columns += (columns.isEmpty()) ? key : "," + key;
            }
        }
        if(mObjectMap != null) {
            for (String key : mObjectMap.keySet()) {
                columns += (columns.isEmpty()) ? key : "," + key;
            }
        }
        columns += ((columns.isEmpty()) ? "" : ",") + Constants._ID + "," +
                Constants._updatedAt + "," + Constants._createdAt;
        return columns;
    }

    /**
     * return the set of key values for the object from all primitive types
     * this will be further used to create or later the table
     */
    private Set<String> getAllKeys(){
        Set<String> mKeySet = null;
        return  mKeySet;
    }

    /**
     * Check if the column is a default column or not
     * @param column of type String against which the check should happen
     * @return of type boolean
     */
    protected boolean isDefaultColumns(String column){
        return (column.equals(Constants._ID) || column.equals(Constants._createdAt) ||
                column.equals(Constants._updatedAt));
    }

    /**
     * Function which will fetch the content from the database
     * if the fetch is performed the content would not be fetched also it checks the
     * existence of table in the preference and then initiate the fetch
     *
     * on success full fetch this set mIsDirty to false and mFetched to true
     * <b>This fetch happens on the main thread<b/>
     */
    public void fetch(){
        if(!mFetched && LocaleStore.obtain().getString(mClassName, null) != null){
            SQLiteDatabase db = CoreLib.getDbHelper().getReadableDatabase();
            Cursor cursor = db.query(mClassName, null, Constants._ID + " = ? ",
                    new String[]{mObjectId}, null, null, null);
            if(null != cursor && cursor.moveToFirst()){
                for(int i = 0; i < cursor.getColumnCount(); i++){
                    if(!isDefaultColumns(cursor.getColumnNames()[i])) {
                        if (cursor.getType(i) == Cursor.FIELD_TYPE_FLOAT) {
                            put(cursor.getColumnName(i), (float)cursor.getFloat(i));
                        } else if (cursor.getType(i) == Cursor.FIELD_TYPE_INTEGER) {
                            put(cursor.getColumnName(i), (int)cursor.getInt(i));
                        } else {
                            put(cursor.getColumnName(i), cursor.getString(i));
                        }
                    }
                }
                cursor.close();
                mIsSynced = true;
                mFetched = true;
                mIsDirty = false;
            }
        }
    }
}
