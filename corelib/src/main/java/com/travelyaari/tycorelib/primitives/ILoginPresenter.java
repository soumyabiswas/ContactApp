package com.travelyaari.tycorelib.primitives;

/**
 * Created by Triode on 5/10/2016.
 */
public interface ILoginPresenter<T> {
    /**
     * function will proceed with login
     *
     * @param params the params needed for login
     */
    void doLogin(final T params);
}
