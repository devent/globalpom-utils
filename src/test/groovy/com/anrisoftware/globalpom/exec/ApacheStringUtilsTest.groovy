package com.anrisoftware.globalpom.exec

import org.apache.commons.exec.util.StringUtils
import org.junit.Test

/**
 * @see StringUtils
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class ApacheStringUtilsTest {

    @Test
    void "quote argument"() {
        def inputs = [
            [argument: "", quoted: ""],
            [argument: "aaa", quoted: "aaa"],
            [argument: "\"aaa\"", quoted: "aaa"],
            [argument: "aaa bbb", quoted: "\"aaa bbb\""],
            [argument: "\"aaa bbb\"", quoted: "\"aaa bbb\""],
            [argument: "'aaa'", quoted: "aaa"],
            [argument: "'aaa bbb'", quoted: "\"aaa bbb\""],
            [argument: "'aaa' bbb", quoted: "\"aaa' bbb\""],
            [argument: "aaa' bbb", quoted: "\"aaa' bbb\""],
            [argument: "aaa\" bbb", quoted: "'aaa\" bbb'"],
        ]
        inputs.each {
            def res = StringUtils.quoteArgument it.argument
            assert res == it.quoted
        }
    }
}
