package com.app.pratilipi.contactapp.contacts;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import com.app.pratilipi.contactapp.AppModule;
import com.app.pratilipi.contactapp.Constants;
import com.app.pratilipi.contactapp.network.API;
import com.app.pratilipi.contactapp.utils.UtilMethods;
import com.travelyaari.tycorelib.events.CoreEvent;
import com.travelyaari.tycorelib.mvp.BasePresenter;
import com.travelyaari.tycorelib.ultlils.CoreLogger;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class ContactListPresenter<V extends ContactListView> extends BasePresenter<V> {

    private Observable<List<ContactItemVO>> mContactListObservable;
    private Subscription mContactListSubscription;

    /**
     * Function to request contact details.
     */
    public void getContacts(Context context) {
        if (!isReleased()) {
            getView().showLoading();
        }
        mContactListObservable = API.getContactsObservable(context);
        mContactListSubscription = mContactListObservable.subscribe(new Subscriber<List<ContactItemVO>>() {

            @Override
            public void onCompleted() {

                if (mContactListSubscription != null) {
                    mContactListSubscription.unsubscribe();
                    mContactListSubscription = null;

                }
            }

            @Override
            public void onError(Throwable e) {
                CoreLogger.e("ContactListPresenter", e);
                mContactListSubscription.unsubscribe();
                mContactListSubscription = null;
                if (!isReleased()) {
                    if (UtilMethods.isNetworkConnected(AppModule.getmModule())) {
                        getView().showError(com.travelyaari.tycorelib.Constants.ErrorCodes.SERVER_ERROR);
                    } else {
                        getView().showError(com.travelyaari.tycorelib.Constants.ErrorCodes.NETWORK_ERROR);
                    }

                }
            }

            @Override
            public void onNext(List<ContactItemVO> contactItemVOList) {
                if (!isReleased()) {
                    getView().hideLoading();
                }
                dispatchContactList(contactItemVOList);

            }
        });
    }


    private void dispatchContactList(List<ContactItemVO> contactItemVOList) {
        Bundle data = new Bundle();
        data.putParcelableArrayList(Constants.DATA, (ArrayList<? extends Parcelable>) contactItemVOList);
        AppModule.getmModule().dispatchEvent(new CoreEvent(Constants.CONTACT_LIST_LOADED_EVENT, data));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mContactListSubscription != null && !mContactListSubscription.isUnsubscribed()) {
            mContactListSubscription.unsubscribe();
        }
        mContactListSubscription = null;
        mContactListObservable = null;
    }
}
