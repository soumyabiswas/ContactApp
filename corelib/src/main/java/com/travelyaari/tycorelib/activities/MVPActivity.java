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

package com.travelyaari.tycorelib.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.travelyaari.tycorelib.mvp.MVPPresenter;
import com.travelyaari.tycorelib.mvp.MVPView;

/**
 * Created by Triode on 5/5/2016.
 */
public abstract class MVPActivity<V extends MVPView, P extends MVPPresenter<V>> extends MVActivity<V> {
    /**
     * Holds the presenter instance
     */
    protected P mPresenter;


    /**
     * Extending fragments should override this and provide the proper presenter
     * class
     * @return the Class type of the presenter
     */
    protected abstract Class<P> getPresenterClass();

    /**
     * function called right after the creation of Presenter
     */
    protected abstract void onCreatePresenter();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            createAndInitializePresenter();
            onCreatePresenter();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }

    /**
     * function create and initialize the presenter
     *
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    protected final void createAndInitializePresenter()
            throws InstantiationException, IllegalAccessException{
        mPresenter = getPresenterClass().newInstance();
        mPresenter.takeView(mView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.releaseView();
        mPresenter.onDestroy();
        mPresenter = null;
    }
}
