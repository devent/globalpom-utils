package com.anrisoftware.globalpom.core.textmatch.match;

import static com.google.inject.Guice.createInjector;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * Text matcher service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component(service = MatchTextService.class)
public class MatchTextServiceImpl implements MatchTextService {

    @Inject
    private MatchTextFactory factory;

    @Override
    public MatchText create(File file, Pattern pattern, Charset charset) {
        return factory.create(file, pattern, charset);
    }

    @Override
    public MatchText create(URL resource, Pattern pattern, Charset charset) throws URISyntaxException {
        return factory.create(resource, pattern, charset);
    }

    @Override
    public MatchText create(URI resource, Pattern pattern, Charset charset) {
        return factory.create(resource, pattern, charset);
    }

    @Activate
    protected void start() {
        createInjector(new MatchTextModule()).injectMembers(this);
    }

}
