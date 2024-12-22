/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import static org.apache.commons.lang3.Validate.isTrue;

import jakarta.inject.Inject;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.inject.assistedinject.Assisted;

/**
 * Comparable version number. Compares the major, minor and revision version
 * numbers.
 *
 * <pre>
 * 1.0 = 1.0
 * 1.0.0 &lt; 1.0.1
 * 0.5.2 &gt; 0.5.0
 * </pre>
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
public class Version implements Comparable<Version> {

    private static final String VERSION_POSITIVE = "Version number must be positive";

    static final String REV_FIELD = "rev";

    private static final String REVISION = "revision";

    static final String MINOR_FIELD = "minor";

    static final String MAJOR_FIELD = "major";

    private final int major;

    private final int minor;

    private final int rev;

    /**
     * @see VersionFactory#create(int, int, int)
     */
    @Inject
    Version(@Assisted(MAJOR_FIELD) int major, @Assisted(MINOR_FIELD) int minor, @Assisted(REV_FIELD) int rev) {
        isTrue(major > -1, VERSION_POSITIVE);
        isTrue(minor > -1, VERSION_POSITIVE);
        isTrue(rev > -1, VERSION_POSITIVE);
        this.major = major;
        this.minor = minor;
        this.rev = rev;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getRevision() {
        return rev;
    }

    @Override
    public int compareTo(Version o) {
        if (this.major != o.major) {
            return Integer.compare(this.major, o.major);
        }
        if (this.minor != o.minor) {
            return Integer.compare(this.minor, o.minor);
        }
        if (this.rev != o.rev) {
            return Integer.compare(this.rev, o.rev);
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Version)) {
            return false;
        }
        Version rhs = (Version) obj;
        return new EqualsBuilder().append(major, rhs.major).append(minor, rhs.minor).append(rev, rhs.rev).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(major).append(minor).append(rev).toHashCode();
    }

    @Override
    public String toString() {
        ToStringBuilder b = new ToStringBuilder(this).append(MAJOR_FIELD, major);
        if (minor != Integer.MAX_VALUE) {
            b.append(MINOR_FIELD, minor);
        }
        if (rev != Integer.MAX_VALUE) {
            b.append(REVISION, rev);
        }
        return b.toString();
    }
}
