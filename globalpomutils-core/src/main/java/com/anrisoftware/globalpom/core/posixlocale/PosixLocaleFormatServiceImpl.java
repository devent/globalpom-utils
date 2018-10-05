package com.anrisoftware.globalpom.core.posixlocale;

import static com.google.inject.Guice.createInjector;

import javax.inject.Inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import com.anrisoftware.globalpom.core.localeformat.LocaleFormatModule;

/**
 * POSIX locale format service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component(service = PosixLocaleFormatService.class)
public class PosixLocaleFormatServiceImpl implements PosixLocaleFormatService {

    @Inject
    private PosixLocaleFormatFactory formatFactory;

    @Override
    public PosixLocaleFormat create() {
        return formatFactory.create();
    }

    @Activate
    protected void start() {
        createInjector(new PosixLocaleModule(), new LocaleFormatModule(), new PosixLocaleFormatModule())
                .injectMembers(this);
    }

}
