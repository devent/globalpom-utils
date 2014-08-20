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
