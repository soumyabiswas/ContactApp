package com.travelyaari.tycorelib.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.travelyaari.tycorelib.fragments.FragmentState;
import com.travelyaari.tycorelib.mvp.MVPPresenter;
import com.travelyaari.tycorelib.mvp.ViewState;
import com.travelyaari.tycorelib.mvp.ViewStateView;
import com.travelyaari.tycorelib.primitives.IViewState;

/**
 * Created by himanshubaghel on 12/07/17.
 */

public abstract class ViewStateActivity<V extends ViewStateView, P extends MVPPresenter<V>,
        VS extends ViewState, FS extends FragmentState>
        extends MVPActivity<V, P> implements IViewState<VS> {

    /**
     * The tag against the view state will be saved
     */
    public static final String VIEW_STATE = "internalSavedViewState8954201239547";

    /**
     * The tag against the view state will be saved
     */
    public static final String FRAGMENT_STATE = "internalSavedFragmentState8954201239547";
    protected boolean mRetainState = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        restoreFromFragmentState(savedInstanceState);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onViewComplete(@Nullable Bundle savedInstanceState) {
        restoreFromViewState(savedInstanceState);
        mView.onViewComplete();
    }

    /**
     * function check for the saved view state in the argument and
     * restore the view state
     *
     * @return true if restored otherwise false
     */
    private boolean restoreFromViewState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(VIEW_STATE)) {
            final VS mViewState = savedInstanceState.getParcelable(VIEW_STATE);
            restoreViewState(mViewState);
            return true;
        }
        return false;
    }

    /**
     * function try to restore the saved fragment state
     *
     * @param savedInstanceState the saved instance state
     * @return
     */
    private boolean restoreFromFragmentState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(FRAGMENT_STATE)) {
            final FS mFragmentState = savedInstanceState.getParcelable(FRAGMENT_STATE);
            restoreFragmentState(mFragmentState);
            return true;
        }
        return false;
    }

    /**
     * to save the fragment state
     *
     * @param outState
     */
    @Override
    public final void onSaveInstanceState(Bundle outState) {
        final FS state = saveFragmentState();
        if (state != null)
            outState.putParcelable(FRAGMENT_STATE, state);

        VS viewState = saveViewState();
        if (viewState != null) {
            outState.putParcelable(VIEW_STATE, viewState);
        }
        super.onSaveInstanceState(outState);
    }


    /**
     * function needs to be override to save the fragment state
     */
    protected abstract FS saveFragmentState();

    /**
     * function needs to be override to save the fragment state
     */
    protected abstract void restoreFragmentState(FS state);

    /**
     * function save the sate of the view and return it
     *
     * @return the {@code ViewState} of the enclosing view of the
     * fragment
     */
    @Override
    public VS saveViewState() {
        return (VS) mView.saveViewState();
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
}
