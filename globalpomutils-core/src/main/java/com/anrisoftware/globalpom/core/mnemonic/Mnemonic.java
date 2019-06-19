/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
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

/*-
 * #%L
 * Global POM Utilities :: Core
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static org.apache.commons.lang3.StringUtils.split;

import java.awt.event.KeyEvent;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;

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

    /**
     * @see MnemonicFactory#create(String)
     */
    public static Mnemonic valueOf(String string) {
        return MnemonicModule.getInjector().getInstance(MnemonicFactory.class)
                .create(string);
    }

    private final KeyCodeMap codeMap;

    private final String[] keynames;

    private final MnemonicLogger log;

    /**
     * @see MnemonicFactory#create(String)
     */
    @Inject
    Mnemonic(MnemonicLogger logger, KeyCodeMap codeMap, @Assisted String string) {
        this.log = logger;
        this.codeMap = codeMap;
        this.keynames = split(string, ",");
    }

    /**
     * Tests if the mnemonic will probably return a valid key code. Not tested
     * is whether or not the mnemonic will actually return a valid key code.
     * 
     * @return {@code true} if the mnemonic will probably return a valid key
     *         code.
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
     * Returns the mnemonic key code. The string can contain a key code name or
     * the character. Examples:
     * <ul>
     * <li>{@code VK_A}</li>
     * <li>{@code a}</li>
     * </ul>
     * 
     * @return the mnemonic key code or {@code null}.
     * 
     * @throws IllegalArgumentException
     *             if the specified mnemonic is not a valid key code.
     * 
     * @see KeyEvent
     */
    public Integer getMnemonic() {
        if (keynames.length == 0) {
            return null;
        }
        Integer code = codeMap.getKeyCode(keynames[0]);
        log.checkKeyCode(code, keynames);
        return code;
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
