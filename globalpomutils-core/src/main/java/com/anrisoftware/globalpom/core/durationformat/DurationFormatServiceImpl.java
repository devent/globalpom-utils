package com.anrisoftware.globalpom.core.durationformat;

import static com.google.inject.Guice.createInjector;

import javax.inject.Inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * Computer duration format service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component(service = DurationFormatService.class)
public class DurationFormatServiceImpl implements DurationFormatService {

    @Inject
    private DurationFormatFactory durationFormatFactory;

    @Override
    public DurationFormat create() {
        return durationFormatFactory.create();
    }

    @Activate
    protected void start() {
        createInjector(new DurationFormatModule()).injectMembers(this);
    }

}
