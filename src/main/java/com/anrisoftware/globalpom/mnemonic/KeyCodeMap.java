/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
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
