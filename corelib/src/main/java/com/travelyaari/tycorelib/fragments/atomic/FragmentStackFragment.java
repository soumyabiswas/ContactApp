package com.travelyaari.tycorelib.fragments.atomic;

import android.os.Bundle;

import androidx.annotation.Nullable;
import android.widget.Toast;

import com.travelyaari.tycorelib.activities.FragmentStackActivity;
import com.travelyaari.tycorelib.fragments.MVPFragment;
import com.travelyaari.tycorelib.fragments.utils.BackstackUtils;
import com.travelyaari.tycorelib.primitives.IFragmentStack;

/**
 * Created by Triode on 6/29/2016.
 */
public class FragmentStackFragment extends PrimitiveFragment implements IFragmentStack<FragmentStackFragment> {

    protected FragmentStackActivity mActivity;


    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (FragmentStackActivity)getActivity();
    }

    /**
     * Function which to return title of current activity
     *
     * @return title string
     */
    protected String getTitle() {
        return mActivity.getTitle().toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getTitle());
    }



    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after {@link #onDestroy()}.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    /**
     * function which will clear the backstack of fragments, This also prevent the fragment animations
     *
     * @return boolean return true success else false
     */
    @Override
    public boolean clearBackstack() {
        return mActivity.clearBackstack();
    }

    /**
     * function returns the fragment at the position provided
     *
     * @param position index at which the fragment to be returned
     * @return {@link MVPFragment}
     */
    @Override
    public FragmentStackFragment getFragmentAt(int position) {
        return mActivity.getFragmentAt(position);
    }

    /**
     * Function which will be used to pop a Fragment from the backstack
     *
     * @return true success otherwise false
     */
    @Override
    public boolean pop() {
        return mActivity.pop();
    }

    /**
     * Function which will pop all the fragments till the first occurrence of the class name
     *
     * @param fragmentName name of the Fragment
     */
    @Override
    public void popUntilFirstOccurrence(String fragmentName) {
        mActivity.popUntilFirstOccurrence(fragmentName);
    }

    /**
     * Function which will pop all the fragments till the first occurrence of the class name
     *
     * @param fragmentName name of the Fragment
     */
    @Override
    public void popUntilLastOccurrence(String fragmentName) {
        mActivity.popUntilLastOccurrence(fragmentName);
    }

    /**
     * Function which push a new Fragment to the backstac
     *
     * @param transaction of type BackstackUtils.FragmentTransaction
     */
    @Override
    public void push(BackstackUtils.FragmentTransaction transaction) {
        if (mActivity == null) {
            mActivity = (FragmentStackActivity) getActivity();
        }
        if (mActivity != null) {
            mActivity.push(transaction);
        }
    }

    /**
     * this will show a toast message with passed message
     *
     * @param message the message to be shown
     */
    protected final void showToast(final String message){
        if(isResumed()) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * this will show a toast message with passed message
     *
     * @param message the message to be shown
     */
    protected final void showToast(final String message, final int length){
        if(isResumed()) {
            Toast.makeText(getActivity(), message, length).show();
        }
    }

    /**
     * this will show a toast message with passed message
     *
     * @param message the message to be shown
     */
    protected final void showToast(final int message) {
        if (isResumed()) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * function redirect the call to enclosing view lto check
     * back button needs to be handled or not
     *
     * @return true if handled otherwise false
     */
    public boolean onBackPressed(){
        return false;
    }
}
