/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.strings;


import static org.apache.commons.lang3.Validate.notNull;

import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.osgi.service.component.annotations.Component;

/**
 * Converts an argument to a String.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Component(service = ToStringService.class)
public class ToStringServiceImpl implements ToStringService {

    @Override
    public String toString(Map<String, Object> args, String name) {
        Object value = args.get(name);
        return toString(value, name);
    }

    @SuppressWarnings("deprecation")
    @Override
    public String toString(Object arg, String name) {
        notNull(arg, "arg = null");
        return ObjectUtils.toString(arg);
    }

}
