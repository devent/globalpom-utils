package com.anrisoftware.globalpom.mnemonic;

import static org.apache.commons.lang3.StringUtils.split;

import java.awt.event.KeyEvent;
import java.util.MissingResourceException;

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

	private final KeyCodeMap codeMap;

	private final String[] keynames;

	/**
	 * @see AcceleratorFactory#create(String)
	 */
	@Inject
	Accelerator(KeyCodeMap codeMap, @Assisted String string) {
		this.codeMap = codeMap;
		this.keynames = split(string, ",");
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
	 * @see KeyEvent
	 * @see KeyStroke
	 */
	public KeyStroke getAccelerator() {
		try {
			int keycode = codeMap.getKeyCode(keynames[0]);
			int modifiers = 0;
			for (int i = 1; i < keynames.length; i++) {
				modifiers |= codeMap.getKeyCode(keynames[i]);
			}
			return KeyStroke.getKeyStroke(keycode, modifiers);
		} catch (MissingResourceException e) {
			return null;
		}
	}
}
