/*
 * Copyright 2015-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-projects.
 *
 * globalpomutils-projects is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-projects is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-projects. If not, see <http://www.gnu.org/licenses/>.
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
