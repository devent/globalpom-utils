package com.anrisoftware.globalpom.exec.command;

import static java.lang.String.format;
import static java.util.Collections.unmodifiableList;
import static org.apache.commons.lang3.StringUtils.contains;
import static org.apache.commons.lang3.StringUtils.containsWhitespace;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Quotes command line arguments.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class CommandLine {

    private static final String QUOTATION_FORMAT = "%s%s%s";

    private static final char SINGLE_QUOTE_CHAR = '\'';

    private static final char DOUBLE_QUOTE_CHAR = '"';

    private static final String QUOTE = "quote";

    private final List<Argument> arguments;

    private final String executable;

    private final Map<String, Object> substitutions;

    private STGroup stgroup;

    private File workingDir;

    /**
     * @see CommandLineFactory#create(File)
     */
    @AssistedInject
    CommandLine(@Assisted File executable) {
        this(executable.getAbsolutePath());
    }

    /**
     * @see CommandLineFactory#create(String)
     */
    @AssistedInject
    CommandLine(@Assisted String executable) {
        this.executable = executable;
        this.arguments = new ArrayList<Argument>();
        this.substitutions = new HashMap<String, Object>();
    }

    /**
     * Returns the of the command list.
     * 
     * @return the unmodifiable {@link List} of {@link String} that contains the
     *         executable as the first item and the command arguments as the
     *         rest.
     */
    public List<String> getCommand() {
        List<String> command = new ArrayList<String>();
        command.add(getExecutable());
        for (String a : getArguments()) {
            command.add(a);
        }
        return command;
    }

    /**
     * Returns the executable.
     * 
     * @return the {@link String} executable to run.
     */
    public String getExecutable() {
        return executable;
    }

    /**
     * Returns the arguments.
     * 
     * @return the unmodifiable {@link List} of arguments.
     */
    public List<String> getArguments() {
        return unmodifiableList(quoteArguments(substitudeVariables(arguments)));
    }

    /**
     * Adds the specified command line arguments.
     * 
     * @param arguments
     *            the {@link String} array arguments.
     * 
     * @return this {@link CommandLine}.
     */
    public CommandLine add(String... arguments) {
        return add(true, arguments);
    }

    /**
     * Adds the specified command line arguments.
     * 
     * @param quote
     *            set to {@code true} to quote the argument.
     * 
     * @param arguments
     *            the {@link String} array arguments.
     * 
     * @return this {@link CommandLine}.
     */
    public CommandLine add(boolean quote, String... arguments) {
        for (String argument : arguments) {
            add(quote, argument);
        }
        return this;
    }

    /**
     * Adds the specified command line argument.
     * 
     * @param arguments
     *            the {@link String} argument.
     * 
     * @return this {@link CommandLine}.
     */
    public CommandLine add(String argument) {
        return add(true, argument);
    }

    /**
     * Adds the specified command line argument.
     * <p>
     * If the quote arguments is set then the arguments is quoted in
     * double/single quotes.
     * <ul>
     * <li>{@code aaa} -> {@code aaa}</li>
     * <li>{@code aaa bbb} -> {@code "aaa bbb"}</li>
     * <li>{@code "aaa" bbb} -> {@code '"aaa" bbb'}</li>
     * <li>{@code "aaa ccc" bbb} -> {@code '"aaa ccc" bbb'}</li>
     * <li>{@code "aaa"bbb} -> {@code '"aaa"bbb'}</li>
     * </ul>
     * </p>
     * 
     * @param argument
     *            the {@link String} argument.
     * 
     * @param quote
     *            set to {@code true} to quote the argument.
     * 
     * @return this {@link CommandLine}.
     */
    public CommandLine add(boolean quote, String argument) {
        arguments.add(new Argument(argument, quote));
        return this;
    }

    /**
     * Adds a variable substitution for the arguments. The variables are only
     * substituted when the arguments are returned. The variables must be
     * enclosed in the enclose {@code "<", ">"}, for example:
     * {@code "foo=<bar>".}
     * 
     * @param name
     *            the {@link String} name of the variable.
     * 
     * @param value
     *            the {@link Object} value of the variable.
     * 
     * @return this {@link CommandLine}.
     */
    public CommandLine sub(String name, Object value) {
        this.substitutions.put(name, value);
        createStGroup();
        return this;
    }

    /**
     * Sets the working directory.
     * 
     * @param dir
     *            the {@link File} directory or {@code null} to use the working
     *            directory of the current Java process.
     */
    public void setWorkingDir(File dir) {
        this.workingDir = dir;
    }

    /**
     * Returns the working directory.
     * 
     * @return the {@link File} directory or {@code null} to use the working
     *         directory of the current Java process.
     */
    public File getWorkingDir() {
        return workingDir;
    }

    /**
     * Sets the variable substitution start character. The start character
     * defaults to <code>"&lt;".</code>
     * 
     * @param character
     *            the character.
     */
    public void setVariableStartChar(char character) {
        createStGroup();
        stgroup.delimiterStartChar = character;
    }

    /**
     * Sets the variable substitution stop character. The stop character
     * defaults to <code>"&gt;".</code>
     * 
     * @param character
     *            the character.
     */
    public void setVariableStopChar(char character) {
        createStGroup();
        stgroup.delimiterStopChar = character;
    }

    private static class Argument {

        private final String argument;

        private final boolean quote;

        public Argument(String argument, boolean quote) {
            this.argument = argument;
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