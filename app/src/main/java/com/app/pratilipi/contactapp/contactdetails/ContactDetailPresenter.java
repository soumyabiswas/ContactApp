package com.app.pratilipi.contactapp.contactdetails;

import android.content.Context;
import android.os.Bundle;

import com.app.pratilipi.contactapp.AppModule;
import com.app.pratilipi.contactapp.Constants;
import com.app.pratilipi.contactapp.network.API;
import com.app.pratilipi.contactapp.utils.UtilMethods;
import com.travelyaari.tycorelib.events.CoreEvent;
import com.travelyaari.tycorelib.mvp.BasePresenter;
import com.travelyaari.tycorelib.ultlils.CoreLogger;



import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class ContactDetailPresenter<V extends ContactDetailView> extends BasePresenter<V> {

    private Observable<ContactDetailState> mContactDetailObservable;
    private Subscription mContactDetailSubscription;

    private Observable<ContactDetailState> mEmailObservable;
    private Subscription mEmailSubscription;

    /**
     * Function to request contact details.
     */
    public void getContactDetails(Context context, String name) {
        if (!isReleased()) {
            getView().showLoading();
        }
        mContactDetailObservable = API.getContactDetailsObservable(context,name);
        mContactDetailSubscription = mContactDetailObservable.subscribe(new Subscriber<ContactDetailState>() {

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
                if (!isReleased()) {
                    if (UtilMethods.isNetworkConnected(AppModule.getmModule())) {
                        getView().showError(com.travelyaari.tycorelib.Constants.ErrorCodes.SERVER_ERROR);
                    } else {
                        getView().showError(com.travelyaari.tycorelib.Constants.ErrorCodes.NETWORK_ERROR);
                    }

                }
            }

            @Override
            public void onNext(ContactDetailState contactItemVOS) {
                if (!isReleased()) {
                    getView().hideLoading();
                }
                dispatchContactDetail(contactItemVOS);

            }
        });
    }


    private void dispatchContactDetail(ContactDetailState contactItemVO) {
        Bundle data = new Bundle();
        data.putParcelable(Constants.DATA, contactItemVO);
        AppModule.getmModule().dispatchEvent(new CoreEvent(Constants.CONTACT_DETAILS_LOADED_EVENT, data));

    }

    /**
     * Function to request contact mail.
     */
    public void getEmails(Context context, String name, ContactDetailState state) {
        mEmailObservable = API.getEmailDetailsObservable(context,name,state);
        mEmailSubscription = mEmailObservable.subscribe(new Subscriber<ContactDetailState>() {

            @Override
            public void onCompleted() {

                if (mEmailSubscription != null) {
                    mEmailSubscription.unsubscribe();
                    mEmailSubscription = null;

                }
            }

            @Override
            public void onError(Throwable e) {
                CoreLogger.e("EmailListPresenter", e);
                mEmailSubscription.unsubscribe();
                mEmailSubscription = null;
            }

            @Override
            public void onNext(ContactDetailState state) {
                if (!isReleased()) {
                    getView().hideLoading();
                }
                dispatchMailList(state);

            }
        });
    }


    private void dispatchMailList(ContactDetailState state) {
        Bundle data = new Bundle();
        data.putParcelable(Constants.DATA, state);
        AppModule.getmModule().dispatchEvent(new CoreEvent(Constants.CONTACT_MAILS_LOADED_EVENT, data));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mContactDetailSubscription != null && !mContactDetailSubscription.isUnsubscribed()) {
            mContactDetailSubscription.unsubscribe();
        }
        mContactDetailSubscription = null;
        mContactDetailObservable = null;

        if (mEmailSubscription != null && !mEmailSubscription.isUnsubscribed()) {
            mEmailSubscription.unsubscribe();
        }
        mEmailSubscription = null;
        mEmailObservable = null;
    }
}
