package de.linkel.aoc.utils

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class StringTest {
    @Test
    fun `can replace s single character at a given position`() {
        Assertions.assertThat("test".replaceIndex(1, '3')).isEqualTo("t3st")
    }
}
