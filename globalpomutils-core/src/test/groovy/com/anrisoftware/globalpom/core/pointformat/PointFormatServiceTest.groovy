package com.anrisoftware.globalpom.core.pointformat

import static com.anrisoftware.globalpom.utils.TestUtils.*

import java.awt.geom.Point2D
import java.text.ParsePosition

import org.apache.sling.testing.mock.osgi.junit5.OsgiContext
import org.apache.sling.testing.mock.osgi.junit5.OsgiContextExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import com.anrisoftware.globalpom.utils.TestUtils

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

/**
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
@CompileStatic
@ExtendWith(OsgiContextExtension.class)
class PointFormatServiceTest {

    final OsgiContext context = new OsgiContext()

    @Test
    void "parse point, default decimal format"() {
        def format = service.defaultFormat()
        List<Map> outputs = new tests_outputs().run() as List
        List<Map> inputs = new tests_inputs().run() as List
        inputs.eachWithIndex { Map input, int i ->
            def point = format.parse input.source as String, new ParsePosition(0), input.point as Point2D
            assert point == outputs[i]
        }
    }

    PointFormatService service

    @BeforeEach
    void createFactories() {
        TestUtils.toStringStyle
        this.service = context.registerInjectActivateService(new PointFormatServiceImpl(), null)
    }
}
