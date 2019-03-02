package com.travelyaari.tycorelib.primitives;

import androidx.annotation.UiThread;

import com.travelyaari.tycorelib.Constants;

/**
 * Created by Triode on 5/16/2016.
 */
public interface ILoadingView<T> {


    /**
     * Function to the loading view
     */
    @UiThread
    void showLoading();

    /**
     * function hide the loading screen
     */
    @UiThread
    void hideLoading();


    /**
     * functions shows the error if the loading fails
     */
    @UiThread
    void showError(final Constants.ErrorCodes error);

    /**
     * function which will show the result in the view
     *
     * @param result the result to be shown
     */
    @UiThread
    void showResult(T result);
}
