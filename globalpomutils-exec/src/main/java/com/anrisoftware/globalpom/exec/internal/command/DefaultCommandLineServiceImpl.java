/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.exec.internal.command;

import static com.google.inject.Guice.createInjector;

import java.io.File;

import javax.inject.Inject;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.anrisoftware.globalpom.exec.external.command.CommandLineFactory;
import com.anrisoftware.globalpom.exec.external.command.CommandLineService;
import com.anrisoftware.globalpom.exec.external.core.CommandLine;

/**
 * Command line service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component
@Service(CommandLineService.class)
public class DefaultCommandLineServiceImpl implements CommandLineService {

    @Inject
    private CommandLineFactory factory;

    @Override
    public CommandLine create(String executable) {
        return factory.create(executable);
    }

    @Override
    public CommandLine create(File executable) {
        return factory.create(executable);
    }

    @Activate
    protected void start() {
        createInjector(new DefaultCommandLineModule()).injectMembers(this);
    }

}
