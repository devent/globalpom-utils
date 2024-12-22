/*
 * Copyright 2014-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.exec.internal.runcommands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.inject.Inject;

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
     * @see RunCommandsFactory#create(Object, String)
     */
    @Inject
    RunCommands(@Assisted Object parent, @Assisted String name) {
        this.parent = parent;
        this.name = name;
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
