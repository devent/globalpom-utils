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
package com.anrisoftware.globalpom.data.dataimport;

/*-
 * #%L
 * Global POM Utilities :: Data
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.text.Format;
import java.text.ParseException;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.assistedinject.Assisted;

/**
 * Parses the column.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public class TypedColumn implements Column {

    private final String name;

    private final Format format;

    /**
     * @see StringColumnFactory#create(String)
     */
    @Inject
    TypedColumn(@Assisted String name, @Assisted Format format) {
        this.name = name;
        this.format = format;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object parseValue(String string) throws ParseException {
        if (StringUtils.isEmpty(string)) {
            return null;
        } else {
            return format.parseObject(string);
        }
    }

}
