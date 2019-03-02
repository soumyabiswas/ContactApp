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

package com.travelyaari.tycorelib.ultlils;

import android.util.Log;

import com.travelyaari.tycorelib.Constants;
import com.travelyaari.tycorelib.CoreLib;


/**
 * Created by Triode on 5/5/2016.
 * Logger responsible for logging all
 */
public final class CoreLogger {

    /**
     * Constructs a new instance of {@code Object}.
     */
    private CoreLogger() {
        super();
    }


    /**
     * function load the message to the stack trace
     *
     * @param tag the tag to be appended against the message
     * @param message the message to be logged
     */
    public static final void log(final String tag, String message){
        Log.d(tag, message);
    }

    /**
     * function load the message to the stack trace, This log the message if the core lib
     * in debug mode @see{@link com.travelyaari.tycorelib.CoreLib#initAppConfig(Constants.Environments)}}
     *
     * @param tag the tag to be appended against the message
     * @param message the message to be logged
     */
    public static final void d(final String tag, String message){
        if(CoreLib.ismIsDebug())
            Log.d(tag + "", message + "");
    }

    /**
     * function load the message to the stack trace, This log the message if the core lib
     * in debug mode @see{@link com.travelyaari.tycorelib.CoreLib#initAppConfig(Constants.Environments)}}
     *
     * @param tag the tag to be appended against the message
     * @param message the message to be logged
     */
    public static final void i(final String tag, String message){
        if(CoreLib.ismIsDebug())
            Log.i(tag + "", message + "");
    }

    /**
     * function load the message to the stack trace, This log the message if the core lib
     * in debug mode @see{@link com.travelyaari.tycorelib.CoreLib#initAppConfig(Constants.Environments)}}
     *
     * @param tag       the tag to be appended against the message
     * @param throwable the throwable to be logged
     */
    public static final void e(final String tag, Throwable throwable) {
        if (CoreLib.ismIsDebug())
            Log.e(tag + "", throwable.getMessage() + "", throwable);
    }


}
