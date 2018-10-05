package com.anrisoftware.globalpom.core.durationsimpleformat;

import static com.google.inject.Guice.createInjector;

import java.text.NumberFormat;

import javax.inject.Inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * Simple duration format service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component(service = DurationSimpleFormatService.class)
public class DurationSimpleFormatServiceImpl implements DurationSimpleFormatService {

    @Inject
    private DurationSimpleFormatFactory formatFactory;

    @Override
    public DurationSimpleFormat create() {
        return formatFactory.create();
    }

    @Override
    public DurationSimpleFormat create(NumberFormat format) {
        return formatFactory.create(format);
    }

    @Activate
    protected void start() {
        createInjector(new DurationSimpleFormatModule()).injectMembers(this);
    }

}
