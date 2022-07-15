/*
 * Copyright 2013-2022 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.mnemonic;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;
import javax.swing.KeyStroke;

/**
 * Maps key strokes to names.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.1
 */
@Singleton
public class KeyStrokeMap {

    private Reference<Map<String, KeyStroke>> keyCodes;

    /**
     * Returns the key stroke for the name.
     * 
     * @param string
     *            the key stroke string.
     * 
     * @return the {@link KeyStroke} or {@code null} if the string is null, or
     *         is formatted incorrectly.
     * 
     * @see KeyStroke#getKeyStroke(String)
     */
    public KeyStroke getKeyCode(String string) {
        Map<String, KeyStroke> strokes = getKeyStrokes();
        KeyStroke stroke = strokes.get(string);
        if (stroke == null) {
            stroke = KeyStroke.getKeyStroke(string);
            strokes.put(string, stroke);
        }
        return stroke;
    }

    private Map<String, KeyStroke> getKeyStrokes() {
        if (isGarbageCollected(keyCodes)) {
            keyCodes = new SoftReference<>(
                    new HashMap<String, KeyStroke>());
        }
        return keyCodes.get();
    }

    private boolean isGarbageCollected(Reference<?> ref) {
        return ref == null || ref.get() == null;
    }
}
