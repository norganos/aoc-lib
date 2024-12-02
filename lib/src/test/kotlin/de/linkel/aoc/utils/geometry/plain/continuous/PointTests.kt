package de.linkel.aoc.utils.geometry.plain.continuous

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class PointTests {

    @Test
    fun `test PointD constructor`() {
        val point = PointD(2.0, 3.0)
        Assertions.assertThat(point.x).isEqualTo(2.0)
        Assertions.assertThat(point.y).isEqualTo(3.0)
    }

    @Test
    fun `test PointD equals method`() {
        val point1 = PointD(2.0, 3.0)
        val point2 = PointD(2.0, 3.0)
        val point3 = PointD(3.0, 4.0)
        Assertions.assertThat(point1).isEqualTo(point2)
        Assertions.assertThat(point1).isNotEqualTo(point3)
    }
    
    @Test
    fun `test PointD distance calculation`() {
        val point1 = PointD(0.0, 0.0)
        val point2 = PointD(3.0, 4.0)
        Assertions.assertThat(point1.rangeTo(point2).length).isEqualTo(5.0)
    }

    @Test
    fun `test PointD plus method`() {
        val point1 = PointD(1.0, 2.0)
        Assertions.assertThat(point1 + VectorD.NORTH).isEqualTo(PointD(1.0, 1.0))
        Assertions.assertThat(point1 + VectorD.SOUTH).isEqualTo(PointD(1.0, 3.0))
        Assertions.assertThat(point1 + VectorD.WEST).isEqualTo(PointD(0.0, 2.0))
        Assertions.assertThat(point1 + VectorD.EAST).isEqualTo(PointD(2.0, 2.0))
        Assertions.assertThat(point1 + VectorD.NORTH_WEST).isEqualTo(PointD(0.0, 1.0))
        Assertions.assertThat(point1 + VectorD.NORTH_EAST).isEqualTo(PointD(2.0, 1.0))
        Assertions.assertThat(point1 + VectorD.SOUTH_WEST).isEqualTo(PointD(0.0, 3.0))
        Assertions.assertThat(point1 + VectorD.SOUTH_EAST).isEqualTo(PointD(2.0, 3.0))

        Assertions.assertThat(PointD.ZERO + VectorD.NORTH).isEqualTo(PointD(0.0, -1.0))
        Assertions.assertThat(PointD.ZERO + VectorD.WEST).isEqualTo(PointD(-1.0, 0.0))
    }

    @Test
    fun `test PointD minus method`() {
        val point1 = PointD(1.0, 2.0)
        Assertions.assertThat(point1 - VectorD.NORTH).isEqualTo(PointD(1.0, 3.0))
        Assertions.assertThat(point1 - VectorD.SOUTH).isEqualTo(PointD(1.0, 1.0))
        Assertions.assertThat(point1 - VectorD.WEST).isEqualTo(PointD(2.0, 2.0))
        Assertions.assertThat(point1 - VectorD.EAST).isEqualTo(PointD(0.0, 2.0))
        Assertions.assertThat(point1 - VectorD.NORTH_WEST).isEqualTo(PointD(2.0, 3.0))
        Assertions.assertThat(point1 - VectorD.NORTH_EAST).isEqualTo(PointD(0.0, 3.0))
        Assertions.assertThat(point1 - VectorD.SOUTH_WEST).isEqualTo(PointD(2.0, 1.0))
        Assertions.assertThat(point1 - VectorD.SOUTH_EAST).isEqualTo(PointD(0.0, 1.0))

        Assertions.assertThat(PointD.ZERO - VectorD.NORTH).isEqualTo(PointD(0.0, 1.0))
        Assertions.assertThat(PointD.ZERO - VectorD.WEST).isEqualTo(PointD(1.0, 0.0))
    }

    @Test
    fun `test PointD vector generation`() {
        val point1 = PointD(5.0, 7.0)
        val point2 = PointD(3.0, 4.0)
        Assertions.assertThat(point1 - point2).isEqualTo(VectorD(2.0, 3.0))
        Assertions.assertThat(point2 - point1).isEqualTo(VectorD(-2.0, -3.0))
    }

    @Test
    fun `test PointD rangeTo method`() {
        val start = PointD(1.0, 1.0)
        val end = PointD(3.0, 3.0)
        val range = start.rangeTo(end)
        Assertions.assertThat(range.start).isEqualTo(start)
        Assertions.assertThat(range.end).isEqualTo(end)
    }

    @Test
    fun `moving and scaling preserves name`() {
        val point = PointD(3.0, 3.0, "my-name")
        val moved = point.plus(VectorD.NORTH).minus(VectorD.EAST).times(2.0)
        Assertions.assertThat(moved.name).isEqualTo("my-name")
    }

    @Test
    fun `name can be removed`() {
        val point = PointD(3.0, 3.0, "my-name")
        Assertions.assertThat(point.toAnonymous().name).isEqualTo("")
    }

    @Test
    fun `name can be set`() {
        val point = PointD(3.0, 3.0, "my-name")
        Assertions.assertThat(point.named("foo").name).isEqualTo("foo")
    }
}