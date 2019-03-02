package com.travelyaari.tycorelib.orm;



/**
 * Created by Triode on 5/12/2016.
 */
public class ORMModule {


    /**
     * function creates the query and return it
     *
     * @param className the class name
     * @return the created instance of {@code Query}
     */
    public Query getQuery(final String className){
        return new Query(className);
    }

    /**
     * function creates the coreObject and return it
     *
     * @param className the class name
     * @return the created instance of {@code CoreObject}
     */
    public CoreObject getCoreObject(final String className){
        return new CoreObject(className);
    }


}
