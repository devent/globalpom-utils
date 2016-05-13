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
package com.anrisoftware.globalpom.exec.external.scriptprocess;

import java.util.concurrent.Callable;

import com.anrisoftware.globalpom.exec.external.core.ProcessTask;

/**
 * Executes the script from a template.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
public interface ScriptExec extends Callable<ProcessTask> {

    /**
     * Returns the script that was executed.
     *
     * @return the script {@link String}.
     */
    String getScriptString();

}
