/*
 *  Copyright (c) 2016, Travelyaari and/or its affiliates. All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions
 *   are met:
 *
 *     - Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     - Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 *     - Neither the name of Travelyaari or the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 *   IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 *   THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *   PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 *   CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *   EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *   PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *   PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *   LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *   NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package com.travelyaari.tycorelib;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import com.travelyaari.tycorelib.events.EventDispatcher;
import com.travelyaari.tycorelib.orm.CoreDBImplementation;
import com.travelyaari.tycorelib.orm.IDBChanges;
import com.travelyaari.tycorelib.ultlils.LocaleStore;
import com.travelyaari.tycorelib.ultlils.PropertyReader;

import java.lang.ref.WeakReference;



/**
 * Created by Triode on 5/5/2016.
 * The entry point for the library, Keeps track of all common
 * SingleTon class instances
 */
public final class CoreLib {

    /**
     * Holds the instance of event dispatcher
     */
    private static WeakReference<EventDispatcher> mDispatcher;
    /**
     * Variable decides the app in debug mode or not
     */
    private static boolean mIsDebug;
    /**
     * Holds the property loader instance
     */
    private static PropertyReader mPropertyReader;
    /**
     * holds the instance of the {@link LocaleStore}
     */
    private static WeakReference<LocaleStore> mLocaleStore;
    /**
     * Holds the application context, this will accessible from any where in the app
     */
    private static Context mApplicationContext;
    private static CoreDBImplementation dbHelper;

    /**
     * Constructs a new instance of {@code Object}.
     */
    private CoreLib() {
        super();
    }

    /**
     * getter function for dispatcher
     * @return {@code mDispatcher#get}
     */
    public static EventDispatcher getmDispatcher() {
        return mDispatcher.get();
    }

    /**
     * access function for member mIsDebug
     *
     * @return the value of mIsDebug
     */
    public static boolean ismIsDebug() {
        return mIsDebug;
    }

    /**
     * getter function for {@code mPropertyReader}
     *
     * @return the instance of #mPropertyReader
     */
    public static PropertyReader getmPropertyReader() {
        return mPropertyReader;
    }

    /**
     * getter function for {@code mLocaleStore}
     *
     * @return the instance of #mLocaleStore
     */
    public static LocaleStore getmLocaleStore() {
        return mLocaleStore.get();
    }

    /**
     * Getter function for mApplicationContext
     * @return of type Context, returns the mApplicationContext instance
     */
    public static final Context getAppContext(){
        return mApplicationContext;
    }

    /**
     * Starting point of the cor-lib if this is not called this will leads to exceptions
     *
     * @param mApplicationContext not null of type Context, this will be saved in mApplicationContext
     * @param environment the mode the application to be launched, based on this value config
     *                    files under assets will be selected.
     *                    Possible values are {@link com.travelyaari.tycorelib.Constants.Environments}
     */
    public static final void init(@NonNull final Context mApplicationContext,
                                  @NonNull final Constants.Environments environment){
        CoreLib.mApplicationContext = mApplicationContext;
        initAppConfig(environment);
        mLocaleStore = new WeakReference<LocaleStore>(LocaleStore.obtain());
        mDispatcher  = new WeakReference<EventDispatcher>(EventDispatcher.acquire());
    }

    public static CoreDBImplementation getDbHelper() {
        return dbHelper;
    }

    /**
     * function initialize the DB creation
     * @param callback the callback listening for DB changes
     */
    public static final void initDB(final IDBChanges callback){
        dbHelper = CoreDBImplementation.obtain(callback);
    }

    /**
     * function initialise the loading of app config file under assets
     *
     * @param environment the mode the application to be launched, based on this value config
     *                    files under assets will be selected.
     *                    Possible values are {@link com.travelyaari.tycorelib.Constants.Environments}
     */
    public static final void initAppConfig(final Constants.Environments environment){
        mPropertyReader = new PropertyReader(getPropertyFileName(environment));
        mIsDebug = (environment.equals(Constants.Environments.DEV_ENV));
    }

    public static final void destroy(){
        mPropertyReader = null;
        mApplicationContext = null;
        mDispatcher = null;
        mLocaleStore = null;
        dbHelper.mDBChangeCallback = null;
        dbHelper = null;
    }


    /**
     * function return the proper path based on the environment passed
     *
     * @param environment the environment app is running
     * @return the string file name
     */
    private static String getPropertyFileName(@NonNull final Constants.Environments environment){
        if(environment.equals(Constants.Environments.PRODUCTION_ENV)){
            return "appconfig_production.properties";
        }else if(environment.equals(Constants.Environments.STAGE_ENV)){
            return "appconfig_stage.properties";
        }else{
            return "appconfig_dev.properties";
        }
    }


    /**
     * function return the application version code
     *
     * @return the version code mentioned in gradle
     */
    public static int getAppVersionCode(){
        int versionCode = 123;
        try {
            versionCode = getAppContext().
                    getPackageManager().getPackageInfo(getAppContext().getPackageName(),
                    0).versionCode;
        }catch (PackageManager.NameNotFoundException e){
        }
        return versionCode;
    }
}
