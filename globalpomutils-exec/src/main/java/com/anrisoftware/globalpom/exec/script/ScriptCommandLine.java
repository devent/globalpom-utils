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

import static com.anrisoftware.globalpom.exec.script.ScriptProcessModule.getScriptCommandLineFactory;
import static java.io.File.createTempFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.exec.api.CommandExecException;
import com.anrisoftware.globalpom.exec.api.CommandLine;
import com.anrisoftware.globalpom.exec.command.DefaultCommandLineFactory;
import com.anrisoftware.resources.api.ResourcesException;
import com.anrisoftware.resources.templates.api.TemplateResource;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Quotes command line arguments.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
public class ScriptCommandLine implements CommandLine {

    /**
     * @see ScriptCommandLineFactory#create(String, TemplateResource)
     */
    public static CommandLine createScriptCommandLine(String name,
            TemplateResource template) {
        return getScriptCommandLineFactory().create(name, template);
    }

    private static final String SCRIPT_POST = ".sh";

    private static final String STOP_CHARACTER_PROPERTY = "template_delimiter_stop_character";

    private static final String START_CHARACTER_PROPERTY = "template_delimiter_start_character";

    private static final String SCRIPT = "script";

    private static final String QUOTE = "quote";

    private static final String ARGUMENTS = "arguments";

    private static final String SUBSTITUTIONS = "substitutions";

    private static final String TEMPLATE = "template";

    private final TemplateResource template;

    private final Map<String, Object> substitutions;

    private final List<Argument> arguments;

    private final String name;

    @Inject
    private ScriptCommandLineLogger log;

    @Inject
    private DefaultCommandLineFactory commandLineFactory;

    private File workingDir;

    private File scriptFile;

    /**
     * @see ScriptCommandLineFactory#create(String, TemplateResource)
     */
    @AssistedInject
    ScriptCommandLine(@Assisted String name, @Assisted TemplateResource template) {
        this.template = template;
        this.substitutions = new HashMap<String, Object>();
        this.arguments = new ArrayList<ScriptCommandLine.Argument>();
        this.name = name;
    }

    @Override
    public List<String> getCommand() throws CommandExecException {
        String scriptString = createScript(template, name, substitutions);
        this.scriptFile = copyScript(scriptString);
        CommandLine commandLine = commandLineFactory.create(scriptFile);
        for (Argument argument : arguments) {
            commandLine.add(argument.quote, argument.argument);
        }
        return commandLine.getCommand();
    }

    @Override
    public File getExecutable() {
        return scriptFile;
    }

    @Override
    public List<String> getArguments() {
        return null;
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
        template.getProperties().setProperty(START_CHARACTER_PROPERTY,
                String.valueOf(character));
    }

    @Override
    public void setVariableStopChar(char character) {
        template.getProperties().setProperty(STOP_CHARACTER_PROPERTY,
                String.valueOf(character));
    }

    public TemplateResource getTemplate() {
        return template;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(TEMPLATE, template)
                .append(SUBSTITUTIONS, substitutions)
                .append(ARGUMENTS, arguments).toString();
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

    private String createScript(TemplateResource template, String name,
            Map<String, Object> substitutions) throws CommandExecException {
        try {
            Object[] attributes = createAttributes(name, substitutions);
            return template.getText(true, attributes);
        } catch (ResourcesException e) {
            throw log.errorProcessTemplate(this, e);
        }
    }

    private Object[] createAttributes(String name,
            Map<String, Object> substitutions) {
        Object[] attr = new Object[1 + substitutions.size() * 2];
        attr[0] = name;
        int i = 1;
        for (Map.Entry<String, Object> entry : substitutions.entrySet()) {
            attr[i++] = entry.getKey();
            attr[i++] = entry.getValue();
        }
        return attr;
    }

    private File copyScript(String scriptString) throws CommandExecException {
        try {
            File file = createTempFile(SCRIPT, SCRIPT_POST);
            FileUtils.write(file, scriptString);
            file.setExecutable(true);
            return file;
        } catch (IOException e) {
            throw log.errorCopyScript(this, e);
        }
    }

}
