package de.linkel.aoc.utils.grid

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class RectangleTest {
    @Test
    fun `basic properties work`() {
        val rectangle = Rectangle(1, 2, 10, 5)
        Assertions.assertThat(rectangle.origin).isEqualTo(Point(1, 2))
        Assertions.assertThat(rectangle.northWest).isEqualTo(Point(1, 2))
        Assertions.assertThat(rectangle.northEast).isEqualTo(Point(10, 2))
        Assertions.assertThat(rectangle.southWest).isEqualTo(Point(1, 6))
        Assertions.assertThat(rectangle.southEast).isEqualTo(Point(10, 6))
        Assertions.assertThat(rectangle.dimension).isEqualTo(Dimension(10, 5))
        Assertions.assertThat(rectangle.area).isEqualTo(50)
    }

    @Test
    fun `negative dimension do not work`() {
        Assertions.assertThatThrownBy { Rectangle(1, 2, -1, 1) }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { Rectangle(1, 2, 1, -1) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `zero dimension do not work`() {
        Assertions.assertThatThrownBy { Rectangle(1, 2, 0, 1) }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { Rectangle(1, 2, 1, 0) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `can extend to into east`() {
        val rectangle = Rectangle(0, 0, 3, 3).extendTo(Point(5, 1))
        Assertions.assertThat(rectangle.origin).isEqualTo(Point(0, 0))
        Assertions.assertThat(rectangle.northWest).isEqualTo(Point(0, 0))
        Assertions.assertThat(rectangle.northEast).isEqualTo(Point(5, 0))
        Assertions.assertThat(rectangle.southWest).isEqualTo(Point(0, 2))
        Assertions.assertThat(rectangle.southEast).isEqualTo(Point(5, 2))
        Assertions.assertThat(rectangle.dimension).isEqualTo(Dimension(6, 3))
    }

    @Test
    fun `can extend to into west`() {
        val rectangle = Rectangle(0, 0, 3, 3).extendTo(Point(-1, 1))
        Assertions.assertThat(rectangle.origin).isEqualTo(Point(-1, 0))
        Assertions.assertThat(rectangle.northWest).isEqualTo(Point(-1, 0))
        Assertions.assertThat(rectangle.northEast).isEqualTo(Point(2, 0))
        Assertions.assertThat(rectangle.southWest).isEqualTo(Point(-1, 2))
        Assertions.assertThat(rectangle.southEast).isEqualTo(Point(2, 2))
        Assertions.assertThat(rectangle.dimension).isEqualTo(Dimension(4, 3))
    }

    @Test
    fun `can extend to into north`() {
        val rectangle = Rectangle(0, 0, 3, 3).extendTo(Point(0, -1))
        Assertions.assertThat(rectangle.origin).isEqualTo(Point(0, -1))
        Assertions.assertThat(rectangle.northWest).isEqualTo(Point(0, -1))
        Assertions.assertThat(rectangle.northEast).isEqualTo(Point(2, -1))
        Assertions.assertThat(rectangle.southWest).isEqualTo(Point(0, 2))
        Assertions.assertThat(rectangle.southEast).isEqualTo(Point(2, 2))
        Assertions.assertThat(rectangle.dimension).isEqualTo(Dimension(3, 4))
    }

    @Test
    fun `can extend to into south`() {
        val rectangle = Rectangle(0, 0, 3, 3).extendTo(Point(0, 3))
        Assertions.assertThat(rectangle.origin).isEqualTo(Point(0, 0))
        Assertions.assertThat(rectangle.northWest).isEqualTo(Point(0, 0))
        Assertions.assertThat(rectangle.northEast).isEqualTo(Point(2, 0))
        Assertions.assertThat(rectangle.southWest).isEqualTo(Point(0, 3))
        Assertions.assertThat(rectangle.southEast).isEqualTo(Point(2, 3))
        Assertions.assertThat(rectangle.dimension).isEqualTo(Dimension(3, 4))
    }

    @Test
    fun `can be moved`() {
        val rectangle = Rectangle(0, 0, 3, 3) + Vector(2, 1)
        Assertions.assertThat(rectangle.origin).isEqualTo(Point(2, 1))
        Assertions.assertThat(rectangle.northWest).isEqualTo(Point(2, 1))
        Assertions.assertThat(rectangle.northEast).isEqualTo(Point(4, 1))
        Assertions.assertThat(rectangle.southWest).isEqualTo(Point(2, 3))
        Assertions.assertThat(rectangle.southEast).isEqualTo(Point(4, 3))
        Assertions.assertThat(rectangle.dimension).isEqualTo(Dimension(3, 3))
        Assertions.assertThat(Rectangle(0, 0, 3, 3) - Vector(2, 1)).isEqualTo(Rectangle(-2, -1, 3, 3))
    }

    @Test
    fun `can be resized`() {
        Assertions.assertThat(Rectangle(1, 1, 3, 2) * 2).isEqualTo(Rectangle(1, 1, 6, 4))
    }

    @Test
    fun `inside check is correct`() {
        val rectangle = Rectangle(0, 0, 3, 3)
        Assertions.assertThat(Point(0,0) in rectangle).isTrue()
        Assertions.assertThat(Point(1,1) in rectangle).isTrue()
        Assertions.assertThat(Point(2,2) in rectangle).isTrue()
        Assertions.assertThat(Point(3,2) in rectangle).isFalse()
        Assertions.assertThat(Point(4,2) in rectangle).isFalse()
        Assertions.assertThat(Point(1,5) in rectangle).isFalse()
        Assertions.assertThat(Point(-1, 0) in rectangle).isFalse()
        Assertions.assertThat(Point(2, -5) in rectangle).isFalse()
    }

    @Test
    fun `sequences are correct`() {
        val rectangle = Rectangle(0, 0, 3, 2)
        Assertions.assertThat(rectangle.xValues.toList()).isEqualTo(listOf(0, 1, 2))
        Assertions.assertThat(rectangle.yValues.toList()).isEqualTo(listOf(0, 1))
        Assertions.assertThat(rectangle.points.toList()).isEqualTo(listOf(Point(0,0), Point(1,0), Point(2,0), Point(0,1), Point(1,1), Point(2,1)))
        Assertions.assertThat(rectangle.corners.toList()).isEqualTo(listOf(Point(0,0), Point(2,0), Point(2,1), Point(0,1)))
    }

    @Test
    fun `intersection works if areas are equal`() {
        val a = Rectangle(0, 0, 3, 3)
        Assertions.assertThat(a.intersects(a)).isTrue()
        Assertions.assertThat(a.intersect(a)).isEqualTo(a)
    }

    @Test
    fun `intersection works if areas are inside each other`() {
        val a = Rectangle(0, 0, 3, 3)
        val b = Rectangle(1, 1, 2, 1)
        Assertions.assertThat(a.intersects(b)).isTrue()
        Assertions.assertThat(a.intersect(b)).isEqualTo(b)
        Assertions.assertThat(b.intersect(a)).isEqualTo(b)
    }

    @Test
    fun `intersection works if areas overlap`() {
        val a = Rectangle(0, 0, 3, 3)
        val b = Rectangle(2, 1, 3, 3)
        val r = Rectangle(2, 1, 1, 2)
        Assertions.assertThat(a.intersects(b)).isTrue()
        Assertions.assertThat(a.intersect(b)).isEqualTo(r)
        Assertions.assertThat(b.intersect(a)).isEqualTo(r)
    }

    @Test
    fun `intersection fails if areas do not overlap`() {
        val a = Rectangle(0, 0, 3, 3)
        val b = Rectangle(5, 3, 3, 3)
        Assertions.assertThat(a.intersects(b)).isFalse()
        Assertions.assertThat(a.intersect(b)).isNull()
    }

    @Test
    fun `can be constructed by 2 points`() {
        val a = Point(-2, 4)
        val b = Point(6, -1)
        val r = Rectangle(-2, -1, 9, 6)
        Assertions.assertThat(Rectangle(a, b)).isEqualTo(r)
        Assertions.assertThat(Rectangle(b, a)).isEqualTo(r)
        Assertions.assertThat(Rectangle(Point(1, 1), Point(1, 1))).isEqualTo(Rectangle(1, 1, 1, 1))
    }

    @Test
    fun `can be constructed by point and dimension`() {
        val p = Point(-2, 4)
        val d = Dimension(9, 6)
        val r = Rectangle(-2, 4, 9, 6)
        Assertions.assertThat(Rectangle(p, d)).isEqualTo(r)
    }
}
