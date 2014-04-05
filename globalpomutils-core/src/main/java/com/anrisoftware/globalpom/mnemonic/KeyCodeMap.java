/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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
		if (keyCodes == null || keyCodes.get() == null) {
			keyCodes = new SoftReference<Map<String, Integer>>(findKeys());
		}
		return keyCodes.get();
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
