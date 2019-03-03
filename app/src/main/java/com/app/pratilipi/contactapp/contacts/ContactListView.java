package com.app.pratilipi.contactapp.contacts;

import android.view.View;

import com.app.pratilipi.contactapp.Common.views.ProgressView;
import com.app.pratilipi.contactapp.Constants;
import com.app.pratilipi.contactapp.R;

import com.travelyaari.tycorelib.mvp.ViewState;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


public class ContactListView extends ProgressView<ViewState, ContactListState> {

    @BindView(R.id.list_view)
    RecyclerView mContactListView;

    @Override
    protected int getLayoutID() {
        return R.layout.contact_list;
    }

    @Override
    protected View getContentView() {
        return mContactListView;
    }

    @Override
    protected String getRetryEventName() {
        return Constants.INITIATE_CONTACT_LIST_LOAD_EVENT;
    }

    @Override
    protected void initializeViewState() {
        mViewState = new ViewState();
    }

    @Override
    protected void onInflate() {
        mContactListView.setLayoutManager(new LinearLayoutManager(mView.getContext()));

    }

    void setAdapter(ContactListAdapter adapter) {
        mContactListView.setAdapter(adapter);
    }
}
