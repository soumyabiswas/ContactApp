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

package com.travelyaari.tycorelib;

import android.app.Application;

import com.travelyaari.tycorelib.events.Dispatcher;
import com.travelyaari.tycorelib.events.Event;
import com.travelyaari.tycorelib.events.EventListener;

/**
 * Created by Triode on 5/9/2016.
 */
public class TYApplication extends Application implements Dispatcher<EventListener> {


    /**
     * This method is for use in emulated process environments.  It will
     * never be called on a production Android device, where processes are
     * removed by simply killing them; no user code (including this callback)
     * is executed when doing so.
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        dumb();
    }

    /**
     * Add the listener to the listener map
     *
     * @param type     the type of the event against which the listener to be added
     * @param listener the listener to be invoked on event dispatch {@code EventListener}
     */
    @Override
    public void addEventListener(String type, EventListener listener) {
        CoreLib.getmDispatcher().addEventListener(type, listener);
    }

    /**
     * Remove the listener for the listener map
     *
     * @param type     the type of the event against which the listener to be removed
     * @param listener the listener to be removed
     */
    @Override
    public void removeEventListener(String type, EventListener listener) {
        CoreLib.getmDispatcher().removeEventListener(type, listener);
    }

    /**
     * Check the listener already registered or not
     *
     * @param type     type of the event against the existence to be checked
     * @param listener the listener to be mapped
     * @return returns true if mapped else false
     */
    @Override
    public boolean hasEventListener(String type, EventListener listener) {
        return CoreLib.getmDispatcher().hasEventListener(type, listener);
    }

    /**
     * The method notify the proper event listeners {@link EventListener#onEvent(Event)}
     *
     * @param event to be propagated to the listener
     */
    @Override
    public void dispatchEvent(Event event) {
        CoreLib.getmDispatcher().dispatchEvent(event);
    }


    public void delayedDispatch(final Event event){

    }

    /**
     * Function destroy the dispatcher
     */
    @Override
    public void dumb() {
        CoreLib.getmDispatcher().dumb();
    }
}
