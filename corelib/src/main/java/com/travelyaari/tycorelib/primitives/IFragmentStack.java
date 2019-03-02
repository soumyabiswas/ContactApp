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

package com.travelyaari.tycorelib.primitives;

import androidx.fragment.app.Fragment;

import com.travelyaari.tycorelib.fragments.utils.BackstackUtils;

/**
 * Created by Triode on 5/5/2016.
 */
public interface IFragmentStack<T extends Fragment> {


    /**
     * Function which will be used to pop a Fragment from the backstack
     * @return true success otherwise false
     */
    boolean pop();

    /**
     * Function which push a new Fragment to the backstac
     * @param transaction of type BackstackUtils.FragmentTransaction
     */
    void push(BackstackUtils.FragmentTransaction transaction);

    /**
     * Function which will pop all the fragments till the first occurrence of the class name
     * @param fragmentName name of the Fragment
     */
    void popUntilFirstOccurrence(String fragmentName);


    /**
     * Function which will pop all the fragments till the first occurrence of the class name
     * @param fragmentName name of the Fragment
     */
    void popUntilLastOccurrence(String fragmentName);

    /**
     * function which will clear the backstack of fragments, This also prevent the fragment animations
     * @return boolean return true success else false
     */
    boolean clearBackstack();

    /**
     * function returns the fragment at the position provided
     * @param position  index at which the fragment to be returned
     * @return {@link com.travelyaari.tycorelib.fragments.MVPFragment}
     */
    T getFragmentAt(int position);
}
