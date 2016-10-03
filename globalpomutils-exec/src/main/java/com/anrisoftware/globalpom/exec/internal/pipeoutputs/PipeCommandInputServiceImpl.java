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
package com.anrisoftware.globalpom.exec.internal.pipeoutputs;

import static com.google.inject.Guice.createInjector;

import java.io.InputStream;

import javax.inject.Inject;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.anrisoftware.globalpom.exec.external.core.CommandInput;
import com.anrisoftware.globalpom.exec.external.pipeoutputs.PipeCommandInputFactory;
import com.anrisoftware.globalpom.exec.external.pipeoutputs.PipeCommandInputService;

/**
 * Pipe input service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component
@Service(PipeCommandInputService.class)
public class PipeCommandInputServiceImpl implements PipeCommandInputService {

    @Inject
    private PipeCommandInputFactory factory;

    @Override
    public CommandInput create(InputStream stream) {
        return factory.create(stream);
    }

    @Override
    public CommandInput fromString(String string) {
        return factory.fromString(string);
    }

    @Activate
    protected void start() {
        createInjector(new PipeOutputsModule()).injectMembers(this);
    }

}
