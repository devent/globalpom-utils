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
package com.anrisoftware.globalpom.exec.external.runcommands;

import com.anrisoftware.globalpom.exec.internal.runcommands.RunCommands;

/**
 * Factory to create the run commands collection.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
public interface RunCommandsFactory {

    /**
     * Creates the run commands collection
     *
     * @param parent
     *            the parent {@link Object} script.
     *
     * @param name
     *            the parent script {@link String} name;
     *
     * @return the {@link RunCommands}.
     */
    RunCommands create(Object parent, String name);
}
