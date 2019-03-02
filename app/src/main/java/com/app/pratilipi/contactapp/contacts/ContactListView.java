package com.app.pratilipi.contactapp.contacts;

import android.view.View;

import com.app.pratilipi.contactapp.Common.views.ProgressView;
import com.app.pratilipi.contactapp.R;

import com.travelyaari.tycorelib.mvp.ViewState;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


public class ContactListView extends ProgressView<ViewState, ContactListState> {

    @BindView(R.id.list_view)
    RecyclerView mListView;



    @Override
    protected int getLayoutID() {
        return R.layout.contact_list;
    }

    @Override
    protected View getContentView() {
        return mListView;
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
}
