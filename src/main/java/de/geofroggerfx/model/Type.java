/*
 * Copyright (c) Andreas Billmann <abi@geofroggerfx.de>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package de.geofroggerfx.model;

import groovy.lang.Closure;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;

/**
 * Created by Andreas Billmann on 22.02.2015.
 */
public enum Type {
    TRADITIONAL_CACHE("Traditional Cache"),
    MULTI_CACHE("Multi-cache"),
    UNKNOWN_CACHE("Unknown Cache"),
    EARTH_CACHE("Earthcache"),
    LETTERBOX("Letterbox Hybrid"),
    EVENT("Event Cache"),
    WHERIGO("Wherigo Cache"),
    WEBCAM_CACHE("Webcam Cache"),
    VIRTUAL_CACHE("Virtual Cache"),
    CITO_EVENT("Cache In Trash Out Event"),
    MEGA_EVENT("Mega-Event Cache"),
    MYSTERY_CACHE("Mystery Cache");

    private String groundspeakString;

    private Type(String groundspeakString) {
        this.groundspeakString = groundspeakString;
    }

    public String toGroundspeakString() {
        return groundspeakString;
    }

    public static Type groundspeakStringToType(String groundspeakString) {
        for (Type t : Type.values()) {
            if (t.toGroundspeakString().equals(groundspeakString)) {
                return t;
            }
        }
        throw new IllegalArgumentException("unknown type:" + groundspeakString);
    }

}
