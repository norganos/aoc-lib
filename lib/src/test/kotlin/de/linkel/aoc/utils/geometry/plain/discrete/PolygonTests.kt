package de.linkel.aoc.utils.geometry.plain.discrete

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class PolygonTests {
    @Test
    fun `test Polygon equals method`() {
        val polygon1 = Polygon(listOf(Point(1,1), Point(4,1), Point(1,5)), "p1")
        val polygon2 = Polygon(listOf(Point(1,1), Point(4,1), Point(1,5)), "p2")
        val polygon3 = Polygon(listOf(Point(1,0), Point(4,1), Point(1,5)), "p3")
        val polygon4 = Polygon(listOf(Point(1,5), Point(1,1), Point(4,1)), "p4")
        val polygon5 = Polygon(listOf(Point(1,5), Point(4,1), Point(1,1)), "p5")
        Assertions.assertThat(polygon1).isEqualTo(polygon2)
        Assertions.assertThat(polygon1).isEqualTo(polygon4)
        Assertions.assertThat(polygon1).isEqualTo(polygon5)
        Assertions.assertThat(polygon1).isNotEqualTo(polygon3)
        Assertions.assertThat(polygon1).isNotEqualTo(polygon3)
        /*
                    x---x    x\ /x
                    | . |    | v |
                    x/ \x    x---x
         */
        val quintuple1 = Polygon(listOf(Point(1,1), Point(5,1), Point(5,3), Point(3,2), Point(1, 3)))
        val quintuple1a = Polygon(listOf(Point(3,2), Point(1, 3), Point(1,1), Point(5,1), Point(5,3)))
        val quintuple2 = Polygon(listOf(Point(1,1), Point(3,2), Point(5,1), Point(5,3), Point(1, 3)))
        Assertions.assertThat(quintuple1).isEqualTo(quintuple1a)
        Assertions.assertThat(quintuple1).isNotEqualTo(quintuple2)
    }

    @Test
    fun `test Polygon hash conflicts`() {
        val quintuple1 = Polygon(listOf(Point(1,1), Point(5,1), Point(5,3), Point(3,2), Point(1, 3)))
        val quintuple1a = Polygon(listOf(Point(3,2), Point(1, 3), Point(1,1), Point(5,1), Point(5,3)))
        val quintuple2 = Polygon(listOf(Point(1,1), Point(3,2), Point(5,1), Point(5,3), Point(1, 3)))
        val set = setOf(quintuple1)
        Assertions.assertThat(set).contains(quintuple1)
        Assertions.assertThat(set).contains(quintuple1a)
        Assertions.assertThat(set).doesNotContain(quintuple2)
        Assertions.assertThat(quintuple1).isEqualTo(quintuple1a)
        Assertions.assertThat(quintuple1.hashCode()).isEqualTo(quintuple1a.hashCode())
        Assertions.assertThat(quintuple1.hashCode()).isEqualTo(quintuple2.hashCode())
        Assertions.assertThat(setOf(quintuple1, quintuple1a)).hasSize(1)
    }

    @Test
    fun `test Polygon corners`() {
        val polygon = Polygon(listOf(Point(1,1), Point(4,1), Point(1,5)))
        Assertions.assertThat(polygon.corners.toSet()).containsExactly(Point(1,1), Point(4,1), Point(1,5))
    }

    @Test
    fun `test Polygon boundingBox`() {
        val polygon = Polygon(listOf(Point(1,1), Point(4,1), Point(1,5)))
        Assertions.assertThat(polygon.boundingBox).isEqualTo(Rectangle(1, 1, 4, 5))
    }

    @Test
    fun `test Polygon area`() {
        /*
         ###        ###
         ##  = 6    ### = 8
         #          ##
         */
        Assertions.assertThat(
            Polygon(
                listOf(
                    Point(0,0),
                    Point(2,0),
                    Point(0,2),
                )
            ).area
        ).isEqualTo(6)
        Assertions.assertThat(
            Polygon(
                listOf(
                    Point(0,0),
                    Point(2,0),
                    Point(2,1),
                    Point(1,1),
                    Point(1,2),
                    Point(0,2),
                )
            ).area
        ).isEqualTo(8)
    }

    @Test
    fun `test Polygon segments`() {
        val polygon = Polygon(listOf(Point(1,1), Point(4,1), Point(1,5)))
        val segments = polygon.segments.toSet()
        Assertions.assertThat(segments.size).isEqualTo(3)
        // it doesn't matter in which direction the segments are pointing, so check all options
        Assertions.assertThat(
            Segment(Point(1, 1), Vector(3, 0)) in segments
         || Segment(Point(4, 1), Vector(-3, 0)) in segments
        ).isTrue()
        Assertions.assertThat(
            Segment(Point(1, 1), Vector(0, 4)) in segments
         || Segment(Point(1, 5), Vector(0, -4)) in segments
        ).isTrue()
        Assertions.assertThat(
            Segment(Point(4, 1), Vector(-3, 4)) in segments
         || Segment(Point(1, 5), Vector(3, -4)) in segments
        ).isTrue()
    }

    @Test
    fun `test Polygon moving`() {
        val polygon = Polygon(listOf(Point(1,1), Point(4,1), Point(1,5)))
        Assertions.assertThat((polygon + Vector.NORTH).boundingBox).isEqualTo(Rectangle(1, 0, 4, 5))
        Assertions.assertThat((polygon + Vector.SOUTH_EAST).boundingBox).isEqualTo(Rectangle(2, 2, 4, 5))
        Assertions.assertThat((polygon + Vector.NORTH_WEST).boundingBox).isEqualTo(Rectangle(0, 0, 4, 5))
    }

    @Test
    fun `test Polygon to Point geometry`() {
        val polygon1 = Polygon(listOf(Point(1,1), Point(4,1), Point(1,5)))
        val pointA = Point(1,1)
        val pointB = Point(2,2)
        val pointC = Point(3,3)
        Assertions.assertThat(pointA in polygon1).isTrue()
        Assertions.assertThat(pointB in polygon1).isTrue()
        Assertions.assertThat(pointC in polygon1).isFalse()
        /*
        ##1#
        #2#
        ##
        # 3
         */
        val polygon2 = Polygon(listOf(Point(0,0), Point(3, 0), Point(0, 3)))
        Assertions.assertThat(Point(2, 0) in polygon2).isTrue()
        Assertions.assertThat(Point(1, 1) in polygon2).isTrue()
        Assertions.assertThat(Point(2, 3) in polygon2).isFalse()

    }

    @Test
    fun `test Rectangle to Shape geometry`() {
        val polygon1 = Polygon(listOf(Point(1,1), Point(4,1), Point(1,5)))
        val polygon2 = Polygon(listOf(Point(1,1), Point(3,1), Point(1,4)))
        val polygon3 = Polygon(listOf(Point(0,0), Point(6,0), Point(0,7)))
        val polygon4 = Polygon(listOf(Point(2,2), Point(4,3), Point(4,0)))
        val polygon5 = Polygon(listOf(Point(3,3), Point(4,3), Point(3,4)))
        val rect = Rectangle(2, 2, 2, 2)
        Assertions.assertThat(polygon2.intersects(polygon1)).isTrue()
        Assertions.assertThat(polygon3.intersects(polygon1)).isTrue()
        Assertions.assertThat(polygon4.intersects(polygon1)).isTrue()
        Assertions.assertThat(polygon5.intersects(polygon1)).isFalse()
        Assertions.assertThat(polygon4.intersects(polygon3)).isTrue()
        Assertions.assertThat(polygon1.intersects(rect)).isTrue()
    }

    @Test
    fun `moving and scaling preserves name`() {
        val polygon = Polygon(listOf(Point(1,1), Point(4,1), Point(1,5)), "my-name")
        val moved = polygon.plus(Vector.NORTH).minus(Vector.EAST)
        Assertions.assertThat(moved.name).isEqualTo("my-name")
    }

    @Test
    fun `name can be removed`() {
        val polygon = Polygon(listOf(Point(1,1), Point(4,1), Point(1,5)), "my-name")
        Assertions.assertThat(polygon.toAnonymous().name).isEqualTo("")
    }

    @Test
    fun `test Polygon isWellFormed`() {
        val polygon1 = Polygon(listOf(Point(1,1), Point(4,1), Point(1,5)))
        val polygon2 = Polygon(listOf(Point(1,1), Point(4,1), Point(4,4), Point(1,4)))
        val polygon3 = Polygon(listOf(Point(1,1), Point(4,4), Point(4,1), Point(1,4)))
        Assertions.assertThat(polygon1.isWellFormed()).isTrue()
        Assertions.assertThat(polygon2.isWellFormed()).isTrue()
        Assertions.assertThat(polygon3.isWellFormed()).isFalse()
    }

    @Test
    fun `test Polygon isConcave`() {
        val polygon1 = Polygon(listOf(Point(1,1), Point(4,1), Point(1,5)))
        val polygon2 = Polygon(listOf(Point(1,1), Point(4,1), Point(4,4), Point(1,4)))
        val polygon3 = Polygon(listOf(Point(1,1), Point(4,1), Point(2,2), Point(1,4)))
        Assertions.assertThat(polygon1.isConcave()).isTrue()
        Assertions.assertThat(polygon2.isConcave()).isTrue()
        Assertions.assertThat(polygon3.isConcave()).isFalse()
    }
}