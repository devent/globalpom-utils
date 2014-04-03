/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.exec.api;

import java.io.InputStream;
import java.util.concurrent.Callable;

/**
 * Reads the standard output of the executed command.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public interface CommandOutput extends Callable<CommandOutput> {

    /**
     * Sets the stream that is connected to output of the executed command.
     * 
     * @param stream
     *            the {@link InputStream}.
     */
    void setInput(InputStream stream);

    /**
     * Clones the standard output reader. The cloned reader have the same
     * connected input stream.
     * 
     * @return the cloned {@link CommandOutput}.
     */
    CommandOutput clone();

    /**
     * Reads the standard output from the connected stream.
     */
    @Override
    CommandOutput call() throws Exception;
}
