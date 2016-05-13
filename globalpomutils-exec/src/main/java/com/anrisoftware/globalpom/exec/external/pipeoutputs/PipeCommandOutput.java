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
package com.anrisoftware.globalpom.exec.external.pipeoutputs;

import java.io.OutputStream;

import com.anrisoftware.globalpom.exec.external.core.CommandOutput;

/**
 * Pipes the read data from the output of the command to a stream.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
public interface PipeCommandOutput extends CommandOutput {

    /**
     * Sets the stream that is filled with the piped to the output of the
     * command.
     *
     * @param stream
     *            the {@link OutputStream}.
     */
    void setPipe(OutputStream stream);

    /**
     * Sets the buffer size.
     *
     * @param size
     *            the size in bytes.
     */
    void setBufferSize(int size);

}
