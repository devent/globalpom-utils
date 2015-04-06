/*
 * Copyright 2014-2015 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.exec.logoutputs;

import org.slf4j.Logger;

import com.anrisoftware.globalpom.exec.api.CommandLine;
import com.anrisoftware.globalpom.exec.logoutputs.AbstractLogCommandOutput.TraceLogCommandOutput;

/**
 * Factory to create the logger output that logs the output as trace level.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
public interface TraceLogCommandOutputFactory {

    /**
     * Creates the logger output that logs the output as trace level.
     * 
     * @param logger
     *            the {@link Logger}.
     * 
     * @param commandLine
     *            the the {@link CommandLine} for the logging message.
     * 
     * @return the {@link TraceLogCommandOutput}.
     */
    TraceLogCommandOutput create(Logger logger, CommandLine commandLine);
}
