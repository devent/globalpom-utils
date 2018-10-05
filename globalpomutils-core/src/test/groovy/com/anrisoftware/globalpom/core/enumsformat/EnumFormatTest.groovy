/*-
 * #%L
 * Global POM Utilities :: Core
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
package com.anrisoftware.globalpom.core.enumsformat

import java.text.Format

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * @see EnumFormat
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class EnumFormatTest {

    static enum TestEnum {
        FOO, BAR
    }

    @Test
    void "parse enum"() {
        inputs.each {
            assert format.parseObject(it.str) == it.item
        }
    }

    @Test
    void "format enum"() {
        inputs.each {
            assert format.format(it.item) == it.format
        }
    }

    Format format

    @BeforeEach
    void setupFormat() {
        format = new EnumFormat(TestEnum)
    }

    static inputs = [
        [str: "FOO", format: "FOO", item: TestEnum.FOO],
        [str: "BAR", format: "BAR", item: TestEnum.BAR],
        [str: "foo", format: "FOO", item: TestEnum.FOO],
    ]
}
