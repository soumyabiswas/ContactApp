package com.travelyaari.tycorelib.activities;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.MotionEvent;
import android.widget.Toast;

import com.travelyaari.tycorelib.fragments.MVPFragment;
import com.travelyaari.tycorelib.fragments.atomic.FragmentStackFragment;
import com.travelyaari.tycorelib.fragments.utils.BackstackUtils;
import com.travelyaari.tycorelib.primitives.IFragmentStack;

/**
 * Created by Triode on 6/29/2016.
 */
public abstract class FragmentStackActivity extends PrimitiveActivity
        implements IFragmentStack<FragmentStackFragment>{


    protected Bundle mSavedInstanceState;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedInstanceState = savedInstanceState;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * function check the activity is restored or not
     * @return true if restored otherwise false
     */
    protected boolean isRestored(){
        return (mSavedInstanceState != null);
    }


    /**
     * Function which will be used to pop a Fragment from the backstack
     *
     * @return true success otherwise false
     */
    @Override
    public boolean pop() {
        return getSupportFragmentManager().popBackStackImmediate();
    }

    /**
     * Function which push a new Fragment to the backstac
     *
     * @param transaction of type BackstackUtils
     */
    @Override
    public void push(BackstackUtils.FragmentTransaction transaction) {
        final FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();
        if (transaction.mInAnimation == BackstackUtils.FRAGMENT_DO_NOT_ANIMATE ||
                transaction.mOutAnimation == BackstackUtils.FRAGMENT_DO_NOT_ANIMATE) {
            //No animation for this condition
        } else if (transaction.mInAnimation == BackstackUtils.FRAGMENT_NO_ANIMATION ||
                transaction.mOutAnimation == BackstackUtils.FRAGMENT_NO_ANIMATION) {
            ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        }else{
            if (transaction.mPopInAnimation != BackstackUtils.FRAGMENT_NO_ANIMATION
                    && transaction.mPopOutAnimation != BackstackUtils.FRAGMENT_NO_ANIMATION) {
                ft.setCustomAnimations(transaction.mInAnimation, transaction.mOutAnimation,
                        transaction.mPopInAnimation, transaction.mPopOutAnimation);
            } else {
                ft.setCustomAnimations(transaction.mInAnimation, transaction.mOutAnimation);
            }

        }
        String tag = "";
        if(transaction.isRoot){
            clearBackstack();
            tag = "0";
        }else {
            tag = getSupportFragmentManager().getBackStackEntryCount() + "";
        }
        Fragment fragment = transaction.compile();
        ft.replace(transaction.mFrameId, fragment, tag);
        ft.addToBackStack(tag);
        if (!isFinishing()) {
            ft.commitAllowingStateLoss();
        }
    }

    /**
     * Function which will pop all the fragments till the first occurrence of the class name
     *
     * @param fragmentName name of the Fragment
     */
    @Override
    public void popUntilFirstOccurrence(String fragmentName) {
        if(fragmentName == null)
            return;
        String name = null;
        for(int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
            index >= 0; index--){
            FragmentStackFragment fragment = getFragmentAt(index);
            final String clsName = fragment.getClass().getName();
            if(fragmentName.equals(clsName)){
                name = index+"";
                break;
            }
        }
        if(name != null) {
            getSupportFragmentManager().popBackStack(name, 0);
        }
    }

    /**
     * Function which will pop all the fragments till the first occurrence of the class name
     *
     * @param fragmentName name of the Fragment
     */
    @Override
    public void popUntilLastOccurrence(String fragmentName) {
        if(fragmentName == null)
            return;
        int length = getSupportFragmentManager().getBackStackEntryCount();
        String name = null;
        for(int index = 0; index < length; index++){
            FragmentStackFragment fragment = getFragmentAt(index);
            final String clsName = fragment.getClass().getName();
            if(fragmentName.equals(clsName)){
                name = index+"";
                break;
            }
        }
        if(name != null) {
            getSupportFragmentManager().popBackStack(name, 0);
        }
    }

    /**
     * function which will clear the back stack of fragments, This also prevent the fragment animations
     *
     * @return boolean return true success else false
     */
    @Override
    public boolean clearBackstack() {
        if (!isFinishing()) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        return true;
    }

    /**
     * function returns the fragment at the position provided
     *
     * @param position index at which the fragment to be returned
     * @return {@link MVPFragment}
     */
    @Override
    public FragmentStackFragment getFragmentAt(int position) {
        FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().
                getBackStackEntryAt(position);
        String str=backEntry.getName();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(str);
        return (FragmentStackFragment) fragment;
    }


    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        final int count = getSupportFragmentManager().getBackStackEntryCount();
        if(count > 0) {
            final FragmentStackFragment fragment = getFragmentAt(count - 1);
            if (fragment != null && !fragment.onBackPressed()) {
                if (!pop()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        finishAfterTransition();
                    } else super.onBackPressed();
                }
                if(count == 1){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        finishAfterTransition();
                    } else super.onBackPressed();
                }
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else
                super.onBackPressed();
        }
    }

    protected void showToast(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(final int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }
}
