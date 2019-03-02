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

package com.travelyaari.tycorelib.mvp;

import androidx.fragment.app.Fragment;

import androidx.annotation.UiThread;

import java.lang.ref.WeakReference;


/**
 * Created by Triode on 5/5/2016.
 */
public class BasePresenter<V extends MVPView> implements MVPPresenter<V> {


    /**
     * Holds the view reference
     */
    protected WeakReference<V> mViewRef;

    /**
     * Constructs a new instance of {@code Object}.
     */
    public BasePresenter() {
        super();
    }

    /**
     * function to be called to set the view
     *
     * @param view the view associated with the presenter
     */
    @Override
    public void takeView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    /**
     * function release the view from the presenter there by blocking
     * all updates to the view. Possible call from
     * {@link android.app.Activity#onDetachedFromWindow()} or
     * {@link Fragment#onDestroyView()}
     */
    @Override
    public void releaseView() {
        if(mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
    }

    /**
     * function which will be called {@link android.app.Activity#onDestroy()} or
     * {@link Fragment#onDestroy()}
     */
    @Override
    public void onDestroy() {

    }

    /**
     * function returns the enclosing {@code V} instance
     *
     * @return {@code V} instance
     */
    @UiThread
    public V getView(){
        return mViewRef == null ? null : mViewRef.get();
    }

    /**
     * function returns if the presenter has an active view
     * Highly sensitive method when it comes to updating the view
     *
     * @return true if the {@code mViewRef} is not NULL otherwise flase
     */
    @UiThread
    public boolean isReleased(){
        return (mViewRef == null || mViewRef.get() == null);
    }
}
