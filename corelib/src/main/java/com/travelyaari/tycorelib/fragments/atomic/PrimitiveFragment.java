package com.travelyaari.tycorelib.fragments.atomic;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travelyaari.tycorelib.ComponentLifeCycleEvent;
import com.travelyaari.tycorelib.CoreLib;

/**
 * Created by triode on 16/8/16.
 */
public abstract class PrimitiveFragment extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dispatchContentViewEvent(new ComponentLifeCycleEvent(ComponentLifeCycleEvent.VIEW_INFLATING,
                getClass().getSimpleName()));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dispatchContentViewEvent(new ComponentLifeCycleEvent(ComponentLifeCycleEvent.VIEW_INFLATED,
                getClass().getSimpleName()));
    }
}
