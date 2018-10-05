package com.anrisoftware.globalpom.core.localeformat;

import static com.google.inject.Guice.createInjector;

import javax.inject.Inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * Simple locale format service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component(service = LocaleFormatService.class)
public class LocaleFormatServiceImpl implements LocaleFormatService {

    @Inject
    private LocaleFormatFactory formatFactory;

    @Override
    public LocaleFormat create() {
        return formatFactory.create();
    }

    @Activate
    protected void start() {
        createInjector(new LocaleFormatModule()).injectMembers(this);
    }

}
