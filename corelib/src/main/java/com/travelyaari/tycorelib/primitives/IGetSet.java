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

/**
 * Created by Triode on 5/5/2016.
 */
public interface IGetSet {

    /**
     * Function save the value against the key provided
     * @param key of type String, The key against which the value should be saved
     * @param value of type String, The value which needs to be saved against key
     */
    boolean put(String key, String value);

    /**
     * Function save the value against the key provided
     * @param key of type String, The key against which the value should be saved
     * @param value of type float, The value which needs to be saved against key
     */
    boolean put(String key, float value);
    /**
     * Function save the value against the key provided
     * @param key of type String, The key against which the value should be saved
     * @param value of type int, The value which needs to be saved against key
     */
    boolean put(String key, int value);

    /**
     * Function save the value against the key provided
     * @param key of type String, The key against which the value should be saved
     * @param value of type boolean, The value which needs to be saved against key
     */
    boolean put(String key, boolean value);


    /**
     * Function return the value which is saved against the key
     * @param key of type String
     * @param fallBack value needs to be send if the value not present
     * @return of type int
     */
    int getInteger(String key, int fallBack);

    /**
     * Function return the value which is saved against the key
     * @param key of type String
     * @param fallBack value needs to be send if the value not present
     * @return of type String
     */
    String getString(String key, String fallBack);

    /**
     * Function return the value which is saved against the key
     * @param key of type String
     * @param fallBack value needs to be send if the value not present
     * @return of type float
     */
    float getFloat(String key, float fallBack);

    /**
     * Function return the value which is saved against the key
     * @param key of type String
     * @param fallBack value needs to be send if the value not present
     * @return of type boolean
     */
    boolean getBoolean(String key, boolean fallBack);
}
