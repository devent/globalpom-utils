package com.anrisoftware.globalpom.core.posixlocale;

import static com.google.inject.Guice.createInjector;

import java.nio.charset.Charset;
import java.util.Locale;

import javax.inject.Inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * Simple locale format service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component(service = PosixLocaleService.class)
public class PosixLocaleServiceImpl implements PosixLocaleService {

    @Inject
    private PosixLocaleFactory localeFactory;

    @Override
    public PosixLocale create(Locale locale) {
        return localeFactory.create(locale);
    }

    @Override
    public PosixLocale create(Locale locale, Charset charset) {
        return localeFactory.create(locale, charset);
    }

    @Activate
    protected void start() {
        createInjector(new PosixLocaleModule()).injectMembers(this);
    }

}
