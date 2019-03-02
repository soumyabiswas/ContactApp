package com.travelyaari.tycorelib.fragments;

import android.os.Bundle;
import com.travelyaari.tycorelib.mvp.ViewState;
import com.travelyaari.tycorelib.mvp.ViewStateView;
import com.travelyaari.tycorelib.primitives.IViewState;

/**
 * Created by triode on 28/7/16.
 */
public abstract class RetainViewStateFragment<V extends ViewStateView,
        VS extends ViewState>
        extends MVFragment<V> implements IViewState<VS>{

    /**
     * Construct the new instance
     * <p/>
     * return the instance of {@code ViewStateFragment}
     */
    public RetainViewStateFragment() {
        if (getArguments() == null) {
            setArguments(new Bundle());
        }

    }



    /**
     * The tag against the view state will be saved
     */
    public static final String VIEW_STATE = "internalSavedViewState8954201239547";




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
        saveState();
        super.onDestroyView();
    }

    /**
     * function save the view state to the arguments
     *
     */
    private void saveState(){
        VS viewState = saveViewState();
        if(viewState != null){
            getArguments().putParcelable(VIEW_STATE, viewState);
        }
    }

    /**
     * function called on when the view added kto the window
     * and the activity is created for the same
     */
    @Override
    protected void onViewComplete() {
        if(!restoreFromArguments())
            onBindView();
        mView.onViewComplete();
    }

    /**
     * function check for the saved view state in the argument and
     * restore the view state
     * @return true if restored otherwise false
     */
    private boolean restoreFromArguments(){
        final Bundle b = getArguments();
        if (b != null) {
            VS viewState = getSavedViewState();
            if (viewState != null) {
                restoreViewState(viewState);
                return true;
            }
        }
        return false;
    }

    /**
     * function which will return the saved view state
     *
     * @return the view state of the enclosed view s
     */
    protected VS getSavedViewState(){
        return (VS)getArguments().getParcelable(VIEW_STATE);
    }


    /**
     * function restore the state of the view
     *
     * @param viewState the view state saved
     */
    @Override
    public void restoreViewState(VS viewState) {
        mView.restoreViewState(viewState);
    }

    /**
     * function save the sate of the view and return it
     *
     * @return the {@code ViewState} of the enclosing view of the
     * fragment
     */
    @Override
    public VS saveViewState() {
        return (VS)mView.saveViewState();
    }
}
