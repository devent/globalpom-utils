/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.projects.appproject;

import java.beans.PropertyVetoException;

import com.anrisoftware.globalpom.projects.appexceptions.AppException;

@SuppressWarnings("serial")
public class ProjectCreateException extends AppException {

    private static final String MESSAGE = "Error create default project";

    public ProjectCreateException(PropertyVetoException e) {
        super(MESSAGE, e);
    }

}
