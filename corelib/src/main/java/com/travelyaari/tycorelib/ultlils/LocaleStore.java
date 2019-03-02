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

package com.travelyaari.tycorelib.ultlils;

import android.content.Context;
import android.content.SharedPreferences;

import com.travelyaari.tycorelib.CoreLib;
import com.travelyaari.tycorelib.primitives.IGetSet;


/**
 * Created by Triode on 5/5/2016.
 *
 * The class responsible for saving contents to the {@link SharedPreferences}
 */
public class LocaleStore implements IGetSet {


    /**
     * Holds the singleton instance of the class
     */
    private static LocaleStore mInstance;

    /**
     * Getter function for mInstance
     *
     * @return mInstance of type HLPreferenceUtils
     */
    public static LocaleStore obtain(){
        mInstance = (mInstance == null) ? new LocaleStore() : mInstance;
        return mInstance;
    }

    /**
     * return the Editor object against which editing will be done
     * @return SharedPreferences.Editor
     */
    public static SharedPreferences.Editor obtainEditor(){
        final Context context = CoreLib.getAppContext();
        return  context.
                getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).edit();
    }

    /**
     * Return the preferences with name
     * @return of type SharedPreferences
     */
    public static SharedPreferences obtainPreferences(){
        final Context context = CoreLib.getAppContext();
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }




    /**
     * Function save the value against the key provided
     *
     * @param key   of type String, The key against which the value should be saved
     * @param value of type String, The value which needs to be saved against key
     */
    @Override
    public boolean put(String key, String value) {
        return obtainEditor().putString(key, value).commit();
    }

    /**
     * Function save the value against the key provided
     *
     * @param key   of type String, The key against which the value should be saved
     * @param value of type float, The value which needs to be saved against key
     */
    @Override
    public boolean put(String key, float value) {
        return obtainEditor().putFloat(key, value).commit();
    }

    /**
     * Function save the value against the key provided
     *
     * @param key   of type String, The key against which the value should be saved
     * @param value of type int, The value which needs to be saved against key
     */
    @Override
    public boolean put(String key, int value) {
        return obtainEditor().putInt(key, value).commit();
    }

    /**
     * Function save the value against the key provided
     *
     * @param key   of type String, The key against which the value should be saved
     * @param value of type boolean, The value which needs to be saved against key
     */
    @Override
    public boolean put(String key, boolean value) {
        return obtainEditor().putBoolean(key, value).commit();
    }

    /**
     * Function return the value which is saved against the key
     *
     * @param key of type String
     * @param fallBack value needs to be send if the value not present
     * @return of type int
     */
    @Override
    public int getInteger(String key, int fallBack) {
        return obtainPreferences().getInt(key, fallBack);
    }

    /**
     * Function return the value which is saved against the key
     *
     * @param key of type String
     * @param fallBack value needs to be send if the value not present
     * @return of type String
     */
    @Override
    public String getString(String key, String fallBack) {
        return obtainPreferences().getString(key, fallBack);
    }

    /**
     * Function return the value which is saved against the key
     *
     * @param key of type String
     * @param fallBack value needs to be send if the value not present
     * @return of type float
     */
    @Override
    public float getFloat(String key, float fallBack) {
        return obtainPreferences().getFloat(key, fallBack);
    }

    /**
     * Function return the value which is saved against the key
     *
     * @param key of type String
     * @param fallBack value needs to be send if the value not present
     * @return of type boolean
     */
    @Override
    public boolean getBoolean(String key, boolean fallBack) {
        return obtainPreferences().getBoolean(key, fallBack);
    }
}
