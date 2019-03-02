package com.travelyaari.tycorelib;

import androidx.annotation.IntDef;

import com.travelyaari.tycorelib.events.Event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by triode on 18/8/16.
 */
public class ComponentLifeCycleEvent implements Event {

    public static final String COMPONENT_LIFE_CYCLE_EVENT = "COMPONENT_LIFE_CYCLE_EVENT";

    public static final int VIEW_INFLATING = 0x00;
    public static final int VIEW_INFLATED = 0x01;

    private final
    @LifeCycleType
    int mCategory;
    private String mComponentName;

    /**
     * Construct the instance of {@code}
     *
     * @param mCategory     the category {@link LifeCycleType}
     * @param componentName the name of the component
     */
    public ComponentLifeCycleEvent(final @LifeCycleType int mCategory,
                                   String componentName) {
        super();
        this.mCategory = mCategory;
        mComponentName = componentName;
    }

    public int getmCategory() {
        return mCategory;
    }

    public String getmComponentName() {
        return mComponentName;
    }

    public void setmComponentName(String mComponentName) {
        this.mComponentName = mComponentName;
    }

    /**
     * To get the creator or source of the event
     *
     * @return of type {@link Object}
     */
    @Override
    public Object getSource() {
        return null;
    }

    /**
     * To set the source of the event propagation
     *
     * @param object the object from which the event is triggered
     */
    @Override
    public void setSource(Object object) {

    }

    /**
     * Function return the type of event
     *
     * @return of type {@link String}
     */
    @Override
    public String getType() {
        return COMPONENT_LIFE_CYCLE_EVENT;
    }

    /**
     * function check event dispatched with bundle or not
     *
     * @return true if exist else flase
     */
    @Override
    public boolean hasExtra() {
        return true;
    }

    /**
     * @ViewState
     */
    @IntDef({VIEW_INFLATING, VIEW_INFLATED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LifeCycleType {
    }
}
