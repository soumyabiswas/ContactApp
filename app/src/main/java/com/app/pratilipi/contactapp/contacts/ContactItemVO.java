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
    String mImageUrl;



    protected ContactItemVO(Parcel in) {
        mName = in.readString();
        mImageUrl = in.readString();
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
        dest.writeString(mImageUrl);

    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }






}
