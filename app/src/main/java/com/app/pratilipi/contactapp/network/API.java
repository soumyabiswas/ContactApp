package com.app.pratilipi.contactapp.network;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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
                                String id = phones.getString(phones.getColumnIndex(ContactsContract.Contacts._ID));
                                Cursor phones1 = context.getContentResolver().query(
                                        ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                        new String[]{id}, null);
                                while(phones1.moveToNext()){
                                    String email = phones1.getString(phones1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                                    contactItemVO.setmEmail(email);
                                }
                                phones1.close();

                                Uri imageUri = getPhotoUri(Long.parseLong(id),context);
                                if(imageUri!=null){
                                    contactItemVO.setmImageUrl(imageUri.toString());
                                }
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


    public static Uri getPhotoUri(long contactId,final  Context context) {
        ContentResolver contentResolver = context.getContentResolver();

        try {
            Cursor cursor = contentResolver
                    .query(ContactsContract.Data.CONTENT_URI,
                            null,
                            ContactsContract.Data.CONTACT_ID
                                    + "="
                                    + contactId
                                    + " AND "

                                    + ContactsContract.Data.MIMETYPE
                                    + "='"
                                    + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
                                    + "'", null, null);

            if (cursor != null) {
                if (!cursor.moveToFirst()) {
                    return null; // no photo
                }
            } else {
                return null; // error in cursor process
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Uri person = ContentUris.withAppendedId(
                ContactsContract.Contacts.CONTENT_URI, contactId);
        return Uri.withAppendedPath(person,
                ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }
}
