package com.anrisoftware.globalpom.mnemonic;

import static com.google.inject.Guice.createInjector;
import static org.apache.commons.lang3.StringUtils.split;

import java.awt.event.KeyEvent;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.inject.Injector;
import com.google.inject.assistedinject.Assisted;

/**
 * Swing mnemonic character with mnemonic index.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public class Mnemonic {

	/**
	 * Indicates no mnemonic index.
	 */
	public static final int NO_INDEX = -1;

	private static final Injector INJECTOR = createInjector();

	/**
	 * @see MnemonicFactory#create(String)
	 */
	public static Mnemonic valueOf(String string) {
		return INJECTOR.getInstance(MnemonicFactory.class).create(string);
	}

	private final KeyCodeMap codeMap;

	private final String[] keynames;

	/**
	 * @see MnemonicFactory#create(String)
	 */
	@Inject
	Mnemonic(KeyCodeMap codeMap, @Assisted String string) {
		this.codeMap = codeMap;
		this.keynames = split(string, ",");
	}

	/**
	 * Returns the mnemonic key code. The string can contain a key code name or
	 * the character. Examples:
	 * <ul>
	 * <li>{@code VK_A}</li>
	 * <li>{@code a}</li>
	 * </ul>
	 * 
	 * @return the mnemonic key code.
	 * 
	 * @see KeyEvent
	 */
	public int getMnemonic() {
		return codeMap.getKeyCode(keynames[0]);
	}

	/**
	 * Returns the displayed mnemonic index if such index was set in the
	 * resource. To set the index, the mnemonic character and the index must be
	 * specified and separated with a comma. Example:
	 * 
	 * <ul>
	 * <li>{@code VK_A,5}</li>
	 * <li>{@code a,5}</li>
	 * </ul>
	 * 
	 * @return displayed mnemonic index or -1. The value -1 means no index was
	 *         specified.
	 * 
	 * @see #NO_INDEX
	 */
	public int getMnemonicIndex() {
		if (keynames.length == 2) {
			return Integer.valueOf(keynames[1]);
		} else {
			return NO_INDEX;
		}
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(keynames).toString();
	}
}
