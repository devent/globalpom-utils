package com.anrisoftware.globalpom.core.pointformat;

import static com.google.inject.Guice.createInjector;

import java.text.NumberFormat;

import javax.inject.Inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * Simple locale format service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component(service = PointFormatService.class)
public class PointFormatServiceImpl implements PointFormatService {

    @Inject
    private PointFormatFactory formatFactory;

    @Override
    public PointFormat defaultFormat() {
        return formatFactory.defaultFormat();
    }

    @Override
    public PointFormat create(NumberFormat format) {
        return formatFactory.create(format);
    }

    @Activate
    protected void start() {
        createInjector(new PointFormatModule()).injectMembers(this);
    }

}
