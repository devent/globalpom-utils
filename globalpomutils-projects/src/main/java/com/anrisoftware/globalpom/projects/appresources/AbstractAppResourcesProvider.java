/*
 * Copyright 2013-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import static org.apache.commons.lang3.Validate.notNull;

import java.io.File;
import java.util.Properties;

import javax.inject.Inject;

import com.anrisoftware.globalpom.projects.appexceptions.AppException;
import com.anrisoftware.globalpom.projects.appresources.AppResourcesLoader.SetupDefaults;
import com.anrisoftware.propertiesutils.ContextProperties;
import com.google.inject.Provider;

/**
 * Provides the application resources. The application resources are loaded from
 * the previously saved resources file.
 *
 * @param <T>
 *            the sub type of the {@link AbstractAppResources} that is specific
 *            for the application.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public abstract class AbstractAppResourcesProvider<T extends AbstractAppResources>
        implements Provider<T> {

    @Inject
    private AbstractAppResourcesProviderLogger log;

    @Inject
    private AppResourcesLoader loader;

    private T loadedResources;

    private File appConfigStorageDir;

    private T appResources;

    private Properties appDefaults;

    private Class<?> context;

    private SetupDefaults setupDefaults;

    private static final File USER_HOME = new File(
            System.getProperty("user.home"));

    protected AbstractAppResourcesProvider() {
        this.appConfigStorageDir = USER_HOME;
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
     * Sets the application specific resources.
     *
     * @param appResources
     *            the sub type of the {@link AbstractAppResources} that is
     *            specific for the application.
     *
     * @param appDefaults
     *            the application default {@link Properties}.
     *
     * @param context
     *            the context {@link Class}.
     */
    public void setAppResources(T appResources, Properties appDefaults,
            Class<?> context) {
        this.appResources = appResources;
        this.appDefaults = appDefaults;
        this.context = context;
    }

    /**
     * Sets the application configuration storage directory. Defaults to the
     * {@code user.home} variable.
     *
     * @param dir
     *            the {@link File} directory.
     */
    public void setAppConfigStorageDir(File dir) {
        this.appConfigStorageDir = dir;
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
    public synchronized T get() {
        if (loadedResources == null) {
            loadedResources = loadResources();
        }
        return loadedResources;
    }

    @SuppressWarnings("unchecked")
    private T loadResources() {
        try {
            loader.setAppResources(appResources, appDefaults, context);
            loader.setStoreDir(appConfigStorageDir);
            loader.setFileName(appResources.getName());
            loader.setSetupDefaults(setupDefaults);
            AbstractAppResources res = loader.call();
            setupDefaults(res);
            return (T) res;
        } catch (Exception e) {
            throw log.errorLoadResources(e, this);
        }
    }

    private void setupDefaults(AbstractAppResources r) {
    }

}
