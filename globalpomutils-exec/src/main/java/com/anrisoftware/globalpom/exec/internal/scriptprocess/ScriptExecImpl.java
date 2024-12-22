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
package com.anrisoftware.globalpom.exec.internal.scriptprocess;

import java.util.Map;

import jakarta.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.exec.external.core.CommandExecException;
import com.anrisoftware.globalpom.exec.external.core.CommandLine;
import com.anrisoftware.globalpom.exec.external.core.ProcessTask;
import com.anrisoftware.globalpom.exec.external.script.ScriptCommandLineFactory;
import com.anrisoftware.globalpom.exec.external.scriptprocess.ScriptExecFactory;
import com.anrisoftware.globalpom.exec.internal.runcommands.RunCommands;
import com.anrisoftware.globalpom.threads.external.core.Threads;
import com.anrisoftware.resources.templates.external.TemplateResource;
import com.google.inject.assistedinject.Assisted;

/**
 * Executes the script from a template.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
public class ScriptExecImpl extends AbstractProcessExec {

    private static final String ARGS_SUB = "args";

    private final Map<String, Object> args;

    private final Object parent;

    private final String name;

    private final TemplateResource templateResource;

    private final RunCommands runCommands;

    private final ScriptExecLogger log;

    /**
     * @see ScriptExecFactory#create(Object, Threads, TemplateResource, String, Map)
     */
    @Inject
    ScriptExecImpl(ScriptExecLogger log, @Assisted Object parent, @Assisted Threads threads,
            @Assisted TemplateResource templateResource, @Assisted String name, @Assisted Map<String, Object> args) {
        super(threads, args);
        this.log = log;
        this.args = args;
        this.parent = parent;
        this.name = name;
        this.templateResource = templateResource;
        this.runCommands = log.runCommands(args, parent);
    }

    @Override
    public ProcessTask call() throws CommandExecException {
        ProcessTask task = super.call();
        log.scriptDone(parent, runCommands, task, args, name);
        return task;
    }

    @Override
    protected CommandLine createCommandLine(ScriptCommandLineFactory commandLineFactory) {
        return commandLineFactory.create(name, templateResource).addSub(ARGS_SUB, args);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(args).toString();
    }
}
