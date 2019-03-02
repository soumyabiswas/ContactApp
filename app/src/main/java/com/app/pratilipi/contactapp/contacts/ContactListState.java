package com.app.pratilipi.contactapp.contacts;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContactListState implements Parcelable {

    List<ContactItemVO> mContactVOList;

    public ContactListState() {

    }

    public static final Creator<ContactListState> CREATOR = new Creator<ContactListState>() {
        @Override
        public ContactListState createFromParcel(Parcel in) {
            return new ContactListState(in);
        }

        @Override
        public ContactListState[] newArray(int size) {
            return new ContactListState[size];
        }
    };

    protected ContactListState(Parcel source) {
        mContactVOList = source.createTypedArrayList(ContactItemVO.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(mContactVOList);
    }

    public List<ContactItemVO> getmContactVOList() {
        return mContactVOList;
    }

    public void setmContactVOList(List<ContactItemVO> mContactVOList) {
        this.mContactVOList = mContactVOList;
    }


}
