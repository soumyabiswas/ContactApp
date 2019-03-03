package com.app.pratilipi.contactapp.contactdetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.app.pratilipi.contactapp.AppModule;
import com.app.pratilipi.contactapp.Constants;
import com.app.pratilipi.contactapp.R;
import com.app.pratilipi.contactapp.contacts.ContactItemVO;
import com.travelyaari.tycorelib.activities.MVPActivity;
import com.travelyaari.tycorelib.events.CoreEvent;
import com.travelyaari.tycorelib.events.Event;
import com.travelyaari.tycorelib.events.EventListener;

import androidx.appcompat.widget.Toolbar;


public class ContactDetailActivity extends MVPActivity<ContactDetailView, ContactDetailPresenter<ContactDetailView>> implements EventListener {
    ContactDetailState mState;
    ColorGenerator mGenerator;

    @Override
    protected void onBindView() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getArgs().getmName());
        createStateIfNull();
        updateIntroView();
    }


    void updateIntroView() {
        mGenerator = ColorGenerator.MATERIAL;
        TextDrawable drawable = null;
        if (getArgs().getmImageUrl() == null) {
            int color = mGenerator.getColor(getArgs().getmName());
            drawable = TextDrawable.builder()
                    .buildRound(getArgs().getmName().substring(0, 1), color);
        }
        mView.setImage(getArgs().getmImageUrl(), drawable);
        mView.setmContactName(getArgs().getmName());
    }

    void createStateIfNull() {
        if (mState == null) {
            mState = new ContactDetailState();
        }
    }

    @Override
    protected Class getViewClass() {
        return ContactDetailView.class;
    }

    @Override
    protected Class getPresenterClass() {
        return ContactDetailPresenter.class;
    }

    @Override
    protected void onCreatePresenter() {
       loadContactDetails();

    }


    private void loadContactDetails(){
        if (mState.getmPhoneList().isEmpty()) {
            mPresenter.getContactDetails(this, getArgs().getmName());
        } else {
            showPhoneNumber();
            showMails();
        }
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
        } else if (event.getType().equalsIgnoreCase(Constants.CONTACT_DETAILS_LOADED_EVENT)) {
            handleContactDetailsLoaded(((CoreEvent) event).getmExtra());
        } else if (event.getType().equalsIgnoreCase(Constants.CONTACT_MAILS_LOADED_EVENT)) {
            handleMailsLoaded(((CoreEvent) event).getmExtra());
        }else if (event.getType().equalsIgnoreCase(Constants.INITIATE_CONTACT_DETAIL_LOAD_EVENT)) {
            loadContactDetails();
        }
    }


    private void handleContactDetailsLoaded(Bundle bundle) {
        mState = bundle.getParcelable(Constants.DATA);
        showPhoneNumber();
        if (mState.getmEmailList() == null || mState.getmEmailList().isEmpty()) {
            mPresenter.getEmails(this, getArgs().getmName(), mState);
        }
    }

    void showPhoneNumber() {
        mView.clearAllPhoneViews();
        if (mState != null && mState.getmPhoneList()!=null) {
            for (String phone : mState.getmPhoneList()){
                mView.addPhoneNumbers(phone);
            }

        }
    }

    void showMails() {
        if (mState != null && mState.getmEmailList() != null ) {
            for (int i = 0; i < mState.getmEmailList().size(); i++) {
                mView.setEmail(mState.getmEmailList().get(i));
            }
        }
    }

    private void handleMailsLoaded(Bundle bundle) {
        showMails();
    }


    private void handleCallClickedEvent(Bundle bundle) {
        String phoneNumber = bundle.getString(Constants.DATA);
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }

    private void handleSMSClickedEvent(Bundle bundle) {
        String phoneNumber = bundle.getString(Constants.DATA);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("smsto:"+phoneNumber));
        intent.putExtra("sms_body", "");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

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

    void addGlobalEvents() {
        AppModule.getmModule().addEventListener(Constants.CONTACT_CALL_CLICKED_EVENT, this);
        AppModule.getmModule().addEventListener(Constants.CONTACT_SMS_CLICKED_EVENT, this);
        AppModule.getmModule().addEventListener(Constants.CONTACT_DETAILS_LOADED_EVENT, this);
        AppModule.getmModule().addEventListener(Constants.CONTACT_MAILS_LOADED_EVENT, this);
        AppModule.getmModule().addEventListener(Constants.INITIATE_CONTACT_DETAIL_LOAD_EVENT, this);
    }

    void removeGlobalEvents() {
        AppModule.getmModule().removeEventListener(Constants.CONTACT_CALL_CLICKED_EVENT, this);
        AppModule.getmModule().removeEventListener(Constants.CONTACT_SMS_CLICKED_EVENT, this);
        AppModule.getmModule().removeEventListener(Constants.CONTACT_DETAILS_LOADED_EVENT, this);
        AppModule.getmModule().removeEventListener(Constants.CONTACT_DETAILS_LOADED_EVENT, this);
        AppModule.getmModule().removeEventListener(Constants.CONTACT_MAILS_LOADED_EVENT, this);
        AppModule.getmModule().removeEventListener(Constants.INITIATE_CONTACT_DETAIL_LOAD_EVENT, this);
    }


    @Override
    public void onDestroy() {
        removeGlobalEvents();
        super.onDestroy();
    }
}
