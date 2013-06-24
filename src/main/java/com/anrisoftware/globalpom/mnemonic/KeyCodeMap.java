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
	 * @return the key code.
	 * 
	 * @see KeyEvent
	 * @see KeyEvent#getExtendedKeyCodeForChar(int)
	 */
	public int getKeyCode(String keyname) {
		if (keyname.startsWith("VK_")) {
			Map<String, Integer> codes = getKeyCodes();
			return codes.get(keyname);
		}
		if (keyname.endsWith("_MASK")) {
			Map<String, Integer> codes = getKeyCodes();
			return codes.get(keyname);
		}
		return KeyEvent.getExtendedKeyCodeForChar(keyname.charAt(0));
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
