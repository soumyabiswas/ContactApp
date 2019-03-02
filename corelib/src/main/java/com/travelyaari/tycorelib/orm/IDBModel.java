package com.travelyaari.tycorelib.orm;

import android.database.Cursor;

/**
 * Created by Triode on 6/15/2016.
 */
public interface IDBModel {

    /**
     * function which parse the corresponding raw which
     * points to
     *
     * @param cursor the cursor obtained
     * @return the {@code java.lang.Object} itself
     */
    void parseCursor(final Cursor cursor);

}
