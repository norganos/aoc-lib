package de.linkel.aoc.utils.grid

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class SegmentTest {
    @Test
    fun `basic properties work with point-to-point constructor`() {
        val segment = Segment(Point(1, 1), Point(5, 1))
        Assertions.assertThat(segment.start).isEqualTo(Point(1, 1))
        Assertions.assertThat(segment.end).isEqualTo(Point(5, 1))
        Assertions.assertThat(segment.vector).isEqualTo(Vector(4, 0))
        Assertions.assertThat(segment.manhattanDistance).isEqualTo(4)
    }
    @Test
    fun `basic properties work with point-vector constructor`() {
        val segment = Segment(Point(1, 1), Vector(5, 1))
        Assertions.assertThat(segment.start).isEqualTo(Point(1, 1))
        Assertions.assertThat(segment.end).isEqualTo(Point(6, 2))
        Assertions.assertThat(segment.vector).isEqualTo(Vector(5, 1))
        Assertions.assertThat(segment.manhattanDistance).isEqualTo(6)
    }
    @Test
    fun `basic properties work with point-range method`() {
        val segment = Point(1, 1)..Point(5, 1)
        Assertions.assertThat(segment.start).isEqualTo(Point(1, 1))
        Assertions.assertThat(segment.end).isEqualTo(Point(5, 1))
        Assertions.assertThat(segment.vector).isEqualTo(Vector(4, 0))
        Assertions.assertThat(segment.manhattanDistance).isEqualTo(4)
    }
    @Test
    fun `iterator works`() {
        Assertions.assertThat(
            Segment(Point(1, 1), Point(3, 1)).toList()
        ).isEqualTo(
            listOf(
                Point(1,1), Point(2, 1), Point(3, 1)
            )
        )
        Assertions.assertThat(
            Segment(Point(1, 1), Point(1, -1)).toList()
        ).isEqualTo(
            listOf(
                Point(1,1), Point(1, 0), Point(1, -1)
            )
        )
        Assertions.assertThat(
            Segment(Point(1, 1), Point(4, 4)).toList()
        ).isEqualTo(
            listOf(
                Point(1,1), Point(2, 2), Point(3, 3), Point(4, 4)
            )
        )
        Assertions.assertThat(
            Segment(Point(1, 1), Point(5, 2)).toList()
        ).isEqualTo(
            listOf(
                Point(1,1), Point(5, 2)
            )
        )
        Assertions.assertThat(
            Segment(Point(-1, 1), Point(-7, 4)).toList()
        ).isEqualTo(
            listOf(
                Point(-1,1), Point(-3, 2), Point(-5, 3), Point(-7, 4)
            )
        )
    }

    @Test
    fun `can be moved`() {
        Assertions.assertThat(
            Segment(Point(1, 1), Point(2, 1)) + Vector(2, 1)
        ).isEqualTo(
            Segment(Point(3, 2), Point(4, 2))
        )
        Assertions.assertThat(
            Segment(Point(1, 1), Point(3, 2)) - Vector(2, 1)
        ).isEqualTo(
            Segment(Point(-1, 0), Point(1, 1))
        )
    }

    @Test
    fun `can be resized`() {
        Assertions.assertThat(
            Segment(Point(1, 1), Point(3, 2)) * 3
        ).isEqualTo(
            Segment(Point(1, 1), Point(7, 4))
        )
        Assertions.assertThat(
            Segment(Point(1, 1), Point(7, 4)) / 3
        ).isEqualTo(
            Segment(Point(1, 1), Point(3, 2))
        )
        Assertions.assertThatThrownBy { Segment(Point(1, 1), Point(7, 4)) / 2 }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { Segment(Point(1, 1), Point(7, 4)) / 0 }.isInstanceOf(ArithmeticException::class.java)
    }

    @Test
    fun `inside check is correct`() {
        val segment1 = Segment(Point(0, 0), Point(3, 3))
        Assertions.assertThat(Point(0,0) in segment1).isTrue()
        Assertions.assertThat(Point(1,1) in segment1).isTrue()
        Assertions.assertThat(Point(2,2) in segment1).isTrue()
        Assertions.assertThat(Point(3,3) in segment1).isTrue()
        Assertions.assertThat(Point(4,4) in segment1).isFalse()
        Assertions.assertThat(Point(1,0) in segment1).isFalse()
        Assertions.assertThat(Point(11,-1) in segment1).isFalse()
        val segment2 = Segment(Point(2, 1), Point(3, 1))
        Assertions.assertThat(Point(2,1) in segment2).isTrue()
        Assertions.assertThat(Point(3,1) in segment2).isTrue()
        Assertions.assertThat(Point(1,2) in segment2).isFalse()
        Assertions.assertThat(Point(4,3) in segment2).isFalse()
        Assertions.assertThat(Point(0,0) in segment2).isFalse()
    }
}
