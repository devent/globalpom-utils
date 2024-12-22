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
package com.anrisoftware.globalpom.exec.external.core;

import java.io.File;
import java.util.List;

import com.anrisoftware.globalpom.exec.internal.command.DefaultCommandLine;

/**
 * Quotes command line arguments.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
public interface CommandLine {

    /**
     * Returns the of the command list.
     *
     * @return the unmodifiable {@link List} of {@link String} that contains the
     *         executable as the first item and the command arguments as the rest.
     *
     * @throws CommandExecException if there was an error create the command.
     */
    List<String> getCommand() throws CommandExecException;

    /**
     * Returns the executable.
     *
     * @return the {@link Object} executable to run, can be the name of the
     *         executable or the path or any other abstract object.
     */
    Object getExecutable();

    /**
     * Returns the arguments.
     *
     * @return the unmodifiable {@link List} of arguments.
     */
    List<String> getArguments();

    /**
     * Adds the specified command line arguments.
     *
     * @param arguments the {@link Object} array arguments.
     *
     * @return this {@link DefaultCommandLine}.
     */
    CommandLine add(Object... arguments);

    /**
     * Adds the specified command line arguments.
     *
     * @param quote     set to {@code true} to quote the argument.
     *
     * @param arguments the {@link Object} array arguments.
     *
     * @return this {@link DefaultCommandLine}.
     */
    CommandLine add(boolean quote, Object... arguments);

    /**
     * Adds the specified command line argument.
     *
     * @param argument the {@link Object} argument.
     *
     * @return this {@link DefaultCommandLine}.
     */
    CommandLine add(Object argument);

    /**
     * Adds the specified command line argument.
     * If the quote arguments is set then the arguments is quoted in double/single
     * quotes.
     * <ul>
     * <li>{@code aaa} -&gt; {@code aaa}</li>
     * <li>{@code aaa bbb} -&gt; {@code "aaa bbb"}</li>
     * <li>{@code "aaa" bbb} -&gt; {@code '"aaa" bbb'}</li>
     * <li>{@code "aaa ccc" bbb} -&gt; {@code '"aaa ccc" bbb'}</li>
     * <li>{@code "aaa"bbb} -&gt; {@code '"aaa"bbb'}</li>
     * </ul>
     *
     * @param argument the {@link Object} argument.
     *
     * @param quote    set to {@code true} to quote the argument.
     *
     * @return this {@link DefaultCommandLine}.
     */
    CommandLine add(boolean quote, Object argument);

    /**
     * Adds a variable substitution for the arguments. The variables are only
     * substituted when the arguments are returned. The variables must be enclosed
     * in the enclose {@code "<", ">"}, for example: {@code "foo=<bar>".}
     *
     * @param name  the {@link String} name of the variable.
     *
     * @param value the {@link Object} value of the variable.
     *
     * @return this {@link DefaultCommandLine}.
     */
    CommandLine addSub(String name, Object value);

    /**
     * Sets the working directory.
     *
     * @param dir the {@link File} directory or {@code null} to use the working
     *            directory of the current Java process.
     */
    void setWorkingDir(File dir);

    /**
     * Returns the working directory.
     *
     * @return the {@link File} directory or {@code null} to use the working
     *         directory of the current Java process.
     */
    File getWorkingDir();

    /**
     * Sets the variable substitution start character. The start character defaults
     * to <code>"&lt;".</code>
     *
     * @param character the character.
     */
    void setVariableStartChar(char character);

    /**
     * Sets the variable substitution stop character. The stop character defaults to
     * <code>"&gt;".</code>
     *
     * @param character the character.
     */
    void setVariableStopChar(char character);

    @Override
    String toString();

}
