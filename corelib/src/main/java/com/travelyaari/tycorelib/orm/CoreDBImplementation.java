package com.travelyaari.tycorelib.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.travelyaari.tycorelib.CoreLib;



/**
 * Created by Triode on 5/10/2016.
 */
public class CoreDBImplementation extends SQLiteOpenHelper {


    private static CoreDBImplementation mInstance;

    public IDBChanges mDBChangeCallback;



    /**
     * getter function for mInstance
     * @return of type {@code CoreDBImplementation}, return the instance of CoreDBImplementation
     */
    public static final CoreDBImplementation obtain(final IDBChanges mDBChangeCallback){
        if(mInstance == null) {
            Context context = CoreLib.getAppContext();
            int dbName = context.getResources().getIdentifier("db_name", "string", context.getPackageName());
            int dbId   = context.getResources().getIdentifier("db_version", "integer", context.getPackageName());
            mInstance = new CoreDBImplementation(CoreLib.getAppContext(),
                    context.getResources().getString(dbName),
                    null, context.getResources().getInteger(dbId));
            mInstance.mDBChangeCallback = mDBChangeCallback;
        }
        return mInstance;
    }


    /**
     * Called when the database needs to be downgraded. This is strictly similar to
     * {@link #onUpgrade} method, but is called whenever current version is newer than requested one.
     * However, this method is not abstract, so it is not mandatory for a customer to
     * implement it. If not overridden, default implementation will reject downgrade and
     * throws SQLiteException
     * <p/>
     * <p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        if(mDBChangeCallback != null){
            mDBChangeCallback.onDowngrade(db, oldVersion, newVersion);
        }
    }

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     * @param name    of the database file, or null for an in-memory database
     * @param factory to use for creating cursor objects, or null for the default
     * @param version number of the database (starting at 1); if the database is older,
     *                {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public CoreDBImplementation(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Called when the database has been opened.  The implementation
     * should check {@link SQLiteDatabase#isReadOnly} before updating the
     * database.
     * <p>
     * This method is called after the database connection has been configured
     * and after the database schema has been created, upgraded or downgraded as necessary.
     * If the database connection must be configured in some way before the schema
     * is created, upgraded, or downgraded, do it in {@link #onConfigure} instead.
     * </p>
     *
     * @param db The database.
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    /**
     * Return the name of the SQLite database being opened, as given to
     * the constructor.
     */
    @Override
    public String getDatabaseName() {
        return super.getDatabaseName();
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        if(mDBChangeCallback != null){
            mDBChangeCallback.onCreate(db);
        }
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(mDBChangeCallback != null){
            mDBChangeCallback.onUpgrade(db, oldVersion, newVersion);
        }
    }
}
