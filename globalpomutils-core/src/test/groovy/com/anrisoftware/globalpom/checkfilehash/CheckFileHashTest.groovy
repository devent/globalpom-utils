/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
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

    static md5HashUnmatch = CheckFileHashTest.class.getResource("wordpress_file.tar.gz_unmatch.md5")

    static sha1HashUnmatch = CheckFileHashTest.class.getResource("wordpress_file.tar.gz_unmatch.sha1")

    @BeforeClass
    static void createFactory() {
        injector = Guice.createInjector(new CheckFileHashModule(), new ResourcesModule())
        factory = injector.getInstance CheckFileHashFactory
    }
}