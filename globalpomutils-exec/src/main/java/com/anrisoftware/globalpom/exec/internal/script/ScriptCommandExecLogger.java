/*
 * Copyright 2014-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.exec.internal.script;

import java.io.IOException;

import com.anrisoftware.globalpom.log.AbstractLogger;

class ScriptCommandExecLogger extends AbstractLogger {

    /**
     * Sets the context of the logger to {@link ScriptCommandExec}.
     */
    public ScriptCommandExecLogger() {
        super(ScriptCommandExec.class);
    }

    void deleteScriptFileError(IOException e) {
        log.error(e.getMessage(), e);
    }
}
