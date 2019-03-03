package com.app.pratilipi.contactapp.contacts.contactdetails;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContactDetailState implements Parcelable {

    String mPhoneNumber;
    List<String> mEmailList;


    public ContactDetailState() {

    }

    public static final Creator<ContactDetailState> CREATOR = new Creator<ContactDetailState>() {
        @Override
        public ContactDetailState createFromParcel(Parcel in) {
            return new ContactDetailState(in);
        }

        @Override
        public ContactDetailState[] newArray(int size) {
            return new ContactDetailState[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    protected ContactDetailState(Parcel source) {
        mPhoneNumber = source.readString();
        mEmailList = source.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mPhoneNumber);
        parcel.writeStringList(mEmailList);

    }


    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public List<String> getmEmailList() {
        return mEmailList;
    }

    public void setmEmailList(List<String> mEmailList) {
        this.mEmailList = mEmailList;
    }
}
