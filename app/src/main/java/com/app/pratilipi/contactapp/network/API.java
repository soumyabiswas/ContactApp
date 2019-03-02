package com.app.pratilipi.contactapp.network;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.app.pratilipi.contactapp.contacts.ContactItemVO;
import com.app.pratilipi.contactapp.contacts.contactdetails.ContactDetailState;

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
                            String order = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
                            String[] projection = new String[] {
                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                    ContactsContract.CommonDataKinds.Phone.PHOTO_URI

                            };

                            Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, order);

                            while (cursor.moveToNext()) {
                                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                               // String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                String photoUri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                                ContactItemVO contactItemVO = new ContactItemVO();
                                contactItemVO.setmName(name);
                              //  contactItemVO.setmNumber(phone);
                                contactItemVO.setmImageUrl(photoUri);
                                contactItemList.add(contactItemVO);

                            }
                            cursor.close();
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



    /**
     * function returns the contacts details request observable
     *
     * @return the created Observable
     */
    public static Observable<ContactDetailState> getContactDetailsObservable(final Context context) {
        return Observable.create(
                new Observable.OnSubscribe<ContactDetailState>() {
                    @Override
                    public void call(Subscriber<? super ContactDetailState> sub) {
                        try {
                            ContactDetailState state = new ContactDetailState();

                            String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
                                    + ("1") + "'";
                            String order = ContactsContract.Contacts.DISPLAY_NAME
                                    + " COLLATE LOCALIZED ASC";
                            String[] projection = new String[] {
                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                    ContactsContract.CommonDataKinds.Phone.PHOTO_URI,

                            };

                            Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, selection+ " AND " + ContactsContract.Contacts.HAS_PHONE_NUMBER
                                    + "=1", null, order);

                            while (cursor.moveToNext()) {
                                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                // String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                String photoUri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));



                            }
                            cursor.close();
                            sub.onNext(state);
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
