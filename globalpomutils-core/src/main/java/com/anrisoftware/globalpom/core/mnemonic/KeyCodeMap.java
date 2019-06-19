/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
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

import static java.lang.reflect.Modifier.isStatic;

import java.awt.event.KeyEvent;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

/**
 * Maps key codes to names.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
@Singleton
public class KeyCodeMap {

    private Reference<Map<String, Integer>> keyCodes;

    /**
     * Tests if the specified string is probably a valid key code. The string is
     * probably a valid key code if the string is a single character or begins
     * with {@code "VK_"}. Not tested is whether or not the string actually is a
     * valid key code.
     * 
     * @param string
     *            the {@link String}.
     * 
     * @return {@code true} if the specified string is probably a valid key
     *         code.
     * 
     * @since 1.6
     */
    public boolean valid(String string) {
        if (string.length() > 1) {
            return string.startsWith("VK_") ? true : false;
        }
        return string.length() == 1;
    }

    /**
     * Returns the key code for the name. The name can be a character or a key
     * code constant starting with {@code "VK_"} or key mask that ends with
     * {@code "_MASK"}.
     * 
     * @param keyname
     *            the key code name.
     * 
     * @return the key code or {@code null} if the key code name does not have a
     *         key code.
     * 
     * @see KeyEvent
     * @see KeyEvent#getExtendedKeyCodeForChar(int)
     */
    public Integer getKeyCode(String keyname) {
        if (keyname.startsWith("VK_")) {
            Map<String, Integer> codes = getKeyCodes();
            return codes.get(keyname);
        }
        if (keyname.endsWith("_MASK")) {
            Map<String, Integer> codes = getKeyCodes();
            return codes.get(keyname);
        }
        if (keyname.length() == 1) {
            return KeyEvent.getExtendedKeyCodeForChar(keyname.charAt(0));
        } else {
            return null;
        }
    }

    private Map<String, Integer> getKeyCodes() {
        if (isGarbageCollected(keyCodes)) {
            keyCodes = new SoftReference<Map<String, Integer>>(findKeys());
        }
        return keyCodes.get();
    }

    private boolean isGarbageCollected(Reference<?> ref) {
        return ref == null || ref.get() == null;
    }

    private Map<String, Integer> findKeys() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (Field field : KeyEvent.class.getFields()) {
            if (isStatic(field.getModifiers())) {
                if (field.getName().startsWith("VK_")
                        || field.getName().endsWith("_MASK")) {
                    try {
                        map.put(field.getName(), field.getInt(null));
                    } catch (IllegalArgumentException e) {
                    } catch (IllegalAccessException e) {
                    }
                }
            }
        }
        return map;
    }

}
