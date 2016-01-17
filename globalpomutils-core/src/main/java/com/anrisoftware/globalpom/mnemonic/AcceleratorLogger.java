/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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

import java.util.Arrays;

import javax.inject.Singleton;
import javax.swing.KeyStroke;

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

    void checkKeyStroke(String string, KeyStroke stroke) {
        if (stroke == null) {
            throw invalidAccelerator(string);
        }
    }

    IllegalArgumentException invalidAccelerator(String string) {
        return logException(
                new IllegalArgumentException(String.format(ILLEGAL_ACC, string)),
                ILLEGAL_ACC_MESSAGE, string);
    }

}
