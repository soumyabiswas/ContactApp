package com.app.pratilipi.contactapp.contacts.contactdetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.app.pratilipi.contactapp.AppModule;
import com.app.pratilipi.contactapp.Constants;
import com.app.pratilipi.contactapp.R;
import com.app.pratilipi.contactapp.contacts.ContactItemVO;
import com.travelyaari.tycorelib.activities.MVActivity;
import com.travelyaari.tycorelib.events.CoreEvent;
import com.travelyaari.tycorelib.events.Event;
import com.travelyaari.tycorelib.events.EventListener;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

public class ContactDetailActivity<V extends ContactDetailView> extends MVActivity<V> implements EventListener {
    @Override
    protected void onBindView() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addGlobalEvents();
        updateView();
    }


    void updateView(){
        mView.setImage(getArgs().getmImageUrl());
        mView.setmContactName(getArgs().getmName());
        mView.setPhoneNumbers(getArgs().getmNumber());
    }

    @Override
    protected Class getViewClass() {
        return ContactDetailView.class;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ContactItemVO getArgs() {
        return getIntent().getParcelableExtra(Constants.DATA);
    }


    @Override
    public void onEvent(Event event) {
        if (event.getType().equalsIgnoreCase(Constants.CONTACT_CALL_CLICKED_EVENT)) {
            handleCallClickedEvent(((CoreEvent) event).getmExtra());
        } else if (event.getType().equalsIgnoreCase(Constants.CONTACT_SMS_CLICKED_EVENT)) {
            handleSMSClickedEvent(((CoreEvent) event).getmExtra());
        }
    }


    private void handleCallClickedEvent(Bundle bundle){
        String phoneNumber = bundle.getString(Constants.DATA);
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" +phoneNumber ));
        startActivity(callIntent);
    }

    private void handleSMSClickedEvent(Bundle bundle){
        String phoneNumber = bundle.getString(Constants.DATA);
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                + phoneNumber)));
    }

    void addGlobalEvents() {
        AppModule.getmModule().addEventListener(Constants.CONTACT_CALL_CLICKED_EVENT, this);
        AppModule.getmModule().addEventListener(Constants.CONTACT_SMS_CLICKED_EVENT, this);
    }

    void removeGlobalEvents() {
        AppModule.getmModule().removeEventListener(Constants.CONTACT_CALL_CLICKED_EVENT, this);
        AppModule.getmModule().removeEventListener(Constants.CONTACT_SMS_CLICKED_EVENT, this);
    }

    @Override
    public void onDestroy() {
        removeGlobalEvents();
        super.onDestroy();
    }
}
