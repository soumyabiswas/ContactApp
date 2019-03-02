package com.app.pratilipi.contactapp.contacts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.app.pratilipi.contactapp.AppModule;
import com.app.pratilipi.contactapp.Constants;
import com.app.pratilipi.contactapp.R;
import com.travelyaari.tycorelib.activities.MVPActivity;
import com.travelyaari.tycorelib.events.CoreEvent;
import com.travelyaari.tycorelib.events.Event;
import com.travelyaari.tycorelib.events.EventListener;

import androidx.core.content.ContextCompat;

public class ContactListActivity extends MVPActivity<ContactListView, ContactListPresenter<ContactListView>> implements EventListener {
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 100;

    ContactListAdapter mContactListAdapter;

    ContactListState mState;

    @Override
    protected Class getPresenterClass() {
        return ContactListPresenter.class;
    }

    @Override
    protected void onCreatePresenter() {
        createStateIfNull();
        mContactListAdapter = new ContactListAdapter(this);
        mView.setAdapter(mContactListAdapter);
        askForContactPermission();
    }

    void loadContacts() {
        if (mState.mContactVOList == null) {
            mPresenter.getContacts(this);
        } else {
            mContactListAdapter.setmDataprovider(mState.mContactVOList);
        }
    }


    public void askForContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                    Toast.makeText(this, R.string.explain_to_read_contacts, Toast.LENGTH_LONG).show();

                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                            READ_CONTACTS_PERMISSIONS_REQUEST);

                } else {
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                            READ_CONTACTS_PERMISSIONS_REQUEST);
                }
            } else {
                loadContacts();
            }
        } else {
            loadContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case READ_CONTACTS_PERMISSIONS_REQUEST:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadContacts();
                } else {
                    onPermissionDenied();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }


    void onPermissionDenied() {
        Toast.makeText(this, R.string.message_please_allow_contacts, Toast.LENGTH_LONG).show();
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
        super.onCreate(savedInstanceState);
        addGlobalEvents();
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
        if (event.getType().equalsIgnoreCase(Constants.CONTACT_LIST_LOADED_EVENT)) {
            handleContactsLoadedEvents(((CoreEvent) event).getmExtra());
        }
    }

    private void handleContactsLoadedEvents(Bundle bundle) {
        mState.mContactVOList = bundle.getParcelableArrayList(Constants.DATA);
        mContactListAdapter.setmDataprovider(mState.mContactVOList);

    }


    void addGlobalEvents() {
        AppModule.getmModule().addEventListener(Constants.CONTACT_LIST_LOADED_EVENT, this);

    }

    void removeGlobalEvents() {
        AppModule.getmModule().removeEventListener(Constants.CONTACT_LIST_LOADED_EVENT, this);

    }

    @Override
    public void onDestroy() {
        removeGlobalEvents();
        super.onDestroy();

    }
}
