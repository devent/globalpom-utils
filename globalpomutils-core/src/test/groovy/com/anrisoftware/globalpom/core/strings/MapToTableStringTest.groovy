package com.anrisoftware.globalpom.core.strings

import org.junit.jupiter.api.Test

class MapToTableStringTest {

    @Test
    void "with defaults to string"() {
        def string = MapToTableString.mapToString map
        assert string == """Aaa := Bbb
Ccc := Ddd"""
    }

    static final map = ["Aaa": "Bbb", "Ccc": "Ddd"]
}
