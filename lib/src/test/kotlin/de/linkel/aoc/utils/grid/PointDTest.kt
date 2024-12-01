package de.linkel.aoc.utils.grid

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class PointDTest {
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
        Assertions.assertThat(Point(1, 2) - Vector(2, -1)).isEqualTo(Point(-1, 3))
    }

    @Test
    fun `2 points form a vector`() {
        val vector = Point(1, 2) - Point(1,1)
        Assertions.assertThat(vector.deltaX).isEqualTo(0)
        Assertions.assertThat(vector.deltaY).isEqualTo(1)
    }

    @Test
    fun `point coordinates can be destructured`() {
        val (x, y) = Point(3, 2)
        Assertions.assertThat(x).isEqualTo(3)
        Assertions.assertThat(y).isEqualTo(2)
    }

    @Test
    fun `point ranges work correctly`() {
        Assertions.assertThat(
            (Point(1, 2)..Point(5, 2))
                .toList()
        ).isEqualTo(
            listOf(
                Point(1, 2),
                Point(2, 2),
                Point(3, 2),
                Point(4, 2),
                Point(5, 2)
            )
        )
        Assertions.assertThat(
            (Point(1, 2)..Point(0, 0))
                .toList()
        ).isEqualTo(
            listOf(
                Point(1, 2), Point(0, 0)
            )
        )
        Assertions.assertThat(
            (Point(1, 2)..Point(1, 0))
                .toList()
        ).isEqualTo(
            listOf(
                Point(1, 2),
                Point(1, 1),
                Point(1, 0),
            )
        )
        Assertions.assertThatThrownBy { Point(1, 2)..Point(1,2) }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
