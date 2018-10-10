/*-
 * #%L
 * Global POM Utilities :: Exec
 * %%
 * Copyright (C) 2014 - 2018 Advanced Natural Research Institute
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
package com.anrisoftware.globalpom.exec.internal.command

import org.apache.commons.exec.util.StringUtils
import org.junit.jupiter.api.Test

/**
 * @see StringUtils
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
class ApacheStringUtilsTest {

    @Test
    void "quote argument"() {
        def inputs = [
            [argument: "", quoted: ""],
            [argument: "aaa", quoted: "aaa"],
            [argument: "\"aaa\"", quoted: "aaa"],
            [argument: "aaa bbb", quoted: "\"aaa bbb\""],
            [argument: "\"aaa bbb\"", quoted: "\"aaa bbb\""],
            [argument: "'aaa'", quoted: "aaa"],
            [argument: "'aaa bbb'", quoted: "\"aaa bbb\""],
            [argument: "'aaa' bbb", quoted: "\"aaa' bbb\""],
            [argument: "aaa' bbb", quoted: "\"aaa' bbb\""],
            [argument: "aaa\" bbb", quoted: "'aaa\" bbb'"],
        ]
        inputs.each {
            def res = StringUtils.quoteArgument it.argument
            assert res == it.quoted
        }
    }
}
