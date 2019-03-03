package com.app.pratilipi.contactapp.contactdetails;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ContactDetailState implements Parcelable {


    List<String> mEmailList;
    List<String> mPhoneList;


    public ContactDetailState() {
        mPhoneList = new ArrayList<>();
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
        mEmailList = source.createStringArrayList();
        mPhoneList = source.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(mEmailList);
        parcel.writeStringList(mPhoneList);

    }

    public List<String> getmEmailList() {
        return mEmailList;
    }

    public void setmEmailList(List<String> mEmailList) {
        this.mEmailList = mEmailList;
    }

    public List<String> getmPhoneList() {
        return mPhoneList;
    }

    public boolean containsPhone(String phoneNumber) {
        for (String phone: mPhoneList) {
            if (phone.replace(" ", "").equals(phoneNumber.replace(" ", ""))){
                return true;
            }
        }
        return false;
    }

    public void addPhoneNumber(String phone) {
        this.mPhoneList.add(phone);
    }
}
