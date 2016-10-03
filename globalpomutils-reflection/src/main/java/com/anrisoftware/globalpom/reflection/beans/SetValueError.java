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
package com.anrisoftware.globalpom.reflection.beans;

import java.lang.reflect.Method;

import com.anrisoftware.globalpom.reflection.exceptions.ReflectionError;

@SuppressWarnings("serial")
public class SetValueError extends ReflectionError {

    public SetValueError(Throwable cause, Object bean, String fieldName,
            Method setter) {
        super("Set value with setter error", cause);
        addContextValue("bean", bean);
        addContextValue("field-name", fieldName);
        addContextValue("setter", setter);
    }

    public SetValueError(Exception cause, Object bean, String fieldName) {
        super("Set value via field error", cause);
        addContextValue("bean", bean);
        addContextValue("field-name", fieldName);
    }

}
