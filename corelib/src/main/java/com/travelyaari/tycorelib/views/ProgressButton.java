package com.travelyaari.tycorelib.views;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IntDef;
import androidx.annotation.UiThread;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.travelyaari.tycorelib.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Triode on 6/8/2016.
 */

@UiThread
public class ProgressButton extends Button{



    /** @ViewState */
    @IntDef({NORMAL, PROGRESSIVE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewStatus {}



    /**
     * The states for the Button

     */
    public static final int NORMAL      = 0x00000000;
    public static final int PROGRESSIVE = 0x00000004;

    @ProgressButton.ViewStatus int mViewStatus = NORMAL;

    /**
     * setter function for ViewState
     *
     * @param mViewStatus the state needs to preserved
     */
    public void setmViewStatus(@ViewStatus int mViewStatus) {
        this.mViewStatus = mViewStatus;
        toggleProgress();
    }

    /**
     * getter function of #mViewStatus
     *
     * @return the current #mViewStatus
     */
    public @ViewStatus int getmViewStatus() {
        return mViewStatus;
    }


    private Drawable mProgressDrawable;


    public ProgressButton(Context context) {
        this(context, null);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * function which will initialize the view
     */
    private void init(final AttributeSet attrs){
        mProgressDrawable = ContextCompat.getDrawable(getContext(), R.drawable.progress_drawable);
        setSaveEnabled(true);
    }

    /**
     * function override the state of super and append the state
     * of the {@code ProgressButton}
     * @retthe combined state
     */
    @Override
    public Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        final ProgressButtonState state = new ProgressButtonState(superState);
        state.viewState = mViewStatus;
        return state;
    }

    /**
     * function restore the state of the view
     * @param state the saved state
     */
    @Override
    public void onRestoreInstanceState(Parcelable state) {
        final ProgressButtonState ss = (ProgressButtonState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setmViewStatus(ss.viewState);
    }


    /**
     * function which will toggle the progress based
     * on the #mViewStatus
     */
    void toggleProgress(){
        if(mViewStatus == NORMAL){
            setCompoundDrawables(null, null, null, null);
        }else{
            setCompoundDrawablesWithIntrinsicBounds(null, null, mProgressDrawable, null);
            updateAnimationsState(mProgressDrawable, true);
        }
    }



    private void updateAnimationsState(Drawable drawable, boolean running) {
        if(drawable instanceof AnimationDrawable) {
            Animatable animationDrawable = (Animatable) drawable;
            if(running) {
                animationDrawable.start();
            } else {
                animationDrawable.stop();
            }
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        updateAnimationsState(mProgressDrawable, (mViewStatus == PROGRESSIVE));
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        updateAnimationsState(mProgressDrawable, (mViewStatus == PROGRESSIVE));
    }


    /**
     * holds the state of the view
     */
    static class ProgressButtonState extends BaseSavedState{

        int viewState = NORMAL;
        ProgressButtonState(Parcelable superState) {
            super(superState);
        }

        private ProgressButtonState(Parcel in) {
            super(in);
            viewState = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(viewState);
        }

        public static final Parcelable.Creator<ProgressButtonState> CREATOR
                = new Parcelable.Creator<ProgressButtonState>() {
            public ProgressButtonState createFromParcel(Parcel in) {
                return new ProgressButtonState(in);
            }

            public ProgressButtonState[] newArray(int size) {
                return new ProgressButtonState[size];
            }
        };
    }
}
