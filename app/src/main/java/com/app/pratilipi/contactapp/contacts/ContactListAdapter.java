package com.app.pratilipi.contactapp.contacts;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.app.pratilipi.contactapp.AppModule;
import com.app.pratilipi.contactapp.R;
import com.app.pratilipi.contactapp.utils.ImageLoadUtils;
import com.travelyaari.tycorelib.adapters.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;




/**
 * Created by soumya on 6/3/18.
 */

public class ContactListAdapter extends RecyclerAdapter.BaseRecycleViewAdapter<ContactItemVO, ContactListAdapter.ViewHolder> implements Filterable {


    LayoutInflater mInflater;
    ColorGenerator mGenerator;
    Activity mActivity;
    private List<ContactItemVO> mContactListFiltered;
    private List<ContactItemVO> mContactList;

    public ContactListAdapter(Activity activity) {
        super();
        this.mActivity = activity;
        mGenerator = ColorGenerator.MATERIAL;

    }

    public ContactListAdapter(List<ContactItemVO> contactList) {
        this.mContactList = contactList;
        this.mContactListFiltered = contactList;
    }

    @Override
    protected ContactListAdapter.ViewHolder createView(ViewGroup pareGroup, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(pareGroup.getContext());
        }
        View view = mInflater.inflate(R.layout.contact_list_item, pareGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    protected void updateView(ContactListAdapter.ViewHolder viewHolder, final ContactItemVO item) {
        viewHolder.mContactName.setText(item.getmName());
        int color = mGenerator.getColor(item.getmName());
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(item.getmName().substring(0, 1), color);
        viewHolder.mContactImg.setImageDrawable(drawable);

    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mContactListFiltered = mContactList;
                } else {
                    List<ContactItemVO> filteredList = new ArrayList<>();
                    for (int i = 0; i < mContactListFiltered.size(); i++) {

                        final String text = mContactListFiltered.get(i).getmName().toLowerCase();
                        if (text.contains(charString.toLowerCase())) {
                            filteredList.add(mContactListFiltered.get(i));
                        }
                    }

                    mContactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mContactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mContactListFiltered = (ArrayList<ContactItemVO>) filterResults.values;
                setmDataprovider(mContactListFiltered);
            }
        };
    }


    public static class ViewHolder extends RecyclerAdapter.ViewHolder {
        AppCompatTextView mContactName;
        AppCompatImageView mContactImg;
        View mContentView;

        /**
         * Constructor function
         *
         * @param itemView the enclosing view
         */
        public ViewHolder(View itemView) {
            super(itemView);
            mContactName = itemView.findViewById(R.id.contact_name);
            mContactImg = itemView.findViewById(R.id.profile_text_img);
            mContentView = itemView;

//            mContentView.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    AppCompatCheckBox cb = v.findViewById(R.id.checkbox);
//                    cb.setChecked(!cb.isChecked());
//                    ContactItemVO contact = (ContactItemVO) cb.getTag();
//
//                }
//            });
        }
    }
}

