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
package com.anrisoftware.globalpom.exec.internal.runcommands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.exec.external.runcommands.RunCommandsFactory;
import com.google.inject.assistedinject.Assisted;

/**
 * Saves the run commands.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
public class RunCommands {

    private final Object parent;

    private final List<Object> commands;

    private final String name;

    @Inject
    private RunCommandsLogger log;

    /**
     * @see RunCommandsFactory#create(String, Object)
     */
    @Inject
    RunCommands(@Assisted String name, @Assisted Object parent) {
        this.name = name;
        this.parent = parent;
        this.commands = new ArrayList<Object>();
    }

    public void add(Object command, Object args) {
        commands.add(command);
        log.commandAdded(command, args, name, parent);
    }

    public List<Object> getCommands() {
        return Collections.unmodifiableList(commands);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).toString();
    }
}
