package com.app.pratilipi.contactapp.contacts.contactdetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.pratilipi.contactapp.AppModule;
import com.app.pratilipi.contactapp.Common.views.ProgressView;
import com.app.pratilipi.contactapp.Constants;
import com.app.pratilipi.contactapp.R;
import com.app.pratilipi.contactapp.utils.ImageLoadUtils;
import com.travelyaari.tycorelib.events.CoreEvent;
import com.travelyaari.tycorelib.mvp.MVPView;
import com.travelyaari.tycorelib.mvp.ViewState;

import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import butterknife.BindView;


public class ContactDetailView extends ProgressView<ViewState, ContactDetailState> {



    @BindView(R.id.profile_img)
    AppCompatImageView mImageView;

    @BindView(R.id.content_view)
    AppCompatImageView mContentView;

    @BindView(R.id.name)
    TextView mContactName;

    @BindView(R.id.phone_numbers_layout)
    LinearLayout mPhoneNoLayout;

    @BindView(R.id.email_layout)
    LinearLayout mEmailLayout;


    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_contact_detail;
    }

    @Override
    protected View getContentView() {
        return mContentView;
    }

    @Override
    protected String getRetryEventName() {
        return null;
    }

    @Override
    protected void initializeViewState() {
        mViewState = new ViewState();
    }

    @Override
    protected void onInflate() {

    }

    public void setPhoneNumbers(String phoneNumber){
        int layout = R.layout.phone_number_item_layout;
        LayoutInflater inflater = LayoutInflater.from(mPhoneNoLayout.getContext());
        View view = inflater.inflate(layout,mPhoneNoLayout, false);
        TextView textView = view.findViewById(R.id.phone_number);
        AppCompatImageView callIcon = view.findViewById(R.id.phone_call);
        callIcon.setTag(phoneNumber);
        callIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = (String) view.getTag();
                Bundle data = new Bundle();
                data.putString(Constants.DATA, number);
                CoreEvent event = new CoreEvent(Constants.CONTACT_CALL_CLICKED_EVENT, data);
                AppModule.getmModule().dispatchEvent(event);
            }
        });
        AppCompatImageView smsIcon = view.findViewById(R.id.sms);
        smsIcon.setTag(smsIcon);
        smsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = (String) view.getTag();
                Bundle data = new Bundle();
                data.putString(Constants.DATA, number);
                CoreEvent event = new CoreEvent(Constants.CONTACT_SMS_CLICKED_EVENT, data);
                AppModule.getmModule().dispatchEvent(event);
            }
        });
        textView.setText(phoneNumber);
        mPhoneNoLayout.addView(view);
    }

    public void setEmail(String emailId){
        int layout = R.layout.email_item_layout;
        LayoutInflater inflater = LayoutInflater.from(mEmailLayout.getContext());
        View view = inflater.inflate(layout,mEmailLayout, false);
        TextView textView = view.findViewById(R.id.email_id);
        textView.setText(emailId);
        mEmailLayout.addView(view);
    }

    public void setImage(String imageUrl){
        if(imageUrl!=null) {
            ImageLoadUtils.loadWith(AppModule.getmModule(), imageUrl, mImageView);
        }else {
            mImageView.setImageResource(R.drawable.ic_person);
        }

    }

    public void setmContactName(String name){
        mContactName.setText(name);
    }


}
