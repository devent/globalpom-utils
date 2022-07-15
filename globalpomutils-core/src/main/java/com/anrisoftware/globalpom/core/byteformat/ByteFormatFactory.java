/*
 * Copyright 2013-2022 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.byteformat;

import java.text.NumberFormat;

/**
 * Factory to create a new computer byte format.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public interface ByteFormatFactory {

    /**
     * Create value format that uses the default number format to format the
     * computer byte.
     *
     * @return the {@link ByteFormat}.
     */
    ByteFormat create();

    /**
     * Create value format with the specified number format to format the computer
     * byte.
     *
     * @param format the {@link NumberFormat}.
     *
     * @return the {@link ByteFormat}.
     */
    ByteFormat create(NumberFormat format);
}
