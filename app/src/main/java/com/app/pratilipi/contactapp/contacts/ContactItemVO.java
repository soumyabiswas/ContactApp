package com.app.pratilipi.contactapp.contacts;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by soumya on 6/3/18.
 */
public class ContactItemVO implements Parcelable {
    public static final Creator<ContactItemVO> CREATOR = new Creator<ContactItemVO>() {
        @Override
        public ContactItemVO createFromParcel(Parcel in) {
            return new ContactItemVO(in);
        }

        @Override
        public ContactItemVO[] newArray(int size) {
            return new ContactItemVO[size];
        }
    };
    String mName;
    String mNumber;


    protected ContactItemVO(Parcel in) {
        mName = in.readString();
        mNumber = in.readString();

    }

    public ContactItemVO() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mNumber);

    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }



}