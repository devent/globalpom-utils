/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.core.schemagenerator;

import static java.lang.String.format;
import static java.util.EnumSet.of;
import static org.hibernate.tool.schema.TargetType.SCRIPT;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Generates the database schema for Hibernate entities.
 * 
 * @author Original author
 *         <ul>
 *         <li><a href=
 *         "http://blog.iprofs.nl/2013/01/29/hibernate4-schema-generation-ddl-from-annotated-entities/"
 *         >Hibernate4 schema generation (ddl) from annotated entities
 *         [profs.nl] </a></li>
 *         </ul>
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 3.1
 */
public class SchemaGenerator {

    private final Metadata metadata;

    private final Dialect dialect;

    public SchemaGenerator(String packageName, Dialect dialect)
            throws Exception {
        BootstrapServiceRegistry bsr;
        bsr = new BootstrapServiceRegistryBuilder().build();
        StandardServiceRegistryBuilder ssrBuilder;
        ssrBuilder = new StandardServiceRegistryBuilder(bsr);
        ssrBuilder.applySetting("hibernate.dialect", dialect.dialectClass);
        ServiceRegistry serviceRegistry = ssrBuilder.build();
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        for (Class<?> clazz : getClasses(packageName)) {
            metadataSources.addAnnotatedClass(clazz);
        }
        MetadataBuilder metadataBuilder = metadataSources.getMetadataBuilder();
        this.metadata = metadataBuilder.build();
        this.dialect = dialect;
    }

    /**
     * Utility method used to fetch Class list based on a package name.
     *
     * @param packageName
     *            (should be the package containing your annotated beans.
     */
    private List<Class<?>> getClasses(String packageName) throws Exception {
        File directory = null;
        try {
            ClassLoader cld = getClassLoader();
            URL resource = getResource(packageName, cld);
            directory = new File(resource.toURI());
        } catch (NullPointerException ex) {
            throw new ClassNotFoundException(packageName + " (" + directory
                    + ") does not appear to be a valid package");
        }
        return collectClasses(packageName, directory);
    }

    private ClassLoader getClassLoader() throws ClassNotFoundException {
        ClassLoader cld = Thread.currentThread().getContextClassLoader();
        if (cld == null) {
            throw new ClassNotFoundException("Can't get class loader.");
        }
        return cld;
    }

    private URL getResource(String packageName, ClassLoader cld)
            throws ClassNotFoundException {
        String path = packageName.replace('.', '/');
        URL resource = cld.getResource(path);
        if (resource == null) {
            throw new ClassNotFoundException("No resource for " + path);
        }
        return resource;
    }

    private List<Class<?>> collectClasses(String packageName, File directory)
            throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (directory.exists()) {
            String[] files = directory.list();
            for (String file : files) {
                if (file.endsWith(".class")) {
                    // removes the .class extension
                    classes.add(Class.forName(packageName + '.'
                            + file.substring(0, file.length() - 6)));
                }
            }
        } else {
            throw new ClassNotFoundException(
                    format("%s is not a valid package.", packageName));
        }
        return classes;
    }

    /**
     * Method that actually creates the file.
     *
     * @return the generated {@link File}.
     */
    public File generate(File directory) {
        SchemaExport export = new SchemaExport();
        export.setDelimiter(";");
        File file = new File(directory,
                format("ddl_%s.sql", dialect.name().toLowerCase()));
        export.setOutputFile(file.getAbsolutePath());
        export.setFormat(true);
        export.createOnly(of(SCRIPT), metadata);
        return file;
    }

    /**
     * Holds the classnames of hibernate dialects for easy reference.
     */
    public static enum Dialect {

        ORACLE("org.hibernate.dialect.Oracle10gDialect"),

        MYSQL("org.hibernate.dialect.MySQLDialect"),

        HSQL("org.hibernate.dialect.HSQLDialect"),

        H2("org.hibernate.dialect.H2Dialect");

        private String dialectClass;

        private Dialect(String dialectClass) {
            this.dialectClass = dialectClass;
        }

    }
}
