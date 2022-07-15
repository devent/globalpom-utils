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
package com.anrisoftware.globalpom.core.checkfilehash


import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

import com.anrisoftware.globalpom.core.resources.ResourcesModule
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see CheckFileHash
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
class CheckFileHashTest {

    @Test
    void "check md5 hash"() {
        def file = file
        def hash = md5Hash
        def check = factory.create this, file: file, hash: hash
        check = check()
        assert check.matching == true
    }

    @Test
    void "check sha hash"() {
        def file = file
        def hash = shaHash
        def check = factory.create this, file: file, hash: hash
        check = check()
        assert check.matching == true
    }

    @Test
    void "check sha1 hash"() {
        def file = file
        def hash = sha1Hash
        def check = factory.create this, file: file, hash: hash
        check = check()
        assert check.matching == true
    }

    @Test
    void "check sha256 hash"() {
        def file = file
        def hash = sha256Hash
        def check = factory.create this, file: file, hash: hash
        check = check()
        assert check.matching == true
    }

    @Test
    void "check sha1 hash, no file"() {
        def file = file
        def hash = sha1NoFileHash
        def check = factory.create this, file: file, hash: hash
        check = check()
        assert check.matching == true
    }

    @Test
    void "not match md5 hash"() {
        def file = file
        def hash = md5HashUnmatch
        def check = factory.create this, file: file, hash: hash
        check = check()
        assert check.matching == false
    }

    @Test
    void "not match sha1 hash"() {
        def file = file
        def hash = sha1HashUnmatch
        def check = factory.create this, file: file, hash: hash
        check = check()
        assert check.matching == false
    }

    @Test
    void "check md5 hash as URI"() {
        def file = file
        def hash = new URI("md5:d1b0c3ffb4dfd8d0f55a2a3d2a317d31")
        def check = factory.create this, file: file, hash: hash
        check = check()
        assert check.matching == true
    }

    @Test
    void "check sha hash as URI"() {
        def file = file
        def hash = new URI("sha:3d47bc8c8a81efe0b9e47ab4250f1a20ef8c308c")
        def check = factory.create this, file: file, hash: hash
        check = check()
        assert check.matching == true
    }

    @Test
    void "check sha1 hash as URI"() {
        def file = file
        def hash = new URI("sha1:3d47bc8c8a81efe0b9e47ab4250f1a20ef8c308c")
        def check = factory.create this, file: file, hash: hash
        check = check()
        assert check.matching == true
    }

    @Test
    void "check sha256 hash as URI"() {
        def file = file
        def hash = new URI("sha256:c71b73872886f8fefdb8c9012a205e57e20bb54858884e0e0571d8df5f18763e")
        def check = factory.create this, file: file, hash: hash
        check = check()
        assert check.matching == true
    }

    static Injector injector

    static CheckFileHashFactory factory

    static file = CheckFileHashTest.class.getResource("wordpress_file.tar.gz")

    static md5Hash = CheckFileHashTest.class.getResource("wordpress_file.tar.gz.md5")

    static shaHash = CheckFileHashTest.class.getResource("wordpress_file.tar.gz.sha")

    static sha1Hash = CheckFileHashTest.class.getResource("wordpress_file.tar.gz.sha1")

    static sha1NoFileHash = CheckFileHashTest.class.getResource("wordpress_file_no_file.tar.gz.sha1")

    static sha256Hash = CheckFileHashTest.class.getResource("wordpress_file.tar.gz.sha256")

    static md5HashUnmatch = CheckFileHashTest.class.getResource("wordpress_file.tar.gz_unmatch.md5")

    static sha1HashUnmatch = CheckFileHashTest.class.getResource("wordpress_file.tar.gz_unmatch.sha1")

    @BeforeAll
    static void createFactory() {
        injector = Guice.createInjector(new CheckFileHashModule(), new ResourcesModule())
        factory = injector.getInstance CheckFileHashFactory
    }
}
