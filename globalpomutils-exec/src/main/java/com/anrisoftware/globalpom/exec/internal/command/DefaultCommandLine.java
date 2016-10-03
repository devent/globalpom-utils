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
package com.anrisoftware.globalpom.exec.internal.command;

import static com.anrisoftware.globalpom.exec.internal.command.DefaultCommandLineModule.getCommandLineFactory;
import static java.lang.String.format;
import static java.util.Collections.unmodifiableList;
import static org.apache.commons.lang3.StringUtils.contains;
import static org.apache.commons.lang3.StringUtils.containsWhitespace;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import com.anrisoftware.globalpom.exec.external.command.CommandLineFactory;
import com.anrisoftware.globalpom.exec.external.core.CommandLine;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Quotes command line arguments.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public class DefaultCommandLine implements CommandLine {

    /**
     * @see CommandLineFactory#create(File)
     */
    public static CommandLine createCommandLine(File executable) {
        return getCommandLineFactory().create(executable);
    }

    /**
     * @see CommandLineFactory#create(String)
     */
    public static CommandLine createCommandLine(String executable) {
        return getCommandLineFactory().create(executable);
    }

    private static final String QUOTATION_FORMAT = "%s%s%s";

    private static final char SINGLE_QUOTE_CHAR = '\'';

    private static final char DOUBLE_QUOTE_CHAR = '"';

    private static final String QUOTE = "quote";

    private final List<Argument> arguments;

    private final String executable;

    private final Map<String, Object> substitutions;

    @Inject
    private DefaultCommandLineLogger log;

    private STGroup stgroup;

    private File workingDir;

    /**
     * @see CommandLineFactory#create(File)
     */
    @AssistedInject
    DefaultCommandLine(@Assisted File executable) {
        this(executable.getAbsolutePath());
    }

    /**
     * @see CommandLineFactory#create(String)
     */
    @AssistedInject
    DefaultCommandLine(@Assisted String executable) {
        this.executable = executable;
        this.arguments = new ArrayList<Argument>();
        this.substitutions = new HashMap<String, Object>();
    }

    @Override
    public List<String> getCommand() {
        List<String> command = new ArrayList<String>();
        command.add(getExecutable());
        for (String a : getArguments()) {
            command.add(a);
        }
        return command;
    }

    @Override
    public String getExecutable() {
        return executable;
    }

    @Override
    public List<String> getArguments() {
        return unmodifiableList(quoteArguments(substitudeVariables(arguments)));
    }

    @Override
    public CommandLine add(Object... arguments) {
        return add(true, arguments);
    }

    @Override
    public CommandLine add(boolean quote, Object... arguments) {
        for (Object argument : arguments) {
            add(quote, argument);
        }
        return this;
    }

    @Override
    public CommandLine add(Object argument) {
        return add(true, argument);
    }

    @Override
    public CommandLine add(boolean quote, Object argument) {
        log.checkArgument(this, argument);
        arguments.add(new Argument(argument, quote));
        return this;
    }

    @Override
    public CommandLine addSub(String name, Object value) {
        this.substitutions.put(name, value);
        createStGroup();
        return this;
    }

    @Override
    public void setWorkingDir(File dir) {
        this.workingDir = dir;
    }

    @Override
    public File getWorkingDir() {
        return workingDir;
    }

    @Override
    public void setVariableStartChar(char character) {
        createStGroup();
        stgroup.delimiterStartChar = character;
    }

    @Override
    public void setVariableStopChar(char character) {
        createStGroup();
        stgroup.delimiterStopChar = character;
    }

    private static class Argument {

        private final String argument;

        private final boolean quote;

        public Argument(Object argument, boolean quote) {
            this.argument = String.valueOf(argument);
            this.quote = quote;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append(argument)
                    .append(QUOTE, quote).toString();
        }
    }

    private void createStGroup() {
        if (stgroup == null) {
            this.stgroup = new STGroup('<', '>');
        }
    }

    private List<Argument> substitudeVariables(List<Argument> arguments) {
        if (stgroup == null) {
            return arguments;
        }
        List<Argument> args = new ArrayList<Argument>(arguments.size());
        for (Argument a : arguments) {
            ST st = new ST(stgroup, a.argument);
            for (Map.Entry<String, Object> entry : substitutions.entrySet()) {
                st.add(entry.getKey(), entry.getValue());
            }
            args.add(new Argument(st.render(), a.quote));
        }
        return args;
    }

    private List<String> quoteArguments(List<Argument> arguments) {
        List<String> args = new ArrayList<String>();
        for (Argument a : arguments) {
            args.add(a.quote ? quoteArgument(a.argument) : a.argument);
        }
        return args;
    }

    private String quoteArgument(String argument) {
        argument = argument.trim();
        if (containsWhitespace(argument)) {
            char q = contains(argument, DOUBLE_QUOTE_CHAR) ? SINGLE_QUOTE_CHAR
                    : DOUBLE_QUOTE_CHAR;
            argument = format(QUOTATION_FORMAT, q, argument, q);
        }
        return argument;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(executable).append(arguments)
                .toString();
    }

}
