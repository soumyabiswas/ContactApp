package com.app.pratilipi.contactapp;




import com.travelyaari.tycorelib.Constants;
import com.travelyaari.tycorelib.CoreLib;
import com.travelyaari.tycorelib.TYApplication;




public class AppModule extends TYApplication {

    private static AppModule mModule;
    public final String TAG = AppModule.class.getSimpleName();


    public AppModule() {
        super();
        mModule = this;
    }

    /**
     * getter function for AppModule
     *
     * @return
     */
    public static AppModule getmModule() {
        return mModule;
    }



    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        initCoreLib();
        super.onCreate();

    }

    /**
     * function initialize the corelib
     */
    private void initCoreLib() {
        CoreLib.init(getApplicationContext(), Constants.Environments.PRODUCTION_ENV);

    }


    /**
     * This method is for use in emulated process environments.  It will
     * never be called on a production Android device, where processes are
     * removed by simply killing them; no user code (including this callback)
     * is executed when doing so.
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        CoreLib.destroy();
    }
}
