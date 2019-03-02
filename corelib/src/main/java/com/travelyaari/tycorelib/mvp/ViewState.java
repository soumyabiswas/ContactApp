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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Triode on 4/29/2016.
 */
public class ViewState implements Parcelable {


    public static final int VIEW_STATE_LOADING = 1;
    public static final int VIEW_STATE_COMPLETE = 2;
    public static final int VIEW_STATE_NETWORK_ERROR = 3;
    public static final int VIEW_STATE_SERVER_ERROR = 4;
    public static final int VIEW_STATE_EMPTY_RESULT = 5;


    public int mViewState = 0;


    /**
     * Constructs a new instance of {@code Object}.
     */
    public ViewState() {
        super();
    }

    /**
     *
     */
    public static final Parcelable.Creator<ViewState> CREATOR
            = new Parcelable.Creator<ViewState>() {
        /**
         *
         * @param in
         * @return
         */
        public ViewState createFromParcel(Parcel in) {
            return new ViewState(in);
        }

        /**
         * @param size
         * @return
         */
        public ViewState[] newArray(int size) {
            return new ViewState[size];
        }
    };


    /**
     * function which recreate the object from the Parcel
     * @param in of type Parcel
     */
    public ViewState(Parcel in) {
        mViewState = in.readInt();
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mViewState);
    }
}
