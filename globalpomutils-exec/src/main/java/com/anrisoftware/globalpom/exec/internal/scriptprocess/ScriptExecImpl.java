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
package com.anrisoftware.globalpom.exec.internal.scriptprocess;

import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.exec.external.core.CommandLine;
import com.anrisoftware.globalpom.exec.external.core.ProcessTask;
import com.anrisoftware.globalpom.exec.external.script.ScriptCommandLineFactory;
import com.anrisoftware.globalpom.exec.external.scriptprocess.ScriptExecFactory;
import com.anrisoftware.globalpom.exec.internal.runcommands.RunCommands;
import com.anrisoftware.globalpom.threads.api.Threads;
import com.anrisoftware.resources.templates.external.TemplateResource;
import com.google.inject.assistedinject.Assisted;

/**
 * Executes the script from a template.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
public class ScriptExecImpl extends AbstractProcessExec {

    private static final String ARGS = "args";

    private final Map<String, Object> args;

    private final Object parent;

    private final String name;

    private final TemplateResource templateResource;

    private final RunCommands runCommands;

    private final ScriptExecLogger log;

    /**
     * @see ScriptExecFactory#create(Object, Threads, TemplateResource, String,
     *      Map)
     */
    @Inject
    ScriptExecImpl(ScriptExecLogger log, @Assisted Object parent,
            @Assisted Threads threads,
            @Assisted TemplateResource templateResource, @Assisted String name,
            @Assisted Map<String, Object> args) {
        super(threads, args);
        this.log = log;
        this.args = args;
        this.parent = parent;
        this.name = name;
        this.templateResource = templateResource;
        this.runCommands = log.runCommands(args, parent);
    }

    @Override
    public ProcessTask call() throws Exception {
        ProcessTask task = super.call();
        log.scriptDone(parent, runCommands, task, args, name);
        return task;
    }

    @Override
    protected CommandLine createCommandLine(
            ScriptCommandLineFactory commandLineFactory) {
        return commandLineFactory.create(name, templateResource).addSub(ARGS,
                args);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(args).toString();
    }
}