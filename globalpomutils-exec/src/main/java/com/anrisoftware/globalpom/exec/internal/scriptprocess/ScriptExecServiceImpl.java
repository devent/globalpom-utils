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

import static com.google.inject.Guice.createInjector;

import java.util.Map;

import javax.inject.Inject;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.anrisoftware.globalpom.exec.external.scriptprocess.ScriptExec;
import com.anrisoftware.globalpom.exec.external.scriptprocess.ScriptExecFactory;
import com.anrisoftware.globalpom.exec.external.scriptprocess.ScriptExecService;
import com.anrisoftware.globalpom.threads.api.Threads;
import com.anrisoftware.resources.templates.external.TemplateResource;

/**
 * Script exec service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component
@Service(ScriptExecService.class)
public class ScriptExecServiceImpl implements ScriptExecService {

    @Inject
    private ScriptExecFactory factory;

    @Override
    public ScriptExec create(Map<String, Object> args, Object parent,
            Threads threads, TemplateResource templateResource, String name) {
        return factory.create(args, parent, threads, templateResource, name);
    }

    @Activate
    protected void start() {
        createInjector(new ScriptProcessModule()).injectMembers(this);
    }

}
