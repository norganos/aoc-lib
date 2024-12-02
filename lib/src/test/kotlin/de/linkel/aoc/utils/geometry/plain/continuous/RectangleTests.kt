package de.linkel.aoc.utils.geometry.plain.continuous

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class RectangleTests {
    @Test
    fun `test RectangleD constructor`() {
        val rectangle = RectangleD(2.0,3.0,4.0,5.0)
        Assertions.assertThat(rectangle.x).isEqualTo(2.0)
        Assertions.assertThat(rectangle.y).isEqualTo(3.0)
        Assertions.assertThat(rectangle.width).isEqualTo(4.0)
        Assertions.assertThat(rectangle.height).isEqualTo(5.0)
    }

    @Test
    fun `test RectangleD equals method`() {
        val rectangle1 = RectangleD(1.0,0.0,2.0,3.0)
        val rectangle2 = RectangleD(1.0,0.0,2.0,3.0)
        val rectangle3 = RectangleD(1.0,0.0,3.0,4.0)
        val rectangle4 = RectangleD(1.0,1.0,2.0,3.0)
        Assertions.assertThat(rectangle1).isEqualTo(rectangle2)
        Assertions.assertThat(rectangle1).isNotEqualTo(rectangle3)
        Assertions.assertThat(rectangle1).isNotEqualTo(rectangle4)
    }

    @Test
    fun `negative or zero dimension do not work`() {
        Assertions.assertThatThrownBy { RectangleD(0.0,0.0,-1.0,1.0) }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { RectangleD(0.0,0.0,1.0,-1.0) }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { RectangleD(0.0,0.0,0.0,1.0) }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { RectangleD(0.0,0.0,1.0,0.0) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `test RectangleD corners`() {
        val rectangle = RectangleD(2.0,3.0,4.0,5.0)
        Assertions.assertThat(rectangle.origin).isEqualTo(PointD(2.0,3.0))
        Assertions.assertThat(rectangle.northWest).isEqualTo(PointD(2.0,3.0))
        Assertions.assertThat(rectangle.northEast).isEqualTo(PointD(5.0,3.0))
        Assertions.assertThat(rectangle.southWest).isEqualTo(PointD(2.0,7.0))
        Assertions.assertThat(rectangle.southEast).isEqualTo(PointD(5.0,7.0))
        Assertions.assertThat(rectangle.corners).containsExactly(rectangle.northWest, rectangle.northEast, rectangle.southEast, rectangle.southWest)
    }

    @Test
    fun `test RectangleD dimension`() {
        val rectangle = RectangleD(2.0,3.0,4.0,5.0)
        Assertions.assertThat(rectangle.dimension).isEqualTo(DimensionD(4.0, 5.0))
        Assertions.assertThat(rectangle.area).isEqualTo(20.0)
    }

    @Test
    fun `test RectangleD boundingBox`() {
        val rectangle = RectangleD(2.0,3.0,4.0,5.0)
        Assertions.assertThat(rectangle.boundingBox).isEqualTo(rectangle)
    }

    @Test
    fun `test RectangleD segments`() {
        val rectangle = RectangleD(2.0,3.0,4.0,5.0)
        val segments = rectangle.segments.toSet()
        Assertions.assertThat(segments.size).isEqualTo(4)
        // it doesn't matter in which direction the segments are pointing, so check all options
        Assertions.assertThat(
            SegmentD(PointD(2.0,3.0), VectorD(4.0,0.0)) in segments
         || SegmentD(PointD(5.0,3.0), VectorD(-4.0,0.0)) in segments
        ).isTrue()
        Assertions.assertThat(
            SegmentD(PointD(5.0,3.0), VectorD(0.0,5.0)) in segments
         || SegmentD(PointD(5.0,7.0), VectorD(0.0,-5.0)) in segments
        ).isTrue()
        Assertions.assertThat(
            SegmentD(PointD(2.0,7.0), VectorD(4.0,0.0)) in segments
         || SegmentD(PointD(5.0,7.0), VectorD(-4.0,0.0)) in segments
        ).isTrue()
        Assertions.assertThat(
            SegmentD(PointD(2.0,7.0), VectorD(4.0,0.0)) in segments
         || SegmentD(PointD(5.0,7.0), VectorD(-4.0,0.0)) in segments
        ).isTrue()
    }

    @Test
    fun `test RectangleD extendTo`() {
        Assertions.assertThat(RectangleD(2.0,3.0,4.0,5.0).extendTo(PointD.ZERO))
            .isEqualTo(RectangleD(0.0,0.0,6.0,8.0))
        Assertions.assertThat(RectangleD(2.0,3.0,4.0,5.0).extendTo(PointD(2.0,3.0)))
            .isEqualTo(RectangleD(2.0,3.0,4.0,5.0))
        Assertions.assertThat(RectangleD(2.0,3.0,4.0,5.0).extendTo(PointD(3.0,4.0)))
            .isEqualTo(RectangleD(2.0,3.0,4.0,5.0))
        Assertions.assertThat(RectangleD(2.0,3.0,4.0,5.0).extendTo(PointD(10.0,4.0)))
            .isEqualTo(RectangleD(2.0,3.0,9.0,5.0))
        Assertions.assertThat(RectangleD(2.0,3.0,4.0,5.0).extendTo(PointD(3.0,1.0)))
            .isEqualTo(RectangleD(2.0,1.0,4.0,7.0))
    }

    @Test
    fun `test RectangleD moving`() {
        val rectangle1 = RectangleD(2.0,3.0,4.0,5.0)
        Assertions.assertThat(rectangle1 + VectorD.NORTH).isEqualTo(RectangleD(2.0,2.0,4.0,5.0))
        Assertions.assertThat(rectangle1 + VectorD.SOUTH_EAST).isEqualTo(RectangleD(3.0,4.0,4.0,5.0))
        Assertions.assertThat(rectangle1 - VectorD.NORTH_WEST).isEqualTo(RectangleD(3.0,4.0,4.0,5.0))
        Assertions.assertThat(rectangle1 * 2.0).isEqualTo(RectangleD(2.0,3.0,8.0,10.0))
    }

    @Test
    fun `test RectangleD to RectangleD geometry`() {
        val rectA = RectangleD(1.0,1.0,5.0,4.0)
        val rectB = RectangleD(1.0,1.0,3.0,2.0)
        val rectC = RectangleD(2.0,2.0,3.0,2.0)
        val rectD = RectangleD(10.0,1.0,3.0,2.0)
        Assertions.assertThat(rectC in rectA).isTrue()
        Assertions.assertThat(rectB in rectA).isTrue()
        Assertions.assertThat(rectA in rectC).isFalse()
        Assertions.assertThat(rectA in rectB).isFalse()
        Assertions.assertThat(rectD in rectA).isFalse()
        Assertions.assertThat(rectC in rectB).isFalse()
        Assertions.assertThat(rectB in rectC).isFalse()
        Assertions.assertThat(rectA in rectA).isTrue()

        Assertions.assertThat(rectA in rectD).isFalse()
        Assertions.assertThat(rectB in rectD).isFalse()
        Assertions.assertThat(rectC in rectD).isFalse()

        Assertions.assertThat(rectA.intersects(rectB)).isTrue()
        Assertions.assertThat(rectA.intersects(rectC)).isTrue()
        Assertions.assertThat(rectA.intersects(rectA)).isTrue()
        Assertions.assertThat(rectB.intersects(rectC)).isTrue()
        Assertions.assertThat(rectC.intersects(rectB)).isTrue()
        Assertions.assertThat(rectA.intersects(rectD)).isFalse()
        Assertions.assertThat(rectD.intersects(rectA)).isFalse()

        Assertions.assertThat(rectB.intersect(rectB)).isEqualTo(rectB)
        Assertions.assertThat(rectB.intersect(rectC)).isEqualTo(RectangleD(2.0,2.0,2.0,1.0))
        Assertions.assertThat(rectC.intersect(rectB)).isEqualTo(RectangleD(2.0,2.0,2.0,1.0))
    }

    @Test
    fun `test RectangleD to PointD geometry`() {
        val rectA = RectangleD(1.0,1.0,5.0,4.0)
        val pointA = PointD(1.0,1.0)
        val pointB = PointD(2.0,2.0)
        val pointC = PointD(0.0,1.0)
        Assertions.assertThat(pointA in rectA).isTrue()
        Assertions.assertThat(pointB in rectA).isTrue()
        Assertions.assertThat(pointC in rectA).isFalse()
    }

    @Test
    fun `test RectangleD to Shape geometry`() {
        val rectA = RectangleD(PointD(1.0,1.0), PointD(5.0,5.0))
        val triangleA = PolygonD(listOf(PointD(1.0,1.0), PointD(3.0,1.0), PointD(2.0,3.0)))
        val triangleB = PolygonD(listOf(PointD(2.0,2.0), PointD(4.0,2.0), PointD(3.0,4.0)))
        val triangleC = PolygonD(listOf(PointD(2.0,2.0), PointD(8.0,2.0), PointD(3.0,4.0)))
        val triangleD = PolygonD(listOf(PointD(6.0,2.0), PointD(8.0,2.0), PointD(7.0,4.0)))
        Assertions.assertThat(triangleA in rectA).isTrue()
        Assertions.assertThat(triangleB in rectA).isTrue()
        Assertions.assertThat(triangleC in rectA).isFalse()
        Assertions.assertThat(triangleD in rectA).isFalse()
        Assertions.assertThat(rectA.intersects(triangleA)).isTrue()
        Assertions.assertThat(rectA.intersects(triangleB)).isTrue()
        Assertions.assertThat(rectA.intersects(triangleC)).isTrue()
        Assertions.assertThat(rectA.intersects(triangleD)).isFalse()
    }

    @Test
    fun `moving and scaling preserves name`() {
        val rect = RectangleD(1.0, 1.0, 3.0, 2.0, "my-name")
        val moved = rect.plus(VectorD.NORTH).minus(VectorD.EAST).times(2.0).extendTo(PointD.ZERO)
        Assertions.assertThat(moved.name).isEqualTo("my-name")
        val poly = rect.toPolygon()
        Assertions.assertThat(poly.name).isEqualTo("my-name")
    }

    @Test
    fun `name can be removed`() {
        val rect = RectangleD(1.0, 1.0, 3.0, 2.0, "my-name")
        Assertions.assertThat(rect.toAnonymous().name).isEqualTo("")
    }

    @Test
    fun `name can be set`() {
        val rect = RectangleD(1.0, 1.0, 3.0, 2.0, "my-name")
        Assertions.assertThat(rect.named("foo").name).isEqualTo("foo")
    }
}