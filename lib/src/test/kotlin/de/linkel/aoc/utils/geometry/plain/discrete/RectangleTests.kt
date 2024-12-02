package de.linkel.aoc.utils.geometry.plain.discrete

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class RectangleTests {
    @Test
    fun `test Rectangle constructor`() {
        val rectangle = Rectangle(2, 3, 4, 5)
        Assertions.assertThat(rectangle.x).isEqualTo(2)
        Assertions.assertThat(rectangle.y).isEqualTo(3)
        Assertions.assertThat(rectangle.width).isEqualTo(4)
        Assertions.assertThat(rectangle.height).isEqualTo(5)
    }

    @Test
    fun `test Rectangle equals method`() {
        val rectangle1 = Rectangle(1, 0, 2, 3)
        val rectangle2 = Rectangle(1, 0, 2, 3)
        val rectangle3 = Rectangle(1, 0, 3, 4)
        val rectangle4 = Rectangle(1, 1, 2, 3)
        Assertions.assertThat(rectangle1).isEqualTo(rectangle2)
        Assertions.assertThat(rectangle1).isNotEqualTo(rectangle3)
        Assertions.assertThat(rectangle1).isNotEqualTo(rectangle4)
    }


    @Test
    fun `negative dimension do not work`() {
        Assertions.assertThatThrownBy { Rectangle(0, 0, -1, 1) }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { Rectangle(0, 0 ,1, -1) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `test Rectangle corners`() {
        val rectangle = Rectangle(2, 3, 4, 5)
        Assertions.assertThat(rectangle.origin).isEqualTo(Point(2, 3))
        Assertions.assertThat(rectangle.northWest).isEqualTo(Point(2, 3))
        Assertions.assertThat(rectangle.northEast).isEqualTo(Point(5, 3))
        Assertions.assertThat(rectangle.southWest).isEqualTo(Point(2, 7))
        Assertions.assertThat(rectangle.southEast).isEqualTo(Point(5, 7))
        Assertions.assertThat(rectangle.corners).containsExactly(rectangle.northWest, rectangle.northEast, rectangle.southEast, rectangle.southWest)
    }

    @Test
    fun `test Rectangle points`() {
        val rectangle = Rectangle(2, 3, 3, 3)
        val points = rectangle.points.toSet()
        Assertions.assertThat(points.size).isEqualTo(9)
        Assertions.assertThat(points).containsExactly(
            Point(2,3),
            Point(3,3),
            Point(4,3),
            Point(2,4),
            Point(3,4),
            Point(4,4),
            Point(2,5),
            Point(3,5),
            Point(4,5),
        )
    }

    @Test
    fun `test Rectangle xValues`() {
        val rectangle = Rectangle(2, 3, 3, 3)
        val points = rectangle.xValues.toSet()
        Assertions.assertThat(points.size).isEqualTo(3)
        Assertions.assertThat(points).containsExactly(2, 3, 4)
    }

    @Test
    fun `test Rectangle yValues`() {
        val rectangle = Rectangle(2, 3, 3, 3)
        val points = rectangle.yValues.toSet()
        Assertions.assertThat(points.size).isEqualTo(3)
        Assertions.assertThat(points).containsExactly(3, 4, 5)
    }

    @Test
    fun `test Rectangle dimension`() {
        val rectangle = Rectangle(2, 3, 4, 5)
        Assertions.assertThat(rectangle.dimension).isEqualTo(Dimension(4, 5))
        Assertions.assertThat(rectangle.area).isEqualTo(20)
    }

    @Test
    fun `test Rectangle boundingBox`() {
        val rectangle = Rectangle(2, 3, 4, 5)
        Assertions.assertThat(rectangle.boundingBox).isEqualTo(rectangle)
    }

    @Test
    fun `test Rectangle segments`() {
        val rectangle = Rectangle(2, 3, 4, 5)
        val segments = rectangle.segments.toSet()
        Assertions.assertThat(segments.size).isEqualTo(4)
        // it doesn't matter in which direction the segments are pointing, so check all options
        Assertions.assertThat(
            Segment(Point(2, 3), Vector(4, 0)) in segments
         || Segment(Point(5, 3), Vector(-4, 0)) in segments
        ).isTrue()
        Assertions.assertThat(
            Segment(Point(5, 3), Vector(0, 5)) in segments
         || Segment(Point(5, 7), Vector(0, -5)) in segments
        ).isTrue()
        Assertions.assertThat(
            Segment(Point(2, 7), Vector(4, 0)) in segments
         || Segment(Point(5, 7), Vector(-4, 0)) in segments
        ).isTrue()
        Assertions.assertThat(
            Segment(Point(2, 7), Vector(4, 0)) in segments
         || Segment(Point(5, 7), Vector(-4, 0)) in segments
        ).isTrue()
    }

    @Test
    fun `test Rectangle extendTo`() {
        Assertions.assertThat(Rectangle(2, 3, 4, 5).extendTo(Point.ZERO))
            .isEqualTo(Rectangle(0, 0, 6, 8))
        Assertions.assertThat(Rectangle(2, 3, 4, 5).extendTo(Point(2, 3)))
            .isEqualTo(Rectangle(2, 3, 4, 5))
        Assertions.assertThat(Rectangle(2, 3, 4, 5).extendTo(Point(3, 4)))
            .isEqualTo(Rectangle(2, 3, 4, 5))
        Assertions.assertThat(Rectangle(2, 3, 4, 5).extendTo(Point(10, 4)))
            .isEqualTo(Rectangle(2, 3, 9, 5))
        Assertions.assertThat(Rectangle(2, 3, 4, 5).extendTo(Point(3, 1)))
            .isEqualTo(Rectangle(2, 1, 4, 7))
    }

    @Test
    fun `test Rectangle moving`() {
        val rectangle1 = Rectangle(2, 3, 4, 5)
        Assertions.assertThat(rectangle1 + Vector.NORTH).isEqualTo(Rectangle(2, 2, 4, 5))
        Assertions.assertThat(rectangle1 + Vector.SOUTH_EAST).isEqualTo(Rectangle(3, 4, 4, 5))
        Assertions.assertThat(rectangle1 - Vector.NORTH_WEST).isEqualTo(Rectangle(3, 4, 4, 5))
        Assertions.assertThat(rectangle1 * 2).isEqualTo(Rectangle(2, 3, 8, 10))
    }

    @Test
    fun `test Rectangle to Rectangle geometry`() {
        val rectA = Rectangle(1, 1, 5, 4)
        val rectB = Rectangle(1, 1, 3, 2)
        val rectC = Rectangle(2, 2, 3, 2)
        val rectD = Rectangle(10, 1, 3, 2)
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
        Assertions.assertThat(rectB.intersect(rectC)).isEqualTo(Rectangle(2,2, 2, 1))
        Assertions.assertThat(rectC.intersect(rectB)).isEqualTo(Rectangle(2,2, 2, 1))
    }

    @Test
    fun `test Rectangle to Point geometry`() {
        val rectA = Rectangle(1, 1, 5, 4)
        val pointA = Point(1,1)
        val pointB = Point(2,2)
        val pointC = Point(0,1)
        Assertions.assertThat(pointA in rectA).isTrue()
        Assertions.assertThat(pointB in rectA).isTrue()
        Assertions.assertThat(pointC in rectA).isFalse()
    }

    @Test
    fun `test Rectangle to Shape geometry`() {
        val rectA = Rectangle(Point(1,1), Point(5, 5))
        val triangleA = Polygon(listOf(Point(1,1), Point(3, 1), Point(2, 3)))
        val triangleB = Polygon(listOf(Point(2,2), Point(4, 2), Point(3, 4)))
        val triangleC = Polygon(listOf(Point(2,2), Point(8, 2), Point(3, 4)))
        val triangleD = Polygon(listOf(Point(6,2), Point(8, 2), Point(7, 4)))
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
        val rect = Rectangle(1, 1, 3, 2, "my-name")
        val moved = rect.plus(Vector.NORTH).minus(Vector.EAST).times(2).extendTo(Point.ZERO)
        Assertions.assertThat(moved.name).isEqualTo("my-name")
        val poly = rect.toPolygon()
        Assertions.assertThat(poly.name).isEqualTo("my-name")
    }

    @Test
    fun `name can be removed`() {
        val rect = Rectangle(1, 1, 3, 2, "my-name")
        Assertions.assertThat(rect.toAnonymous().name).isEqualTo("")
    }

    @Test
    fun `name can be set`() {
        val rect = Rectangle(1, 1, 3, 2, "my-name")
        Assertions.assertThat(rect.named("foo").name).isEqualTo("foo")
    }
}