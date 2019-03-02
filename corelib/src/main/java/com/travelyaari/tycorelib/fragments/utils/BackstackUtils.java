/*
 *  Copyright (c) 2016, Travelyaari and/or its affiliates. All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions
 *   are met:
 *
 *     - Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     - Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 *     - Neither the name of Travelyaari or the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 *   IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 *   THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *   PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 *   CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *   EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *   PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *   PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *   LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *   NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package com.travelyaari.tycorelib.fragments.utils;

import android.os.Bundle;

import com.travelyaari.tycorelib.fragments.atomic.FragmentStackFragment;

/**
 * Created by Triode on 5/5/2016.
 */
public class BackstackUtils {


    public static final int FRAGMENT_NO_ANIMATION = -1;
    public static final int FRAGMENT_DO_NOT_ANIMATE = -3;
    /**
     * Switch the Fragment enter and exit animation
     */
    public static boolean ALLOW_FRAGMENT_ANIMATION = true;

    /**
     * Class which will be used to create a fragment transaction
     */
    public static class FragmentTransaction {
        /**
         * Holds the class for the fragment to be pushed
         */
        public Class mFragmentClass;
        /**
         * Prameter to be passed to the newly created Fragment
         */
        public Bundle mParameters;

        /**
         * Decides it is root or not
         */
        public boolean isRoot;

        /**
         * Holds the id of the FrameLayout
         */
        public int mFrameId;

        /**
         * Indicates the animation to be performed when the fragment attached to the Window
         */
        public int mInAnimation = FRAGMENT_NO_ANIMATION;

        /**
         * Indicates the animation to be performed when the fragment de-attached from the Window
         */
        public int mPopOutAnimation = FRAGMENT_NO_ANIMATION;

        /**
         * Indicates the animation to be performed when the fragment attached to the Window
         */
        public int mPopInAnimation = FRAGMENT_NO_ANIMATION;

        /**
         * Indicates the animation to be performed when the fragment de-attached from the Window
         */
        public int mOutAnimation = FRAGMENT_NO_ANIMATION;


        /**
         * Function which create the Fragment and assign the arguments
         * @return MVPFragment return the created Fragment
         */
        public FragmentStackFragment compile(){
            FragmentStackFragment fragment = null;
            try {
                fragment = (FragmentStackFragment) mFragmentClass.newInstance();
                fragment.setArguments(mParameters);
                return fragment;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
