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
package com.anrisoftware.globalpom.checkfilehash

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.resources.ResourcesModule
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
    void "check sha1 hash"() {
        def file = file
        def hash = sha1Hash
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
        def hash = new URI("md5:f371147726ecbcb506e3dfe4a0fd4815")
        def check = factory.create this, file: file, hash: hash
        check = check()
        assert check.matching == true
    }

    @Test
    void "check sha1 hash as URI"() {
        def file = file
        def hash = new URI("sha1:6043cedeee89e9a2ff29c04b00b9a2c40f46439a")
        def check = factory.create this, file: file, hash: hash
        check = check()
        assert check.matching == true
    }

    static Injector injector

    static CheckFileHashFactory factory

    static file = CheckFileHashTest.class.getResource("wordpress_file.tar.gz")

    static md5Hash = CheckFileHashTest.class.getResource("wordpress_file.tar.gz.md5")

    static sha1Hash = CheckFileHashTest.class.getResource("wordpress_file.tar.gz.sha1")

    static sha1NoFileHash = CheckFileHashTest.class.getResource("wordpress_file_no_file.tar.gz.sha1")

    static md5HashUnmatch = CheckFileHashTest.class.getResource("wordpress_file.tar.gz_unmatch.md5")

    static sha1HashUnmatch = CheckFileHashTest.class.getResource("wordpress_file.tar.gz_unmatch.sha1")

    @BeforeClass
    static void createFactory() {
        injector = Guice.createInjector(new CheckFileHashModule(), new ResourcesModule())
        factory = injector.getInstance CheckFileHashFactory
    }
}
