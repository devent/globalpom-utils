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
package com.anrisoftware.globalpom.core.version;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

/**
 * Parses version with major, minor and revision.
 *
 * @see Version
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
@SuppressWarnings("serial")
public class VersionFormat extends Format {

    private static final char VERSION_SEP = '.';

    @Inject
    private VersionFormatLogger log;

    @Inject
    private VersionFactory versionFactory;

    /**
     * Formats the specified version.
     *
     * <pre>
     * major.minor.revision
     * </pre>
     *
     * @param obj  the {@link Version}.
     *
     * @param buff {@link StringBuffer}
     *
     * @param pos  {@link FieldPosition}
     *
     * @return the {@link StringBuffer}
     */
    @Override
    public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
        if (obj instanceof Version) {
            formatDuration(buff, (Version) obj);
        }
        return buff;
    }

    private void formatDuration(StringBuffer buff, Version version) {
        int major = version.getMajor();
        int minor = version.getMinor();
        int revision = version.getRevision();
        buff.append(major);
        if (minor != Integer.MAX_VALUE) {
            buff.append(VERSION_SEP).append(minor);
        }
        if (revision != Integer.MAX_VALUE) {
            buff.append(VERSION_SEP).append(revision);
        }
    }

    /**
     * Parses the specified string to a version.
     * <h2>Format</h2>
     * <ul>
     * <li>{@code "major[.minor[.revision]]"}
     * </ul>
     *
     * @param source the {@link String} source.
     *
     * @param pos    the {@link ParsePosition}
     *
     * @return the parsed {@link Version}.
     */
    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return parse(source, pos);
    }

    /**
     * @see #parse(String, ParsePosition)
     *
     * @param source the {@link String} source.
     *
     * @return the {@link Version}
     *
     * @throws ParseException if there was an error parsing the source.
     */
    public Version parse(String source) throws ParseException {
        ParsePosition pos = new ParsePosition(0);
        Version result = parse(source, pos);
        if (pos.getIndex() == 0) {
            throw log.errorParse(source, pos);
        }
        return result;
    }

    /**
     * @see #parseObject(String)
     *
     * @param source the {@link String} source.
     *
     * @param pos    the index {@link ParsePosition} position from where to start
     *               parsing.
     *
     * @return the {@link Version}
     */
    public Version parse(String source, ParsePosition pos) {
        try {
            source = source.substring(pos.getIndex());
            Version version = decodeVersion(source, pos);
            pos.setErrorIndex(-1);
            pos.setIndex(pos.getIndex() + source.length());
            return version;
        } catch (NumberFormatException e) {
            log.errorParseNumber(e, source);
            pos.setIndex(0);
            pos.setErrorIndex(0);
            return null;
        }
    }

    private Version decodeVersion(String source, ParsePosition pos) {
        String[] str = StringUtils.split(source, VERSION_SEP);
        int major = Integer.valueOf(str[0]);
        int minor = Integer.MAX_VALUE;
        int rev = Integer.MAX_VALUE;
        if (str.length > 1) {
            minor = Integer.valueOf(str[1]);
        }
        if (str.length > 2) {
            rev = Integer.valueOf(str[2]);
        }
        return versionFactory.create(major, minor, rev);
    }
}
