package de.linkel.aoc.utils.grid

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class AreaTest {
    @Test
    fun `basic properties work`() {
        val area = Area(1, 2, 10, 5)
        Assertions.assertThat(area.origin).isEqualTo(Point(1, 2))
        Assertions.assertThat(area.northWest).isEqualTo(Point(1, 2))
        Assertions.assertThat(area.northEast).isEqualTo(Point(10, 2))
        Assertions.assertThat(area.southWest).isEqualTo(Point(1, 6))
        Assertions.assertThat(area.southEast).isEqualTo(Point(10, 6))
        Assertions.assertThat(area.dimension).isEqualTo(Dimension(10, 5))
    }

    @Test
    fun `can extend to into east`() {
        val area = Area(0, 0, 3, 3).extendTo(Point(5, 1))
        Assertions.assertThat(area.origin).isEqualTo(Point(0, 0))
        Assertions.assertThat(area.northWest).isEqualTo(Point(0, 0))
        Assertions.assertThat(area.northEast).isEqualTo(Point(5, 0))
        Assertions.assertThat(area.southWest).isEqualTo(Point(0, 2))
        Assertions.assertThat(area.southEast).isEqualTo(Point(5, 2))
        Assertions.assertThat(area.dimension).isEqualTo(Dimension(6, 3))
    }

    @Test
    fun `can extend to into west`() {
        val area = Area(0, 0, 3, 3).extendTo(Point(-1, 1))
        Assertions.assertThat(area.origin).isEqualTo(Point(-1, 0))
        Assertions.assertThat(area.northWest).isEqualTo(Point(-1, 0))
        Assertions.assertThat(area.northEast).isEqualTo(Point(2, 0))
        Assertions.assertThat(area.southWest).isEqualTo(Point(-1, 2))
        Assertions.assertThat(area.southEast).isEqualTo(Point(2, 2))
        Assertions.assertThat(area.dimension).isEqualTo(Dimension(4, 3))
    }

    @Test
    fun `can extend to into north`() {
        val area = Area(0, 0, 3, 3).extendTo(Point(0, -1))
        Assertions.assertThat(area.origin).isEqualTo(Point(0, -1))
        Assertions.assertThat(area.northWest).isEqualTo(Point(0, -1))
        Assertions.assertThat(area.northEast).isEqualTo(Point(2, -1))
        Assertions.assertThat(area.southWest).isEqualTo(Point(0, 2))
        Assertions.assertThat(area.southEast).isEqualTo(Point(2, 2))
        Assertions.assertThat(area.dimension).isEqualTo(Dimension(3, 4))
    }

    @Test
    fun `can extend to into south`() {
        val area = Area(0, 0, 3, 3).extendTo(Point(0, 3))
        Assertions.assertThat(area.origin).isEqualTo(Point(0, 0))
        Assertions.assertThat(area.northWest).isEqualTo(Point(0, 0))
        Assertions.assertThat(area.northEast).isEqualTo(Point(2, 0))
        Assertions.assertThat(area.southWest).isEqualTo(Point(0, 3))
        Assertions.assertThat(area.southEast).isEqualTo(Point(2, 3))
        Assertions.assertThat(area.dimension).isEqualTo(Dimension(3, 4))
    }

    @Test
    fun `can be moved`() {
        val area = Area(0, 0, 3, 3).plus(Vector(2, 1))
        Assertions.assertThat(area.origin).isEqualTo(Point(2, 1))
        Assertions.assertThat(area.northWest).isEqualTo(Point(2, 1))
        Assertions.assertThat(area.northEast).isEqualTo(Point(4, 1))
        Assertions.assertThat(area.southWest).isEqualTo(Point(2, 3))
        Assertions.assertThat(area.southEast).isEqualTo(Point(4, 3))
        Assertions.assertThat(area.dimension).isEqualTo(Dimension(3, 3))
    }
}
