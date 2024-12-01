package de.linkel.aoc.utils.geometry.plain.discrete

import de.linkel.aoc.utils.geometry.plain.continuous.PointD
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class PointTests {

    @Test
    fun `test Point constructor`() {
        val point = Point(2, 3)
        Assertions.assertThat(point.x).isEqualTo(2)
        Assertions.assertThat(point.y).isEqualTo(3)
    }

    @Test
    fun `test Point equals method`() {
        val point1 = Point(2, 3)
        val point2 = Point(2, 3)
        val point3 = Point(3, 4)
        Assertions.assertThat(point1).isEqualTo(point2)
        Assertions.assertThat(point1).isNotEqualTo(point3)
    }
    
    @Test
    fun `test Point distance calculation`() {
        val point1 = Point(0, 0)
        val point2 = Point(3, 4)
        Assertions.assertThat(point1.rangeTo(point2).distance).isEqualTo(5.0)
    }

    @Test
    fun `test Point plus method`() {
        val point1 = Point(1, 2)
        Assertions.assertThat(point1 + Vector.NORTH).isEqualTo(Point(1, 1))
        Assertions.assertThat(point1 + Vector.SOUTH).isEqualTo(Point(1, 3))
        Assertions.assertThat(point1 + Vector.WEST).isEqualTo(Point(0, 2))
        Assertions.assertThat(point1 + Vector.EAST).isEqualTo(Point(2, 2))
        Assertions.assertThat(point1 + Vector.NORTH_WEST).isEqualTo(Point(0, 1))
        Assertions.assertThat(point1 + Vector.NORTH_EAST).isEqualTo(Point(2, 1))
        Assertions.assertThat(point1 + Vector.SOUTH_WEST).isEqualTo(Point(0, 3))
        Assertions.assertThat(point1 + Vector.SOUTH_EAST).isEqualTo(Point(2, 3))

        Assertions.assertThat(Point.ZERO + Vector.NORTH).isEqualTo(Point(0, -1))
        Assertions.assertThat(Point.ZERO + Vector.WEST).isEqualTo(Point(-1, 0))
    }

    @Test
    fun `test Point minus method`() {
        val point1 = Point(1, 2)
        Assertions.assertThat(point1 - Vector.NORTH).isEqualTo(Point(1, 3))
        Assertions.assertThat(point1 - Vector.SOUTH).isEqualTo(Point(1, 1))
        Assertions.assertThat(point1 - Vector.WEST).isEqualTo(Point(2, 2))
        Assertions.assertThat(point1 - Vector.EAST).isEqualTo(Point(0, 2))
        Assertions.assertThat(point1 - Vector.NORTH_WEST).isEqualTo(Point(2, 3))
        Assertions.assertThat(point1 - Vector.NORTH_EAST).isEqualTo(Point(0, 3))
        Assertions.assertThat(point1 - Vector.SOUTH_WEST).isEqualTo(Point(2, 1))
        Assertions.assertThat(point1 - Vector.SOUTH_EAST).isEqualTo(Point(0, 1))

        Assertions.assertThat(Point.ZERO - Vector.NORTH).isEqualTo(Point(0, 1))
        Assertions.assertThat(Point.ZERO - Vector.WEST).isEqualTo(Point(1, 0))
    }

    @Test
    fun `test Point vector generation`() {
        val point1 = Point(5, 7)
        val point2 = Point(3, 4)
        Assertions.assertThat(point1 - point2).isEqualTo(Vector(2, 3))
        Assertions.assertThat(point2 - point1).isEqualTo(Vector(-2, -3))
    }

    @Test
    fun `test Point rangeTo method`() {
        val start = Point(1, 1)
        val end = Point(3, 3)
        val range = start.rangeTo(end)
        Assertions.assertThat(range).containsExactly(Point(1, 1), Point(2, 2), Point(3, 3))
    }

    @Test
    fun `test Point toContinuous method`() {
        Assertions.assertThat(Point(3,2).toContinuous()).isEqualTo(PointD(3.0, 2.0))
    }

}