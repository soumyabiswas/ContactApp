package com.travelyaari.tycorelib.dialogs;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travelyaari.tycorelib.ComponentLifeCycleEvent;
import com.travelyaari.tycorelib.CoreLib;
import com.travelyaari.tycorelib.ultlils.CoreLogger;

/**
 * Created by triode on 16/8/16.
 */
public abstract class PrimitiveDialog extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dispatchContentViewEvent(new ComponentLifeCycleEvent(ComponentLifeCycleEvent.VIEW_INFLATING,
                getClass().getSimpleName()));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dispatchContentViewEvent(new ComponentLifeCycleEvent(ComponentLifeCycleEvent.VIEW_INFLATED,
                getClass().getSimpleName()));
    }

    /**
     * function which allows the child classes to modify the
     * contents of the event
     *
     * @param event the event about to be dispatched
     *
     */
    protected void filter(final ComponentLifeCycleEvent event) {
    }

    /**
     * function dispatch the content view event
     */
    protected void dispatchContentViewEvent(final ComponentLifeCycleEvent mViewEvent) {
        filter(mViewEvent);
        CoreLib.getmDispatcher().dispatchEvent(mViewEvent);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (IllegalStateException e) {
            CoreLogger.e("PrimitiveDialog", e);
        }
    }
}
