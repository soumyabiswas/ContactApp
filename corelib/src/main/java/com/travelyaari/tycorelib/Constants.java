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

/**
 * Created by Triode on 5/5/2016.
 * Holds all the constants required for the lib
 */
public final class Constants {


    public static final String _ID = "_id";
    public static final String _createdAt = "createdAt";
    public static final String _updatedAt = "updatedAt";
    public static final String _user_email = "email";
    public static final String _user_name = "name";
    public static final String _user_password = "password";
    public static final String _user_phone = "phone";
    /**
     * Name of the user table
     */
    public static final String USER = "_user";
    /**
     * Constructs a new instance of {@code Object}.
     */
    private Constants() {
        super();
    }

    /**
     * The possible error codes to be injected to the
     * view
     */
    public static enum ErrorCodes {
        NETWORK_ERROR, EMPTY_RESULT,
        SERVER_ERROR
    }

    /**
     * Configuration type for app
     */
    public static enum Environments {
        DEV_ENV,
        STAGE_ENV,
        PRODUCTION_ENV;
    }
}
