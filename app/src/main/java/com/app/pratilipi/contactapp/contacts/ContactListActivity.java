package com.app.pratilipi.contactapp.contacts;

import android.os.Bundle;

import com.app.pratilipi.contactapp.Constants;
import com.travelyaari.tycorelib.activities.MVPActivity;
import com.travelyaari.tycorelib.events.Event;
import com.travelyaari.tycorelib.events.EventListener;

public class ContactListActivity extends MVPActivity<ContactListView, ContactListPresenter<ContactListView>> implements EventListener {

    ContactListState mState;

    @Override
    protected Class getPresenterClass() {
        return ContactListPresenter.class;
    }

    @Override
    protected void onCreatePresenter() {
        createStateIfNull();
    }

    @Override
    protected void onBindView() {

    }

    @Override
    protected Class getViewClass() {
        return ContactListView.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        addGlobalEvents();
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mState = savedInstanceState.getParcelable(Constants.DATA);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.DATA, mState);
    }

    void createStateIfNull() {
        if (mState == null) {
            mState = new ContactListState();
        }
    }

    @Override
    public void onEvent(Event event) {

    }

    void addGlobalEvents() {

    }

    void removeGlobalEvents() {

    }

    @Override
    public void onDestroy() {
        removeGlobalEvents();
        super.onDestroy();

    }
}
