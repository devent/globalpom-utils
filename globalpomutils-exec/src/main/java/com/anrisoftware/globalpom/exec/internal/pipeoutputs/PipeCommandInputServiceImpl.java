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
