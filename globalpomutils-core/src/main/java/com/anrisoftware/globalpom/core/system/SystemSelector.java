/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anrisoftware.globalpom.core.system;

/*-
 * #%L
 * Global POM Utilities :: Core
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.anrisoftware.globalpom.core.system.SystemSelector;

/**
 * Selects the operating system based on the system property.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
public enum SystemSelector {

    /**
     * Unix operating system.
     */
    UNIX("unix"),

    /**
     * Windows operating system.
     */
    WINDOWS("windows"),

    /**
     * MacOS operating system.
     */
    MAC("max"),

    /**
     * Solaris operating system.
     */
    SOLARIS("solaris"),

    /**
     * Unknown operating system.
     */
    UNKNOWN("unknown");

    /**
     * Selects the operating system based on the system property.
     * 
     * @return one of the {@link SystemSelector} enumeration.
     */
    public static SystemSelector getSystem() {
        String os = System.getProperty("os.name").toLowerCase();
        if (isWindows(os)) {
            return WINDOWS;
        } else if (isMac(os)) {
            return MAC;
        } else if (isUnix(os)) {
            return UNIX;
        } else if (isSolaris(os)) {
            return SOLARIS;
        } else {
            return UNKNOWN;
        }
    }

    private static boolean isSolaris(String string) {
        return (string.indexOf("sunos") >= 0);
    }

    private static boolean isUnix(String string) {
        return (string.indexOf("nix") >= 0 || string.indexOf("nux") >= 0);
    }

    private static boolean isMac(String string) {
        return (string.indexOf("mac") >= 0);
    }

    private static boolean isWindows(String string) {
        return (string.indexOf("win") >= 0);
    }

    private final String name;

    private SystemSelector(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
