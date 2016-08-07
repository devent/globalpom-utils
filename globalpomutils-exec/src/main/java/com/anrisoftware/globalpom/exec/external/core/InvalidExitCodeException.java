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
package com.anrisoftware.globalpom.exec.external.core;

import java.util.Arrays;

/**
 * Thrown if the command returned an invalid exit code.
 *
 * @author Erwin Müller <erwin.mueller@deventm.de>
 * @version 1.0
 */
@SuppressWarnings("serial")
public class InvalidExitCodeException extends CommandExecException {

    public InvalidExitCodeException(ProcessTask processTask, int ret,
            int[] exitCodes, CommandLine commandLine) {
        super("Error exit code");
        addContextValue("process-task", processTask);
        addContextValue("command-line", commandLine);
        addContextValue("return-code", ret);
        addContextValue("exit-codes", Arrays.toString(exitCodes));
    }

}
