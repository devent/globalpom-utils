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
package com.anrisoftware.globalpom.projects.appresources;

import static com.anrisoftware.globalpom.projects.appresources.AppStoredProperty.LOCALE_PROPERTY;
import static org.apache.commons.lang3.Validate.notNull;

import java.io.File;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.Callable;

import jakarta.inject.Inject;
import javax.inject.Provider;

import com.anrisoftware.globalpom.projects.appexceptions.AppException;
import com.anrisoftware.propertiesutils.ContextProperties;
import com.google.inject.Injector;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;

/**
 * Loads the application resources from the user home directory.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class AppResourcesLoader implements Callable<AbstractAppResources> {

    /**
     * Setups the defaults of the application resources.
     *
     * @author Erwin Müller, erwin.mueller@deventm.de
     * @since 1.0
     */
    public interface SetupDefaults {

        /**
         * Sets the defaults to the application resources.
         *
         * @param appResources
         *            the {@link AbstractAppResources}.
         *
         * @param appDefaults
         *            the application default {@link ContextProperties}.
         *
         * @return the {@link AbstractAppResources}.
         *
         * @throws AppException
         *             if there was an error setup the default properties.
         */
        AbstractAppResources withDefaults(AbstractAppResources appResources,
                ContextProperties appDefaults) throws AppException;
    }

    private static final String USER_HOME = System.getProperty("user.home");

    @Inject
    private AppResourcesLoaderLogger log;

    @Inject
    private Injector injector;

    @Inject
    private Provider<XStream> stream;

    private ContextProperties appDefaults;

    private File storeDir;

    private String fileName;

    private AbstractAppResources defaultResources;

    private SetupDefaults setupDefaults;

    /**
     * @see AppResourcesLoaderFactory#create()
     */
    @Inject
    AppResourcesLoader() {
        this.storeDir = new File(USER_HOME);
        this.setupDefaults = new SetupDefaults() {

            @Override
            public AbstractAppResources withDefaults(
                    AbstractAppResources appResources,
                    ContextProperties appDefaults) throws AppException {
                return appResources;
            }
        };
    }

    /**
     * Sets the application resources and the default properties.
     *
     * @param appResources
     *            the {@link AbstractAppResources}.
     *
     * @param appDefaults
     *            the application default {@link Properties}.
     *
     * @param context
     *            the context {@link Class}.
     *
     * @throws AppException
     *             if there was an error setup the default properties.
     */
    public void setAppResources(AbstractAppResources appResources,
            Properties appDefaults, Class<?> context) throws AppException {
        this.appDefaults = new ContextProperties(context, appDefaults);
        this.defaultResources = appResources;
    }

    /**
     * Sets the store directory.
     *
     * @param dir
     *            the store {@link File} directory.
     */
    public void setStoreDir(File dir) {
        notNull(dir, "storeDir=null");
        this.storeDir = dir;
    }

    /**
     * Sets the configuration file name.
     *
     * @param fileName
     *            the {@link String} name.
     */
    public void setFileName(String fileName) {
        notNull(fileName, "fileName=null");
        this.fileName = fileName;
    }

    /**
     * Sets the setup defaults.
     *
     * @param setupDefaults
     *            the {@link SetupDefaults}.
     */
    public void setSetupDefaults(SetupDefaults setupDefaults) {
        notNull(setupDefaults, "setupDefaults=null");
        this.setupDefaults = setupDefaults;
    }

    @Override
    public AbstractAppResources call() throws Exception {
        notNull(appDefaults, "appDefaults=null");
        notNull(defaultResources, "defaultResources=null");
        notNull(storeDir, "storeDir=null");
        notNull(fileName, "fileName=null");
        this.defaultResources = withDefaults(defaultResources, appDefaults);
        File file = new File(storeDir, fileName);
        if (!file.isFile()) {
            log.fileNotFound(file);
            return defaultResources;
        }
        try {
            AbstractAppResources resources;
            resources = (AbstractAppResources) stream.get().fromXML(file);
            injector.injectMembers(resources);
            return withDefaults(resources, appDefaults);
        } catch (XStreamException e) {
            log.errorLoad(file, e);
            return defaultResources;
        }
    }

    private AbstractAppResources withDefaults(
            AbstractAppResources appResources, ContextProperties appDefaults)
            throws AppException {
        AbstractAppResources r = appResources;
        if (r.getStoredProperty(LOCALE_PROPERTY) == null) {
            r.setStoredProperty(LOCALE_PROPERTY, Locale.getDefault());
        }
        setupDefaults.withDefaults(appResources, appDefaults);
        r.setStoredPropertiesLocked(false);
        return r;
    }

}
