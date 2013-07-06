package com.anrisoftware.globalpom.mnemonic;

import java.util.Arrays;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link Accelerator}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
class AcceleratorLogger extends AbstractLogger {

	private static final String ILLEGAL_ACC_MESSAGE = "Illegal accelerator '{}'";
	private static final String ILLEGAL_ACC = "Illegal accelerator '%s'";

	/**
	 * Creates a logger for {@link Accelerator}.
	 */
	public AcceleratorLogger() {
		super(Accelerator.class);
	}

	void checkKeyCode(Integer keycode, String[] keynames) {
		if (keycode == null) {
			throw invalidAccelerator(keynames);
		}
	}

	IllegalArgumentException invalidAccelerator(String[] keynames) {
		return logException(
				new IllegalArgumentException(String.format(ILLEGAL_ACC,
						Arrays.toString(keynames))), ILLEGAL_ACC_MESSAGE,
				Arrays.toString(keynames));
	}

}
