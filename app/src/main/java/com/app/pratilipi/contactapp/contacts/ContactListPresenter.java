package com.app.pratilipi.contactapp.contacts;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import com.app.pratilipi.contactapp.AppModule;
import com.app.pratilipi.contactapp.Constants;
import com.app.pratilipi.contactapp.network.API;
import com.travelyaari.tycorelib.events.CoreEvent;
import com.travelyaari.tycorelib.mvp.BasePresenter;
import com.travelyaari.tycorelib.ultlils.CoreLogger;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class ContactListPresenter<V extends ContactListView> extends BasePresenter<V> {

    private Observable<List<ContactItemVO>> mContactDetailObservable;
    private Subscription mContactDetailSubscription;

    /**
     * Function to request contact details.
     */
    public void getContacts(Context context) {
        if (!isReleased()) {
            getView().showLoading();
        }
        mContactDetailObservable = API.getContactsObservable(context);
        mContactDetailSubscription = mContactDetailObservable.subscribe(new Subscriber<List<ContactItemVO>>() {

            @Override
            public void onCompleted() {

                if (mContactDetailSubscription != null) {
                    mContactDetailSubscription.unsubscribe();
                    mContactDetailSubscription = null;

                }
            }

            @Override
            public void onError(Throwable e) {
                CoreLogger.e("ContactDetailPresenter", e);
                mContactDetailSubscription.unsubscribe();
                mContactDetailSubscription = null;
            }

            @Override
            public void onNext(List<ContactItemVO> contactItemVOS) {
                if (!isReleased()) {
                    getView().hideLoading();
                }
                dispatchContactDetail(contactItemVOS);

            }
        });
    }


    private void dispatchContactDetail(List<ContactItemVO> contactItemVOList) {
        Bundle data = new Bundle();
        data.putParcelableArrayList(Constants.DATA, (ArrayList<? extends Parcelable>) contactItemVOList);
        AppModule.getmModule().dispatchEvent(new CoreEvent(Constants.CONTACT_LIST_LOADED_EVENT, data));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mContactDetailSubscription != null && !mContactDetailSubscription.isUnsubscribed()) {
            mContactDetailSubscription.unsubscribe();
        }
        mContactDetailSubscription = null;
        mContactDetailObservable = null;
    }
}
