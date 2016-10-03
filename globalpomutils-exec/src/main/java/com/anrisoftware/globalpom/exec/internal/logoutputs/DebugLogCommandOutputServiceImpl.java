/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.exec.internal.logoutputs;

import static com.google.inject.Guice.createInjector;

import javax.inject.Inject;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;

import com.anrisoftware.globalpom.exec.external.core.CommandLine;
import com.anrisoftware.globalpom.exec.external.core.CommandOutput;
import com.anrisoftware.globalpom.exec.external.logoutputs.DebugLogCommandOutputFactory;
import com.anrisoftware.globalpom.exec.external.logoutputs.DebugLogCommandOutputService;

/**
 * Debug level logging service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component
@Service(DebugLogCommandOutputService.class)
public class DebugLogCommandOutputServiceImpl implements
        DebugLogCommandOutputService {

    @Inject
    private DebugLogCommandOutputFactory factory;

    @Override
    public CommandOutput create(Logger logger, CommandLine commandLine) {
        return factory.create(logger, commandLine);
    }

    @Activate
    protected void start() {
        createInjector(new LogOutputsModule()).injectMembers(this);
    }

}
