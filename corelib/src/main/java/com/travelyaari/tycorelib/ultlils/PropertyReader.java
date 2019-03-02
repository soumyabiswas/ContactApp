package com.travelyaari.tycorelib.ultlils;


import androidx.annotation.NonNull;

import com.travelyaari.tycorelib.CoreLib;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by Triode on 6/16/2016.
 */
public final class PropertyReader {


    /**
     * Holds the reference to the property object
     */
    private Properties mProperties;

    /**
     * Constructs a new instance of {@code Object}.
     *
     * @param propertyFileName the name of the property file
     */
    public PropertyReader(final String propertyFileName) {
        super();
       loadProperties(propertyFileName);
    }


    /**
     * function loads the property file to memory and save it
     * @param fileName the name of the file
     */
    void loadProperties(final String fileName){
        mProperties = new Properties();
        try {
            InputStreamReader stream = new InputStreamReader(CoreLib.getAppContext().getResources().
                    getAssets().open(fileName));
            mProperties.load(stream);
        }catch (IOException e){
            throw new Error("Make sure you have both properties file(" + fileName + ")present under assets");
        }
    }


    /**
     * Function read the property from the mProperties object
     *
     * @param key the key against which the property should be obtained
     * @return the value obtained against the key
     */
    public final String readProperty(@NonNull final String key){
        return mProperties.getProperty(key);
    }

}
