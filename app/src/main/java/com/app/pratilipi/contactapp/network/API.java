package com.app.pratilipi.contactapp.network;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.app.pratilipi.contactapp.contacts.ContactItemVO;
import com.app.pratilipi.contactapp.contactdetails.ContactDetailState;

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
                            ContentResolver contentResolver = context.getContentResolver();
                            Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, order);

                            while (cursor.moveToNext()) {
                                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                String photoUri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                                ContactItemVO contactItemVO = new ContactItemVO();
                                contactItemVO.setmName(name);
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
    public static Observable<ContactDetailState> getContactDetailsObservable(final Context context, final String contactName) {
        return Observable.create(
                new Observable.OnSubscribe<ContactDetailState>() {
                    @Override
                    public void call(Subscriber<? super ContactDetailState> sub) {
                        try {
                            ContactDetailState state = new ContactDetailState();

                            String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" like '" + contactName +"'";
                            String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    ContactsContract.CommonDataKinds.Phone.TYPE};
                            Cursor c = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    projection, selection, null, null);
                            while (c.moveToNext()){
                                String phoneNo = c.getString(0);
                                if (!state.containsPhone(phoneNo)){
                                        state.addPhoneNumber(phoneNo);
                                }
                            }
                            c.close();
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

    /**
     * function returns the email list request observable
     *
     * @return the created Observable
     */
    public static Observable<ContactDetailState> getEmailDetailsObservable(final Context context, final String contactName, final ContactDetailState state) {
        return Observable.create(
                new Observable.OnSubscribe<ContactDetailState>() {
                    @Override
                    public void call(Subscriber<? super ContactDetailState> sub) {
                        try {
                            List<String> mails = new ArrayList<>();
                            ContentResolver cr = context.getContentResolver();
                            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                                    null, null, null);
                            if (cur.getCount() > 0) {
                                while (cur.moveToNext()) {
                                    String id = cur.getString(cur
                                            .getColumnIndex(ContactsContract.Contacts._ID));
                                    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                                    if(name.equalsIgnoreCase(contactName)) {
                                        Cursor emailCur = cr.query(
                                                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                                        null,
                                                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                                        new String[]{id}, null);
                                                while (emailCur.moveToNext()) {
                                                    String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                                                    mails.add(email);

                                                }
                                                emailCur.close();
                                            }
                                        }


                            }
                            state.setmEmailList(mails);
                            cur.close();
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
