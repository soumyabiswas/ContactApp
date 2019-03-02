package com.travelyaari.tycorelib.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travelyaari.tycorelib.fragments.atomic.FragmentStackFragment;
import com.travelyaari.tycorelib.mvp.MVPView;

/**
 * Created by Triode on 6/29/2016.
 */
public abstract class MVFragment<V extends MVPView> extends FragmentStackFragment {


    /**
     * Holds the view instance
     */
    protected V mView;


    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        try{
            initializeView(inflater, container);
            onViewComplete();
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
    protected final void initializeView(final LayoutInflater  inflater, final ViewGroup parent)
            throws java.lang.InstantiationException, IllegalAccessException{
        mView = getViewClass().newInstance();
        mView.init(inflater, parent);
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


    /**
     * Called when the view previously created by {@link #onCreateView} has
     * been detached from the fragment.  The next time the fragment needs
     * to be displayed, a new view will be created.  This is called
     * after {@link #onStop()} and before {@link #onDestroy()}.  It is called
     * <em>regardless</em> of whether {@link #onCreateView} returned a
     * non-null view.  Internally it is called after the view's state has
     * been saved but before it has been removed from its parent.
     */
    @Override
    public void onDestroyView() {
        mView.onDestroy();
        mView = null;
        super.onDestroyView();
    }

    /**
     * function which called after view initialized
     */
    protected void onViewComplete(){
        onBindView();
    }

    /**
     * function redirect the call to enclosing view lto check
     * back button needs to be handled or not
     *
     * @return true if handled otherwise false
     */
    public boolean onBackPressed(){
        return mView.onBackPressed();
    }


}
