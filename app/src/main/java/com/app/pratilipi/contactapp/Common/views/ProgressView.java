package com.app.pratilipi.contactapp.Common.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.pratilipi.contactapp.AppModule;
import com.app.pratilipi.contactapp.R;
import com.travelyaari.tycorelib.Constants;
import com.travelyaari.tycorelib.events.CoreEvent;
import com.travelyaari.tycorelib.mvp.ViewState;
import com.travelyaari.tycorelib.mvp.ViewStateView;
import com.travelyaari.tycorelib.primitives.ILoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public abstract class ProgressView<VS extends ViewState, T extends Object>
        extends ViewStateView<VS>
        implements ILoadingView<T> {
    public VS mViewState;
    @BindView(R.id.progress_bar)
    protected ProgressBar mProgressBar;

    @BindView(R.id.loading_message)
    protected TextView mLoadingTextView;
    @BindView(R.id.retry_button)
    protected Button mRetryButton;
    @BindView(R.id.loading_layout)
    protected View mLoadingLayout;
    Unbinder unbinder;

    /**
     * function handles the click event for the button
     * notify other components by dispatching an event
     * {@link com.app.pratilipi.contactapp.Constants}
     */
    @OnClick(R.id.retry_button)
    protected void retry() {
        final CoreEvent searchEvent = new CoreEvent
                (getRetryEventName(), null);
        AppModule.getmModule().dispatchEvent(searchEvent);
    }

    /**
     * To handle the back press
     *
     * @return false if not handled true if handled
     */
    @Override
    public boolean onBackPressed() {
        return false;
    }

    /**
     * Child classes should override this method in order to
     * set the layout resource for the view if it is not overridden this will
     * leads to crashes
     *
     * @return the layout resource id associated with the view
     */
    protected abstract int getLayoutID();

    /**
     * function which returns the content view
     *
     * @return teh content view associated with the View
     */
    protected abstract View getContentView();

    /**
     * function return the name of the event to be dispatched on click
     * of retry button
     *
     * @return the name of the event
     */
    protected abstract String getRetryEventName();

    /**
     * function initialize the view state
     */
    protected abstract void initializeViewState();

    /**
     * function will be called once inflation is done for the
     * View
     */
    protected abstract void onInflate();

    /**
     * Create the view from the id provided
     *
     * @param inflater inflater using which the view shold be inflated
     * @param parent   to which the view to be attached
     */
    @Override
    public final void init(LayoutInflater inflater, ViewGroup parent) {
        mView = inflater.inflate(getLayoutID(), parent, false);
        unbinder = ButterKnife.bind(this, mView);
        onInflate();
        if(mViewState == null){
            initializeViewState();
        }
    }

    /**
     * function called on destroy of the view
     */
    @Override
    public void onDestroy() {
        unbinder.unbind();
        unbinder = null;
    }

    /**
     * function save the sate of the view and return it
     *
     * @return the {@code ViewState} of the enclosing view of the
     * fragment
     */
    @Override
    public VS saveViewState() {
        return mViewState;
    }

    /**
     * function restore the state of the view
     *
     * @param viewState the view state saved
     */
    @Override
    public void restoreViewState(VS viewState) {
        mViewState = viewState;
        restoreView();
    }


    /**
     * function restore the view
     */
    void restoreView() {
        switch (mViewState.mViewState) {
            case ViewState.VIEW_STATE_EMPTY_RESULT: {
                showError(Constants.ErrorCodes.EMPTY_RESULT);
                break;
            }
            case ViewState.VIEW_STATE_LOADING: {
                showLoading();
                break;
            }
            case ViewState.VIEW_STATE_NETWORK_ERROR: {
                showError(Constants.ErrorCodes.NETWORK_ERROR);
                break;
            }
            case ViewState.VIEW_STATE_SERVER_ERROR: {
                showError(Constants.ErrorCodes.SERVER_ERROR);
                break;
            }
            case ViewState.VIEW_STATE_COMPLETE:{
                mLoadingLayout.setVisibility(View.GONE);
                getContentView().setVisibility(View.VISIBLE);
                break;
            }
        }
    }


    /**
     * function which will be called after all the view creation completes
     */
    @Override
    public void onViewComplete() {
    }


    /**
     * Function to the loading view
     */
    @Override
    public void showLoading() {
        mLoadingLayout.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mLoadingTextView.setText(mView.getContext().getString(R.string.message_loading));

        mRetryButton.setVisibility(View.GONE);
        mViewState.mViewState = ViewState.VIEW_STATE_LOADING;
    }

    /**
     * function hide the loading screen
     */
    @Override
    public void hideLoading() {
        mLoadingLayout.setVisibility(View.GONE);
    }

    /**
     * functions shows the error if the loading fails
     *
     * @param error
     */
    @Override
    public void showError(Constants.ErrorCodes error) {
        mLoadingLayout.setVisibility(View.VISIBLE);
        if (error == Constants.ErrorCodes.NETWORK_ERROR) {
            mViewState.mViewState = ViewState.VIEW_STATE_NETWORK_ERROR;
            mLoadingTextView.setText(R.string.message_check_internet);
            mRetryButton.setVisibility(View.VISIBLE);
        } else if (Constants.ErrorCodes.SERVER_ERROR == error) {
            mViewState.mViewState = ViewState.VIEW_STATE_SERVER_ERROR;
            mLoadingTextView.setText(R.string.message_unable_to_fetch_result);
            mRetryButton.setVisibility(View.VISIBLE);
        } else {
            mViewState.mViewState = ViewState.VIEW_STATE_EMPTY_RESULT;
            mLoadingTextView.setText(mView.getContext().getString(R.string.message_no_result));
            mRetryButton.setVisibility(View.GONE);
        }

        mProgressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * function which will show the result in the view
     *
     * @param result the result to be shown
     */
    @Override
    public void showResult(T result) {
        mViewState.mViewState = ViewState.VIEW_STATE_COMPLETE;
        mLoadingLayout.setVisibility(View.GONE);
        getContentView().setVisibility(View.VISIBLE);
    }
}
