package com.anrisoftware.globalpom.mnemonic;

import java.util.Arrays;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link Mnemonic}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
class MnemonicLogger extends AbstractLogger {

	private static final String ILLEGAL_ACC_MESSAGE = "Illegal mnemonic '{}'";
	private static final String ILLEGAL_ACC = "Illegal mnemonic '%s'";

	/**
	 * Creates a logger for {@link Mnemonic}.
	 */
	public MnemonicLogger() {
		super(Mnemonic.class);
	}

	void checkKeyCode(Integer keycode, String[] keynames) {
		if (keycode == null) {
			throw invalidMnemonic(keynames);
		}
	}

	IllegalArgumentException invalidMnemonic(String[] keynames) {
		return logException(
				new IllegalArgumentException(String.format(ILLEGAL_ACC,
						Arrays.toString(keynames))), ILLEGAL_ACC_MESSAGE,
				Arrays.toString(keynames));
	}

}
