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

import static org.apache.commons.lang3.StringUtils.split;

import java.awt.event.KeyEvent;

import javax.inject.Inject;
import javax.swing.KeyStroke;

import com.google.inject.assistedinject.Assisted;

/**
 * Action accelerator key.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public class Accelerator {

	/**
	 * @see AcceleratorFactory#create(String)
	 */
	public static Accelerator valueOf(String string) {
		return MnemonicModule.getInjector()
				.getInstance(AcceleratorFactory.class).create(string);
	}

	private final AcceleratorLogger log;

	private final KeyCodeMap codeMap;

	private final String[] keynames;

	/**
	 * @see AcceleratorFactory#create(String)
	 */
	@Inject
	Accelerator(AcceleratorLogger logger, KeyCodeMap codeMap,
			@Assisted String string) {
		this.log = logger;
		this.codeMap = codeMap;
		this.keynames = split(string, ",");
	}

	/**
	 * Tests if the accelerator will probably return a valid key stroke. Not
	 * tested is whether or not the accelerator will actually return a valid key
	 * stroke.
	 * 
	 * @return {@code true} if the accelerator will probably return a valid key
	 *         stroke.
	 * 
	 * @since 1.6
	 */
	public boolean isValid() {
		if (keynames.length == 0) {
			return false;
		}
		return codeMap.valid(keynames[0]);
	}

	/**
	 * Returns the accelerator key for an action. The accelerator can be just a
	 * key character or the key code name. In addition, the modifiers can be set
	 * as the modifiers names. Examples:
	 * 
	 * <ul>
	 * <li>{@code VK_A,ALT_DOWN_MASK,CTRL_DOWN_MASK}</li>
	 * <li>{@code a,ALT_DOWN_MASK,CTRL_DOWN_MASK}</li>
	 * </ul>
	 * 
	 * @return accelerator {@link KeyStroke} or {@code null} if no accelerator
	 *         key was specified.
	 * 
	 * @throws IllegalArgumentException
	 *             if the specified accelerator or the modifier are not a valid
	 *             key code.
	 * 
	 * @see KeyEvent
	 * @see KeyStroke
	 */
	public KeyStroke getAccelerator() {
		if (keynames.length == 0) {
			return null;
		}
		Integer keycode = codeMap.getKeyCode(keynames[0]);
		log.checkKeyCode(keycode, keynames);
		int modifiers = 0;
		for (int i = 1; i < keynames.length; i++) {
			Integer mod = codeMap.getKeyCode(keynames[i]);
			log.checkKeyCode(mod, keynames);
			modifiers |= mod;
		}
		return KeyStroke.getKeyStroke(keycode, modifiers);
	}
}
