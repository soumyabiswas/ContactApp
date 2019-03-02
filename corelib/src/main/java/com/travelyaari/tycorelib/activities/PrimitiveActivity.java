package com.travelyaari.tycorelib.activities;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.view.MenuItem;

import com.travelyaari.tycorelib.ComponentLifeCycleEvent;
import com.travelyaari.tycorelib.CoreLib;

/**
 * Created by triode on 16/8/16.
 */
public abstract class PrimitiveActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dispatchContentViewEvent(new ComponentLifeCycleEvent(ComponentLifeCycleEvent.VIEW_INFLATING,
                getClass().getSimpleName()));
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
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
    protected void dispatchContentViewEvent(final ComponentLifeCycleEvent event) {
        filter(event);
        CoreLib.getmDispatcher().dispatchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
