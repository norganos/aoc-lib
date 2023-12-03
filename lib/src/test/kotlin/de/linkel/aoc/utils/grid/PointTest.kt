package de.linkel.aoc.utils.grid

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class PointTest {
    @Test
    fun `basic properties work`() {
        val point = Point(1, 2)
        Assertions.assertThat(point.x).isEqualTo(1)
        Assertions.assertThat(point.y).isEqualTo(2)
    }

    @Test
    fun `can be moved`() {
        val point = Point(1, 2) + Vector(2, -1)
        Assertions.assertThat(point.x).isEqualTo(3)
        Assertions.assertThat(point.y).isEqualTo(1)
    }

    @Test
    fun `2 points form a vector`() {
        val vector = Point(1, 2) - Point(1,1)
        Assertions.assertThat(vector.deltaX).isEqualTo(0)
        Assertions.assertThat(vector.deltaY).isEqualTo(1)
    }
}
