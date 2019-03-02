package com.travelyaari.tycorelib.dialogs;

import android.os.Bundle;

import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travelyaari.tycorelib.mvp.MVPView;

/**
 * Created by Triode on 6/29/2016.
 */
public abstract class MVDialog<V extends MVPView> extends PrimitiveDialog {


    /**
     * Holds the view instance
     */
    protected V mView;


    protected Bundle mSavedInstanceState;


    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p/>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mSavedInstanceState = savedInstanceState;
        try{
            initializeView(inflater, container);
            onBindView();
        }catch (java.lang.InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        return mView.getView();
    }

    /**
     * function create and initialize the enclosing view
     *
     * @throws java.lang.InstantiationException
     * @throws IllegalAccessException
     */
    protected final void initializeView(final LayoutInflater inflater, final ViewGroup parent)
            throws java.lang.InstantiationException, IllegalAccessException{
        mView = getViewClass().newInstance();
        mView.init(inflater, parent);
    }

    /**
     * Remove dialog.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mView.onDestroy();
        mView = null;
    }

    /**
     * Extending fragment should override this to provide the
     * enclosing view class
     *
     * @return the enclosing view class
     */
    protected abstract Class<V> getViewClass();


    /**
     * Function needs to override by the child classes to load
     * the contents and add the listeners
     *
     */
    protected abstract void onBindView();

}
