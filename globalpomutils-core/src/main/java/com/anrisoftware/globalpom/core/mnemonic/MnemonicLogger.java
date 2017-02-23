/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
