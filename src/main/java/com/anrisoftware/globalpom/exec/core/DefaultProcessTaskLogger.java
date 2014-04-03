package com.anrisoftware.globalpom.exec.core;

import static com.anrisoftware.globalpom.exec.core.DefaultProcessTaskLogger._.command_interrupted;
import static com.anrisoftware.globalpom.exec.core.DefaultProcessTaskLogger._.command_interrupted_message;
import static com.anrisoftware.globalpom.exec.core.DefaultProcessTaskLogger._.error_exit_code;
import static com.anrisoftware.globalpom.exec.core.DefaultProcessTaskLogger._.error_exit_code_message;
import static com.anrisoftware.globalpom.exec.core.DefaultProcessTaskLogger._.error_start_command;
import static com.anrisoftware.globalpom.exec.core.DefaultProcessTaskLogger._.error_start_command_message;
import static com.anrisoftware.globalpom.exec.core.DefaultProcessTaskLogger._.exit_codes;
import static com.anrisoftware.globalpom.exec.core.DefaultProcessTaskLogger._.return_code;
import static com.anrisoftware.globalpom.exec.core.DefaultProcessTaskLogger._.task_;

import java.io.IOException;
import java.util.Arrays;

import com.anrisoftware.globalpom.exec.api.CommandExecException;
import com.anrisoftware.globalpom.exec.command.CommandLine;
import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link DefaultProcessTask}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
class DefaultProcessTaskLogger extends AbstractLogger {

    enum _ {

        error_exit_code("Error exit code"),

        task_("task"),

        return_code("return code"),

        exit_codes("exit codes"),

        error_exit_code_message(
                "Error exit code for '{}', return code {}, valid exit codes {}."),

        error_start_command("Error start command"),

        error_start_command_message("Error start command '{}'."),

        command_interrupted("Command interrupted"),

        command_interrupted_message("Command interrupted '{}'.");

        private String name;

        private _(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Sets the context of the logger to {@link DefaultProcessTask}.
     */
    public DefaultProcessTaskLogger() {
        super(DefaultProcessTask.class);
    }

    CommandExecException invalidExitCode(DefaultProcessTask task, int ret,
            int[] exitCodes, CommandLine line) {
        String codes = Arrays.toString(exitCodes);
        return logException(
                new CommandExecException(error_exit_code).add(task_, task)
                        .add(return_code, ret).add(exit_codes, codes),
                error_exit_code_message, line.getExecutable(), ret, codes);
    }

    CommandExecException errorStartCommand(DefaultProcessTask task,
            IOException e, CommandLine line) {
        String ex = line.getExecutable();
        return logException(
                new CommandExecException(error_start_command, e).add(task_,
                        task), error_start_command_message, ex);
    }

    CommandExecException commandInterrupted(DefaultProcessTask task,
            InterruptedException e, CommandLine line) {
        String ex = line.getExecutable();
        return logException(
                new CommandExecException(command_interrupted, e).add(task_,
                        task), command_interrupted_message, ex);
    }
}
