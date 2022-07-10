/*
 * Copyright 2013-2022 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.textscentral;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;

import javax.swing.KeyStroke;

import org.apache.commons.lang3.event.EventListenerSupport;

import com.anrisoftware.globalpom.core.mnemonic.Accelerator;
import com.anrisoftware.globalpom.core.mnemonic.AcceleratorFactory;
import com.anrisoftware.globalpom.core.mnemonic.Mnemonic;
import com.anrisoftware.globalpom.core.mnemonic.MnemonicFactory;
import com.anrisoftware.resources.api.external.LocaleListener;
import com.anrisoftware.resources.api.external.ResourcesException;
import com.anrisoftware.resources.texts.external.Texts;
import com.anrisoftware.resources.texts.external.TextsFactory;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Centralized access to all text resources.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
public class TextsResources {

    /**
     * Action accelerator keys resource bundle.
     */
    public static final String ACTION_ACCELERATORS_PROPERTY = "action_accelerators";

    /**
     * Action mnemonics resource bundle.
     */
    public static final String ACTION_MNEMONICS_PROPERTY = "action_mnemonics";

    /**
     * Action names resource bundle.
     */
    public static final String ACTIONS_PROPERTY = "actions";

    /**
     * Texts resource bundle.
     */
    public static final String TEXTS_PROPERTY = "texts";

    private final EventListenerSupport<LocaleListener> localeListeners;

    private final MnemonicFactory mnemonicFactory;

    private final AcceleratorFactory acceleratorFactory;

    private Texts texts;

    private Texts actions;

    private Texts mnemonics;

    private Texts accelerators;

    private Locale locale;

    /**
     * Sets default properties.
     *
     * @since 1.6
     */
    @AssistedInject
    TextsResources(TextsFactory textsFactory, MnemonicFactory mnemonicFactory, AcceleratorFactory acceleratorFactory) {
        this(textsFactory, mnemonicFactory, acceleratorFactory, createDefaultProperties());
    }

    private static Properties createDefaultProperties() {
        Properties p = new Properties();
        p.setProperty(TEXTS_PROPERTY, "Texts");
        p.setProperty(ACTIONS_PROPERTY, "Actions");
        p.setProperty(ACTION_MNEMONICS_PROPERTY, "ActionMnemonics");
        p.setProperty(ACTION_ACCELERATORS_PROPERTY, "ActionAccelerators");
        return p;
    }

    /**
     * @see TextsResourcesFactory#create(Properties)
     *
     * @since 1.6
     */
    @AssistedInject
    TextsResources(TextsFactory textsFactory, MnemonicFactory mnemonicFactory, AcceleratorFactory acceleratorFactory,
            @Assisted Properties properties) {
        if (properties.containsKey(TEXTS_PROPERTY)) {
            this.texts = textsFactory.create(properties.getProperty(TEXTS_PROPERTY));
        }
        if (properties.containsKey(ACTIONS_PROPERTY)) {
            this.actions = textsFactory.create(properties.getProperty(ACTIONS_PROPERTY));
        }
        if (properties.containsKey(ACTION_MNEMONICS_PROPERTY)) {
            this.mnemonics = textsFactory.create(properties.getProperty(ACTION_MNEMONICS_PROPERTY));
        }
        if (properties.containsKey(ACTION_ACCELERATORS_PROPERTY)) {
            this.accelerators = textsFactory.create(properties.getProperty(ACTION_ACCELERATORS_PROPERTY));
        }
        this.localeListeners = new EventListenerSupport<LocaleListener>(LocaleListener.class);
        this.locale = Locale.getDefault();
        this.mnemonicFactory = mnemonicFactory;
        this.acceleratorFactory = acceleratorFactory;
    }

    /**
     * Sets the texts resources.
     *
     * @param texts the {@link Texts}.
     */
    public void setTexts(Texts texts) {
        Texts oldValue = this.texts;
        this.texts = texts;
        if (oldValue != texts) {
            fireUpdateLocale();
        }
    }

    /**
     * Returns the texts resources.
     *
     * @return the {@link Texts}.
     */
    public Texts getTexts() {
        return texts;
    }

    /**
     * Sets the texts resources for the actions names.
     *
     * @param actions the {@link Texts}.
     */
    public void setActions(Texts actions) {
        Texts oldValue = this.actions;
        this.actions = actions;
        if (oldValue != actions) {
            fireUpdateLocale();
        }
    }

    /**
     * Returns the texts resources for the actions names.
     *
     * @return the {@link Texts}.
     */
    public Texts getActions() {
        return actions;
    }

    /**
     * Sets the texts resources for the mnemonics.
     *
     * @param mnemonics the {@link Texts}.
     */
    public void setMnemonics(Texts mnemonics) {
        Texts oldValue = this.mnemonics;
        this.mnemonics = mnemonics;
        if (oldValue != mnemonics) {
            fireUpdateLocale();
        }
    }

    /**
     * Returns the texts resources for the mnemonics.
     *
     * @return the {@link Texts}.
     */
    public Texts getMnemonics() {
        return mnemonics;
    }

    /**
     * Sets the texts resources for the accelerator keys.
     *
     * @param accelerators the {@link Texts}.
     */
    public void setAccelerators(Texts accelerators) {
        Texts oldValue = this.accelerators;
        this.accelerators = accelerators;
        if (oldValue != accelerators) {
            fireUpdateLocale();
        }
    }

    /**
     * Returns the texts resources for the accelerator keys.
     *
     * @return the {@link Texts}.
     */
    public Texts getAccelerators() {
        return accelerators;
    }

    /**
     * Sets the locale for the texts resources.
     *
     * @param locale the {@link Locale}.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
        fireUpdateLocale();
    }

    /**
     * Returns the locale for the texts resources.
     *
     * @return the {@link Locale}.
     */
    public Locale getLocale() {
        return locale;
    }

    private void fireUpdateLocale() {
        localeListeners.fire().updateLocale(locale);
    }

    /**
     * Returns the text from the texts resources.
     *
     * @param name the {@link String} name.
     *
     * @return the {@link String} text.
     *
     * @throws ResourcesException if there was an error loading the text.
     */
    public String getText(String name) throws ResourcesException {
        return texts.getResource(name, locale).getText();
    }

    /**
     * Returns a formatted text from the texts resource.
     * <p>
     * The string is formatted as in
     * {@link String#format(Locale, String, Object...)} with the current text as the
     * format string and the current locale as the locale.
     *
     * @param name the {@link String} name.
     *
     * @param args the arguments.
     *
     * @return the {@link String} text.
     *
     * @throws ResourcesException if there was an error loading the text.
     */
    public String getTextFormat(String name, Object... args) throws ResourcesException {
        return texts.getResource(name, locale).getFormattedText(args);
    }

    /**
     * Returns the action name from the actions texts resources.
     *
     * @param name the {@link String} resource name.
     *
     * @return the {@link String} action name.
     *
     * @throws ResourcesException if there was an error loading the text.
     */
    public String getAction(String name) throws ResourcesException {
        return actions.getResource(name, locale).getText();
    }

    /**
     * Returns the mnemonic key code from the mnemonics texts resources.
     *
     * @see Mnemonic#getMnemonic()
     *
     * @param name the {@link String} resource name.
     *
     * @return the {@link Integer} mnemonic key code.
     *
     * @throws ResourcesException if there was an error loading the text resource.
     */
    public int getMnemonic(String name) throws ResourcesException {
        String keyname = mnemonics.getResource(name, locale).getText();
        return mnemonicFactory.create(keyname).getMnemonic();
    }

    /**
     * Returns the displayed mnemonic index from the mnemonics texts resources if
     * such index was set in the resource.
     *
     * @see Mnemonic#getMnemonicIndex()
     *
     * @param name the mnemonic text resource {@link String} name.
     *
     * @return displayed mnemonic index or -1. The value -1 means no index was
     *         specified.
     *
     * @throws ResourcesException if there was an error loading the text resource.
     */
    public int getMnemonicIndex(String name) throws ResourcesException {
        String keyname = mnemonics.getResource(name, locale).getText();
        return mnemonicFactory.create(keyname).getMnemonicIndex();
    }

    /**
     * Returns the accelerator key for an action from the accelerators texts
     * resources.
     *
     * @see Accelerator#getAccelerator()
     *
     * @param name the accelerator text resource {@link String} name.
     *
     * @return accelerator {@link KeyStroke} or {@code null} if no accelerator key
     *         was specified.
     *
     * @throws ResourcesException       if there was an error loading the text
     *                                  resource.
     *
     * @throws IllegalArgumentException if the specified accelerator or the modifier
     *                                  are not a valid key code.
     */
    public KeyStroke getAccelerator(String name) {
        try {
            String keyname = accelerators.getResource(name, locale).getText();
            return acceleratorFactory.create(keyname).getAccelerator();
        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * Adds the specified locale listener that is notified if a new locale is set
     * for the texts resources.
     *
     * @param l the {@link LocaleListener}.
     */
    public void addTextsLocaleListeners(LocaleListener l) {
        localeListeners.addListener(l);
    }

    /**
     * Removes the specified locale listener that is notified if a new locale is set
     * for the texts resources.
     *
     * @param l the {@link LocaleListener}.
     */
    public void removeTextsLocaleListeners(LocaleListener l) {
        localeListeners.removeListener(l);
    }

}
