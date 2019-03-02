package com.travelyaari.tycorelib.fragments;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Triode on 6/6/2016.
 */
public class FragmentState implements Parcelable {

    /**
     *
     */
    public static final Creator<FragmentState> CREATOR
            = new Creator<FragmentState>() {
        public FragmentState createFromParcel(Parcel in) {
            return new FragmentState(in);
        }

        public FragmentState[] newArray(int size) {
            return new FragmentState[size];
        }
    };


    /**
     * Constructs a new instance of {@code Object}.
     */
    public FragmentState() {
        super();
    }

    /**
     * Constructs a new instance of {@code Object}.
     */
    public FragmentState(Parcel source) {
        super();
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

    }
}
