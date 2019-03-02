package com.app.pratilipi.contactapp.contacts.contactdetails;

import android.os.Parcel;
import android.os.Parcelable;

import com.app.pratilipi.contactapp.contacts.ContactItemVO;
import com.app.pratilipi.contactapp.contacts.ContactListState;

import java.util.List;

public class ContactDetailState implements Parcelable {



    String mName;
    String mImgUrl;
    List<String> mPhoneNumbers;
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
        mName= source.readString();
        mImgUrl= source.readString();
        mPhoneNumbers = source.createStringArrayList();
        mEmailList = source.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mImgUrl);
        parcel.writeStringList(mPhoneNumbers);
        parcel.writeStringList(mEmailList);

    }


    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImgUrl() {
        return mImgUrl;
    }

    public void setmImgUrl(String mImgUrl) {
        this.mImgUrl = mImgUrl;
    }

    public List<String> getmPhoneNumbers() {
        return mPhoneNumbers;
    }

    public void setmPhoneNumbers(List<String> mPhoneNumbers) {
        this.mPhoneNumbers = mPhoneNumbers;
    }

    public List<String> getmEmailList() {
        return mEmailList;
    }

    public void setmEmailList(List<String> mEmailList) {
        this.mEmailList = mEmailList;
    }
}
