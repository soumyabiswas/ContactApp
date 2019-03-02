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

package com.travelyaari.tycorelib.events;

import androidx.annotation.NonNull;

import com.travelyaari.tycorelib.ultlils.CoreLogger;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Created by Triode on 5/9/2016.
 * The class responsible for dispatching the event to the
 * observers
 */
public class EventDispatcher implements Dispatcher<EventListener> {


    private static final String TAG = EventDispatcher.class.getSimpleName();
    /**
     * Singleton instance Global to the app
     */
    private static EventDispatcher mInstance;
    /**
     * The map in which all the listeners will be saved
     */
    private HashMap<String, CopyOnWriteArrayList<EventListener>> mListenersMap;

    /**
     * Constructs a new instance of {@code Object}.
     */
    public EventDispatcher() {
        super();
        mListenersMap = new HashMap<>();
    }

    /**
     * getter method for mInstance
     *
     * @return Constructs a new instance of {@code EventDispatcher}
     */
    public static EventDispatcher acquire() {
        mInstance = (mInstance == null) ? new EventDispatcher() :
                mInstance;
        return mInstance;
    }

    /**
     * Add the listener to the listener map
     *
     * @param type     the type of the event against which the listener to be added
     * @param listener the listener to be invoked on event dispatch {@code EventListener}
     */
    @Override
    public void addEventListener(String type, EventListener listener) {
        CoreLogger.log("Started Listening For " + type, listener.getClass().getName());
        synchronized (mListenersMap) {
            CopyOnWriteArrayList<EventListener> listenerList = mListenersMap.get(type);
            if (listenerList == null) {
                listenerList = new CopyOnWriteArrayList<>();
                mListenersMap.put(type, listenerList);
            }
            listenerList.add(listener);
        }
    }

    /**
     * Remove the listener for the listener map
     *
     * @param type     the type of the event against which the listener to be removed
     * @param listener the listener to be removed
     */
    @Override
    public void removeEventListener(@NonNull String type, @NonNull EventListener listener) {
        CoreLogger.log("About to remove " + type + " for", listener.getClass().getName());
        synchronized (mListenersMap) {
            final CopyOnWriteArrayList<EventListener> listeners = mListenersMap.get(type);
            if (listeners == null) {
                CoreLogger.log("Invalid call to remove " + type + " for", listener.getClass().getName());
                return;
            }
            boolean flag = listeners.remove(listener);
            CoreLogger.log((flag ? "Stopped" : "Unable to stop") + " listening for " + type, listener.getClass().getName());
            if (listeners.size() == 0) {
                mListenersMap.remove(type);
                CoreLogger.log("Removed listener for " + type, listener.getClass().getName());
            }
        }
    }

    /**
     * Check the listener already registered or not
     *
     * @param type     type of the event against the existence to be checked
     * @param listener the listener to be mapped
     * @return returns true if mapped else false
     */
    @Override
    public boolean hasEventListener(String type, @NonNull EventListener listener) {
        synchronized (mListenersMap) {
            final CopyOnWriteArrayList<EventListener> listeners = mListenersMap.get(type);
            if (listeners == null)
                return false;
            return listeners.contains(listener);
        }
    }

    /**
     * The method notify the proper event listeners {@link EventListener#onEvent(Event)}
     *
     * @param event to be propagated to the listener
     */
    @Override
    public void dispatchEvent(@NonNull Event event) {
        if (event == null) {
            CoreLogger.i(TAG, "Event object can not be null");
            return;
        }
        final String type = event.getType();
        event.setSource(this);
        final CopyOnWriteArrayList<EventListener> listeners;
        synchronized (mListenersMap) {
            listeners = mListenersMap.get(type);
        }
        if (listeners == null)
            return;
        for (EventListener listener : listeners) {
            listener.onEvent(event);
        }
    }

    /**
     * Function destroy the dispatcher
     */
    @Override
    public void dumb() {
        synchronized (mListenersMap) {
            mListenersMap.clear();
        }
    }
}
