package com.travelyaari.tycorelib.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.travelyaari.tycorelib.mvp.MVPView;

/**
 * Created by Triode on 6/29/2016.
 */
public abstract class MVActivity<V extends MVPView> extends FragmentStackActivity {


    /**
     * Holds the view instance
     */
    protected V mView;


    /**
     * Function needs to override by the child classes to load
     * the contents and add the listeners
     *
     */
    protected abstract void onBindView();

    /**
     * Function called after setcontentView and before bindView to be used while restoring views
     */
    protected void onViewComplete(@Nullable Bundle savedInstanceState) {

    }


    /**
     * Extending fragment should override this to provide the
     * enclosing view class
     *
     * @return the enclosing view class
     */
    protected abstract Class<V> getViewClass();

    /**
     * function create and initialize the enclosing view
     *
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    protected final void initializeView() throws InstantiationException, IllegalAccessException{
        mView = getViewClass().newInstance();
        mView.init(getLayoutInflater(), null);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initializeView();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        setContentView(mView.getView());
        onViewComplete(savedInstanceState);
        onBindView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mView.onDestroy();
        mView = null;
    }

    /**
     * Called when the main window associated with the activity has been
     * attached to the window manager.
     */
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /**
     * function called when activity detached from window manager
     */
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mView = null;
    }


    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        if (mView == null || !mView.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
