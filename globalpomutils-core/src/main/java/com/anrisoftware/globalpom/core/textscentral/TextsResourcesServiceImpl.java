package com.anrisoftware.globalpom.core.textscentral;

import static com.google.inject.Guice.createInjector;
import static com.google.inject.util.Providers.of;

import java.util.Properties;

import javax.inject.Inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.anrisoftware.globalpom.core.mnemonic.AcceleratorFactory;
import com.anrisoftware.globalpom.core.mnemonic.AcceleratorService;
import com.anrisoftware.globalpom.core.mnemonic.MnemonicFactory;
import com.anrisoftware.globalpom.core.mnemonic.MnemonicService;
import com.anrisoftware.resources.binary.external.BinariesBundlesMapFactory;
import com.anrisoftware.resources.binary.external.BinariesBundlesMapService;
import com.anrisoftware.resources.binary.external.BinariesFactory;
import com.anrisoftware.resources.binary.external.BinariesMapFactory;
import com.anrisoftware.resources.binary.external.BinariesMapService;
import com.anrisoftware.resources.binary.external.BinariesService;
import com.anrisoftware.resources.binary.external.BinaryResourceFactory;
import com.anrisoftware.resources.binary.external.BinaryResourceService;
import com.anrisoftware.resources.texts.external.TextsBundlesMapFactory;
import com.anrisoftware.resources.texts.external.TextsBundlesMapService;
import com.anrisoftware.resources.texts.external.TextsFactory;
import com.anrisoftware.resources.texts.external.TextsMapFactory;
import com.anrisoftware.resources.texts.external.TextsMapService;
import com.anrisoftware.resources.texts.external.TextsService;
import com.google.inject.AbstractModule;

/**
 * Centralized access to text resources service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component(service = TextsResourcesService.class)
public class TextsResourcesServiceImpl implements TextsResourcesService {

    @Inject
    private TextsResourcesFactory factory;

    @Reference
    private AcceleratorService acceleratorService;

    @Reference
    private MnemonicService mnemonicService;

    @Reference
    private BinariesBundlesMapService binariesBundlesMapService;

    @Reference
    private BinariesMapService binariesMapService;

    @Reference
    private BinariesService binariesService;

    @Reference
    private BinaryResourceService binaryResourceService;

    @Reference
    private TextsBundlesMapService textsBundlesMapService;

    @Reference
    private TextsMapService textsMapService;

    @Reference
    private TextsService textsService;

    @Override
    public TextsResources create() {
        return factory.create();
    }

    @Override
    public TextsResources create(Properties properties) {
        return factory.create(properties);
    }

    @Activate
    protected void start() {
        createInjector(new TextsCentralModule(), new AbstractModule() {

            @Override
            protected void configure() {
                bind(AcceleratorFactory.class).toProvider(of(acceleratorService));
                bind(MnemonicFactory.class).toProvider(of(mnemonicService));
                bind(BinariesBundlesMapFactory.class).toProvider(of(binariesBundlesMapService));
                bind(BinariesMapFactory.class).toProvider(of(binariesMapService));
                bind(BinariesFactory.class).toProvider(of(binariesService));
                bind(BinaryResourceFactory.class).toProvider(of(binaryResourceService));
                bind(TextsBundlesMapFactory.class).toProvider(of(textsBundlesMapService));
                bind(TextsMapFactory.class).toProvider(of(textsMapService));
                bind(TextsFactory.class).toProvider(of(textsService));
            }
        }).injectMembers(this);
    }

}
