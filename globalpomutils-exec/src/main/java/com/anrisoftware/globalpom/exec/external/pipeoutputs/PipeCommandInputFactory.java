/*
 * Copyright 2014-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-exec.
 *
 * globalpomutils-exec is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-exec is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-exec. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.exec.external.pipeoutputs;

import java.io.InputStream;

import com.anrisoftware.globalpom.exec.external.core.CommandInput;

/**
 * Factory to create the pipe input.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public interface PipeCommandInputFactory {

    /**
     * Creates the pipe input that reads the specified stream to the standard
     * input of the command.
     *
     * @param stream
     *            the {@link InputStream}.
     *
     * @return the {@link CommandInput}.
     */
    CommandInput create(InputStream stream);

    /**
     * Pipes the string content to the standard input of the command.
     *
     * @param string
     *            the {@link String}.
     *
     * @return the {@link CommandInput}.
     */
    CommandInput fromString(String string);
}
