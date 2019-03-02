package com.travelyaari.tycorelib.adapters;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;


/**
 * Created by triode on 25/8/16.
 */
public abstract class BaseFragmentStateAdapter extends FragmentStatePagerAdapter {

    Bundle[] mViewStates;
    public BaseFragmentStateAdapter(FragmentManager fm) {
        super(fm);
    }


    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public final Fragment getItem(int position) {
        final Fragment fragment = getFragment(position);
        restoreArguments(fragment, position);
        return fragment;
    }

    /**
     * function restore the argument if present
     *
     * @param fragment the fragment against the argument to be restored
     */
    private void restoreArguments(final Fragment fragment, final int position){
        if(mViewStates != null && mViewStates[position] != null){
            fragment.setArguments(mViewStates[position]);
        }
    }

    @Override
    public Parcelable saveState() {
        final Parcelable parcelable = super.saveState();
        if (parcelable != null && parcelable instanceof Bundle && mViewStates != null) {
            ((Bundle) parcelable).putParcelableArray(getClass().getName(), mViewStates);
        }
        return parcelable;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
        if (state instanceof Bundle && ((Bundle) state).containsKey(getClass().getName())) {
            Parcelable[] parcelables = (((Bundle) state).getParcelableArray(getClass().getName()));
            mViewStates = new Bundle[parcelables.length];
            int i = 0;
            for (Parcelable parcelable : parcelables) {
                mViewStates[i++] = (Bundle) parcelable;
            }
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        if(mViewStates == null){
            mViewStates = new Bundle[getCount()];
        }
        mViewStates[position] = ((Fragment)object).getArguments();
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position the position of the fragment
     * @return the created fragment
     */
    protected abstract Fragment getFragment(int position);
}
