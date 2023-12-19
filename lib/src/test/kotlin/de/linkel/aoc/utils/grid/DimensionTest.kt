package de.linkel.aoc.utils.grid

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class DimensionTest {
    @Test
    fun `negative dimension do not work`() {
        Assertions.assertThatThrownBy { Dimension(-1, 1) }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { Dimension(1, -1) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `zero dimension do not work`() {
        Assertions.assertThatThrownBy { Dimension(0, 1) }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { Dimension(1, 0) }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
