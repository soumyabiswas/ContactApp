package com.travelyaari.tycorelib.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.travelyaari.tycorelib.mvp.MVPPresenter;
import com.travelyaari.tycorelib.mvp.ViewState;
import com.travelyaari.tycorelib.mvp.ViewStateView;

/**
 * Created by Triode on 6/21/2016.
 */
public abstract class OptionMenuFragment<V extends ViewStateView, P extends MVPPresenter<V>,
        VS extends ViewState, FS extends FragmentState> extends ViewStateFragment<V, P, VS, FS> {



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
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * called when the menu creation happens for the fragment
     *
     * @param menu the menu object
     * @param inflater the inflater to inflate the menu
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(getOptionMenu(), menu);
    }

    /**
     * called post creation of the menu this allow the fragment
     * to modify the menu items
     * @param menu the created menu object
     */
    @Override
    public final void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        final int[] items = getDisabledOptionMenuIds();
        if(items != null) {
            for (int i : items) {
                MenuItem item = menu.findItem(i);
                item.setVisible(false);
            }
        }
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p/>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        return onOptionMenuSelected(item);
    }

    /**
     * function to override to set the option menu for the fragment
     *
     * @return the resource id of the option menu
     */
    protected abstract int getOptionMenu();

    /**
     * this will return the disabled option menu ids for the fragment
     * this allows to share the same menu across the fragments
     *
     * @return ids of options menus to be disabled
     */
    protected abstract int[] getDisabledOptionMenuIds();

    /**
     * Called on selection of any menu item
     *
     * @param item the item selected
     */
    protected abstract boolean onOptionMenuSelected(final MenuItem item);

    @Override
    public void onStart() {
        super.onStart();
        getActivity().supportInvalidateOptionsMenu();
    }
}
