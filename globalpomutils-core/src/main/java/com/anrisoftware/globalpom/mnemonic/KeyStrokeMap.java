/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.mnemonic;

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
            keyCodes = new SoftReference<Map<String, KeyStroke>>(
                    new HashMap<String, KeyStroke>());
        }
        return keyCodes.get();
    }

    private boolean isGarbageCollected(Reference<?> ref) {
        return ref == null || ref.get() == null;
    }
}
