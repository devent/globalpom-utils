/*
 * Copyright 2014 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.exec.script;

import com.anrisoftware.globalpom.exec.api.CommandLine;
import com.anrisoftware.resources.templates.api.TemplateResource;

/**
 * Factory to create a command line.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
public interface ScriptCommandLineFactory {

    /**
     * Create a command line from a template.
     * 
     * @param name
     *            the {@link String} name.
     * 
     * @param template
     *            the template {@link TemplateResource} resource.
     * 
     * @return the {@link ScriptCommandLine}.
     */
    CommandLine create(String name, TemplateResource template);
}
