package com.anrisoftware.globalpom.exec.core;

import com.anrisoftware.globalpom.exec.command.CommandLine;

interface DefaultProcessTaskFactory {

    DefaultProcessTask create(CommandLine commandLine);
}
