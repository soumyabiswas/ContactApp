package com.travelyaari.tycorelib.primitives;

/**
 * Created by Triode on 5/10/2016.
 */
public interface ILoginView {

    /**
     * shows the login page
     */
    void showLogin();

    /**
     * shows the loading screen
     */
    void showLoading();

    /**
     * show the error page
     */
    void showError(final LoginError error);

    /**
     * function will be called on successful login
     */
    void onSuccess();

    /**
     * the possible errors
     */
    public static enum LoginError {
        USER_NAME_ERROR,
        PASSWORD_ERROR,
        EMAIL_ERROR,
        SERVER_ERROR,
        NETWORK_ERROR
    }
}
