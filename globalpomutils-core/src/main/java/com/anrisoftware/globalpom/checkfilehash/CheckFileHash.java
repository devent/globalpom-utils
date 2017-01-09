/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.checkfilehash;

import static com.anrisoftware.globalpom.checkfilehash.HashName.forExtension;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.io.IOUtils.readLines;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.inject.assistedinject.Assisted;

/**
 * Checks the hash of the specified file.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
public class CheckFileHash implements Callable<CheckFileHash> {

    private static final String SEP = " ";

    private static final String HASH_RESOURCE = "hash resource";

    private static final String FILE = "file";

    private final CheckFileHashLogger log;

    private final URI file;

    private final URI hashResource;

    private final HashName hashName;

    private boolean matching;

    private final Charset charset;

    /**
     * @see CheckFileHashFactory#create(Map, Object)
     */
    @Inject
    CheckFileHash(CheckFileHashLogger log, @Assisted Map<String, Object> args,
            @Assisted Object script) {
        this.log = log;
        this.file = log.file(script, args);
        this.hashResource = log.hash(script, args);
        this.charset = log.charset(script, args);
        this.hashName = hashName(hashResource);
    }

    private HashName hashName(URI hashResource) {
        HashName hashName = null;
        if (hashResource.getScheme() != null) {
            hashName = forExtension(hashResource.getScheme());
        }
        if (hashName == null) {
            String ex = getExtension(hashResource.getPath());
            hashName = forExtension(ex);
        }
        return hashName;
    }

    @Override
    public CheckFileHash call() throws Exception {
        String hashstr = readHash(hashResource, hashName);
        String expectedHashstr = readExpectedHash(hashResource);
        this.matching = hashstr.equals(expectedHashstr);
        log.hashMatching(this, expectedHashstr, hashstr, matching);
        return this;
    }

    /**
     * Returns if the hash of the file and the specified hash is matching.
     *
     * @return {@code true} if the hashes is matching.
     */
    public boolean isMatching() {
        return matching;
    }

    private String readHash(URI hashResource, HashName hashName)
            throws NoSuchAlgorithmException, IOException,
            MalformedURLException {
        MessageDigest md = MessageDigest.getInstance(hashName.getHashName());
        InputStream fis = file.toURL().openStream();
        byte[] mdbytes = readFile(md, fis);
        String hashstr = toHexString(mdbytes);
        return hashstr;
    }

    private String readExpectedHash(URI hashResource) throws Exception {
        if (forExtension(hashResource.getScheme()) != null) {
            return hashResource.getSchemeSpecificPart();
        } else {
            String line = readLine(hashResource);
            String hash = StringUtils.split(line, SEP)[0];
            return hash;
        }
    }

    private String readLine(URI hashResource)
            throws IOException, MalformedURLException {
        return readLines(hashResource.toURL().openStream(), charset).get(0);
    }

    private String toHexString(byte[] mdbytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mdbytes.length; i++) {
            String s = Integer.toString((mdbytes[i] & 0xff) + 0x100, 16);
            sb.append(s.substring(1));
        }
        return sb.toString();
    }

    private byte[] readFile(MessageDigest md, InputStream fis)
            throws IOException {
        byte[] dataBytes = new byte[1024];
        int nread = 0;
        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }
        return md.digest();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(FILE, file)
                .append(HASH_RESOURCE, hashResource).toString();
    }
}
