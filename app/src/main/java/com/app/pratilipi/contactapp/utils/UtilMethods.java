package com.app.pratilipi.contactapp.utils;


import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;

import com.app.pratilipi.contactapp.Constants;

public class UtilMethods {



    /**
     * Checks if the current api level is at least the provided value.
     *
     * @param apiLevel One of the values within {@link Build.VERSION_CODES}.
     * @return <code>true</code> if the calling version is at least <code>apiLevel</code>.
     * Else <code>false</code> is returned.
     */
    public static boolean ApiIsAtLeast(int apiLevel) {
        return Build.VERSION.SDK_INT >= apiLevel;
    }

    /**
     * Checks if the current api level is at lower than the provided value.
     *
     * @param apiLevel One of the values within {@link Build.VERSION_CODES}.
     * @return <code>true</code> if the calling version is lower than <code>apiLevel</code>.
     * Else <code>false</code> is returned.
     */
    public static boolean ApiIsLowerThan(int apiLevel) {
        return Build.VERSION.SDK_INT < apiLevel;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

    public static String getConnectionType(Context activity) {
        final ConnectivityManager connMgr = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);


        final NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (null != wifi && wifi.isAvailable()
                && wifi.getDetailedState() == NetworkInfo.DetailedState.CONNECTED && wifi.isConnected()) {

            return Constants.CONN_WIFI;
        } else if (null != mobile && mobile.isAvailable()
                && mobile.getDetailedState() == NetworkInfo.DetailedState.CONNECTED && mobile.isConnected()) {
            return Constants.CONN_MOBILE;
        } else {
            return Constants.CONN_NONE;
        }
    }


}
