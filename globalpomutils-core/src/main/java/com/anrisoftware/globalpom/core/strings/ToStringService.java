/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.strings;

import java.util.Map;

/**
 * Converts an argument to a String.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public interface ToStringService {

    /**
     * Converts the specified argument to a {@link String}.
     *
     * @param args the {@link Map} arguments.
     *
     * @param name the {@link String} name.
     *
     * @return the {@link String}
     *
     * @throws NullPointerException if the argument is {@code null}.
     */
    String toString(Map<String, Object> args, String name);

    /**
     * Converts the specified argument to a {@link String}.
     *
     * @param arg the argument.
     *
     * @param name the {@link String} name.
     *
     * @return the {@link String}
     *
     * @throws NullPointerException if the argument is {@code null}.
     */
    String toString(Object arg, String name);
}
