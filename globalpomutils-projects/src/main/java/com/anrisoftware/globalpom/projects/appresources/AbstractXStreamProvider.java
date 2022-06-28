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
package com.anrisoftware.globalpom.projects.appresources;

import javax.inject.Provider;

import com.anrisoftware.globalpom.projects.appproject.DefaultProject;
import com.anrisoftware.globalpom.projects.appprojectres.DefaultProjectResource;
import com.thoughtworks.xstream.XStream;

/**
 * Provides the <i>XStream</i> serializer.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public abstract class AbstractXStreamProvider implements Provider<XStream> {

    private XStream xstream;

    @Override
    public final synchronized XStream get() {
        if (xstream == null) {
            this.xstream = createXStream();
        }
        return xstream;
    }

    /**
     * Creates the XStream.
     *
     * @return the {@link XStream}.
     */
    protected XStream createXStream() {
        XStream xstream = new XStream();
        xstream.setMode(XStream.ID_REFERENCES);
        xstream.processAnnotations(DefaultProject.class);
        xstream.processAnnotations(DefaultProjectResource.class);
        return xstream;
    }

}
