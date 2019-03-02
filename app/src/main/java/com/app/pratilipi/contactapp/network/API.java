package com.app.pratilipi.contactapp.network;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.app.pratilipi.contactapp.contacts.ContactItemVO;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class API {

    /**
     * function returns the contacts request observable
     *
     * @return the created Observable
     */
    public static Observable<List<ContactItemVO>> getContactsObservable(final Context context) {
        return Observable.create(
                new Observable.OnSubscribe<List<ContactItemVO>>() {
                    @Override
                    public void call(Subscriber<? super List<ContactItemVO>> sub) {
                        try {
                            List<ContactItemVO> contactItemList = new ArrayList<>();
                            Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, "UPPER(" + ContactsContract.Contacts.DISPLAY_NAME + ") ASC");
                            while (phones.moveToNext()) {
                                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                ContactItemVO contactItemVO = new ContactItemVO();
                                contactItemVO.setmName(name);
                                contactItemVO.setmNumber(phoneNumber);
                                contactItemList.add(contactItemVO);
                            }
                            phones.close();
                            sub.onNext(contactItemList);
                            sub.onCompleted();
                        } catch (Exception e) {
                            sub.onError(e);
                        }
                    }
                }
        ).retryWhen(new RetryWhenObservable(5))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
