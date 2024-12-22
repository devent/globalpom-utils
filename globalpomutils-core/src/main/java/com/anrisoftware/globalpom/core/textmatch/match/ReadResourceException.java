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
package com.anrisoftware.globalpom.core.textmatch.match;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;

@SuppressWarnings("serial")
public class ReadResourceException extends MatchTextException {

    public ReadResourceException(IOException e, URI resource, Charset charset) {
        super("Error read resource", e);
        addContextValue("resource", resource);
        addContextValue("charset", charset);
    }

}
