/*
 * Copyright 2013-2023 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import jakarta.inject.Singleton;
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
