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
package com.anrisoftware.globalpom.exec.command;

import static com.anrisoftware.globalpom.exec.command.DefaultCommandLineModule.getCommandLineFactory;
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

import com.anrisoftware.globalpom.exec.api.CommandLine;
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
     * @see DefaultCommandLineFactory#create(File)
     */
    public static CommandLine createCommandLine(File executable) {
        return getCommandLineFactory().create(executable);
    }

    /**
     * @see DefaultCommandLineFactory#create(String)
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
     * @see DefaultCommandLineFactory#create(File)
     */
    @AssistedInject
    DefaultCommandLine(@Assisted File executable) {
        this(executable.getAbsolutePath());
    }

    /**
     * @see DefaultCommandLineFactory#create(String)
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
