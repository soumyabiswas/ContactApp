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

import android.os.Bundle;


/**
 * Created by Triode on 5/9/2016.
 * Class act as the bas class for all the vents in the
 * System
 */
public class CoreEvent implements Event {


    /**
     * getter function for the {@code mExtra}
     *
     * @return the {@code mExtra}
     */
    public Bundle getmExtra() {
        return mExtra;
    }

    /**
     * function check event dispatched with bundle or not
     *
     * @return true if exist else flase
     */
    @Override
    public boolean hasExtra() {
        return mExtra != null;
    }

    /**
     * Holds the extra values about the event
     */
    protected final Bundle mExtra;

    /**
     * Constructs a new instance of {@code Object}.
     *
     * @param type the type of th event
     */
    public CoreEvent(final String type, final Bundle mExtra) {
        super();
        mType = type;
        this.mExtra = mExtra;
    }

    /**
     * Holds the type of the event
     */
    private final String mType;

    /**
     * Event propagation source
     */
    private Object mSource;

    /**
     * Function return the type of event
     *
     * @return of type {@link String}
     */
    @Override
    public String getType() {
        return mType;
    }

    /**
     * To get the creator or source of the event
     *
     * @return of type {@link Object}
     */
    @Override
    public Object getSource() {
        return mSource;
    }

    /**
     * To set the source of the event propagation
     *
     * @param object the object from which the event is triggered
     */
    @Override
    public void setSource(Object object) {
        this.mSource = object;
    }
}
