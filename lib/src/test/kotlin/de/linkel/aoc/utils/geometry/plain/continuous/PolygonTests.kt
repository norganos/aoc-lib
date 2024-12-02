package de.linkel.aoc.utils.geometry.plain.continuous

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class PolygonTests {
    @Test
    fun `test PolygonD equals method`() {
        val polygon1 = PolygonD(listOf(PointD(1.0,1.0), PointD(4.0,1.0), PointD(1.0,5.0)), "p1")
        val polygon2 = PolygonD(listOf(PointD(1.0,1.0), PointD(4.0,1.0), PointD(1.0,5.0)), "p2")
        val polygon3 = PolygonD(listOf(PointD(1.0,0.0), PointD(4.0,1.0), PointD(1.0,5.0)), "p3")
        val polygon4 = PolygonD(listOf(PointD(1.0,5.0), PointD(1.0,1.0), PointD(4.0,1.0)), "p4")
        val polygon5 = PolygonD(listOf(PointD(1.0,5.0), PointD(4.0,1.0), PointD(1.0,1.0)), "p5")
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
        val quintuple1 = PolygonD(listOf(PointD(1.0,1.0), PointD(5.0,1.0), PointD(5.0,3.0), PointD(3.0,2.0), PointD(1.0,3.0)))
        val quintuple1a = PolygonD(listOf(PointD(3.0,2.0), PointD(1.0,3.0), PointD(1.0,1.0), PointD(5.0,1.0), PointD(5.0,3.0)))
        val quintuple2 = PolygonD(listOf(PointD(1.0,1.0), PointD(3.0,2.0), PointD(5.0,1.0), PointD(5.0,3.0), PointD(1.0,3.0)))
        Assertions.assertThat(quintuple1).isEqualTo(quintuple1a)
        Assertions.assertThat(quintuple1).isNotEqualTo(quintuple2)
    }

    @Test
    fun `test PolygonD hash conflicts`() {
        val quintuple1 = PolygonD(listOf(PointD(1.0,1.0), PointD(5.0,1.0), PointD(5.0,3.0), PointD(3.0,2.0), PointD(1.0,3.0)))
        val quintuple1a = PolygonD(listOf(PointD(3.0,2.0), PointD(1.0,3.0), PointD(1.0,1.0), PointD(5.0,1.0), PointD(5.0,3.0)))
        val quintuple2 = PolygonD(listOf(PointD(1.0,1.0), PointD(3.0,2.0), PointD(5.0,1.0), PointD(5.0,3.0), PointD(1.0,3.0)))
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
    fun `test PolygonD corners`() {
        val polygon = PolygonD(listOf(PointD(1.0,1.0), PointD(4.0,1.0), PointD(1.0,5.0)))
        Assertions.assertThat(polygon.corners.toSet()).containsExactly(PointD(1.0,1.0), PointD(4.0,1.0), PointD(1.0,5.0))
    }

    @Test
    fun `test PolygonD boundingBox`() {
        val polygon = PolygonD(listOf(PointD(1.0,1.0), PointD(4.0,1.0), PointD(1.0,5.0)))
        Assertions.assertThat(polygon.boundingBox).isEqualTo(RectangleD(1.0, 1.0, 3.0, 4.0))
    }

    @Test
    fun `test PolygonD area`() {
        Assertions.assertThat(
            PolygonD(
                listOf(
                    PointD(0.0,0.0),
                    PointD(2.0,0.0),
                    PointD(0.0,3.0),
                )
            ).area
        ).isEqualTo(3.0)
        Assertions.assertThat(
            PolygonD(
                listOf(
                    PointD(0.0,0.0),
                    PointD(2.0,0.0),
                    PointD(2.0,1.0),
                    PointD(1.0,1.0),
                    PointD(1.0,2.0),
                    PointD(0.0,2.0),
                )
            ).area
        ).isEqualTo(3.0)
    }

    @Test
    fun `test PolygonD segments`() {
        val polygon = PolygonD(listOf(PointD(1.0,1.0), PointD(4.0,1.0), PointD(1.0,5.0)))
        val segments = polygon.segments.toSet()
        Assertions.assertThat(segments.size).isEqualTo(3)
        // it doesn't matter in which direction the segments are pointing, so check all options
        Assertions.assertThat(
            SegmentD(PointD(1.0,1.0), VectorD(3.0, 0.0)) in segments
         || SegmentD(PointD(4.0,1.0), VectorD(-3.0, 0.0)) in segments
        ).isTrue()
        Assertions.assertThat(
            SegmentD(PointD(1.0,1.0), VectorD(0.0, 4.0)) in segments
         || SegmentD(PointD(1.0,5.0), VectorD(0.0, -4.0)) in segments
        ).isTrue()
        Assertions.assertThat(
            SegmentD(PointD(4.0,1.0), VectorD(-3.0, 4.0)) in segments
         || SegmentD(PointD(1.0,5.0), VectorD(3.0, -4.0)) in segments
        ).isTrue()
    }

    @Test
    fun `test PolygonD moving`() {
        val polygon = PolygonD(listOf(PointD(1.0,1.0), PointD(4.0,1.0), PointD(1.0,5.0)))
        Assertions.assertThat((polygon + VectorD.NORTH).boundingBox).isEqualTo(RectangleD(1.0, 0.0, 3.0, 4.0))
        Assertions.assertThat((polygon + VectorD.SOUTH_EAST).boundingBox).isEqualTo(RectangleD(2.0, 2.0, 3.0, 4.0))
        Assertions.assertThat((polygon + VectorD.NORTH_WEST).boundingBox).isEqualTo(RectangleD(0.0, 0.0, 3.0, 4.0))
    }

    @Test
    fun `test PolygonD to PointD geometry`() {
        val polygon1 = PolygonD(listOf(PointD(1.0,1.0), PointD(4.0,1.0), PointD(1.0,5.0)))
        val pointA = PointD(1.0,1.0)
        val pointB = PointD(2.0,2.0)
        val pointC = PointD(3.0,3.0)
        Assertions.assertThat(pointA in polygon1).isTrue()
        Assertions.assertThat(pointB in polygon1).isTrue()
        Assertions.assertThat(pointC in polygon1).isFalse()
        /*
        ##1#
        #2#
        ##
        # 3
         */
        val polygon2 = PolygonD(listOf(PointD(0.0,0.0), PointD(3.0,0.0), PointD(0.0,3.0)))
        Assertions.assertThat(PointD(2.0,0.0) in polygon2).isTrue()
        Assertions.assertThat(PointD(1.0,1.0) in polygon2).isTrue()
        Assertions.assertThat(PointD(2.0,3.0) in polygon2).isFalse()

    }

    @Test
    fun `test RectangleD to Shape geometry`() {
        val polygon1 = PolygonD(listOf(PointD(1.0,1.0), PointD(4.0,1.0), PointD(1.0,5.0)))
        val polygon2 = PolygonD(listOf(PointD(1.0,1.0), PointD(3.0,1.0), PointD(1.0,4.0)))
        val polygon3 = PolygonD(listOf(PointD(0.0,0.0), PointD(6.0,0.0), PointD(0.0,7.0)))
        val polygon4 = PolygonD(listOf(PointD(2.0,2.0), PointD(4.0,3.0), PointD(4.0,0.0)))
        val polygon5 = PolygonD(listOf(PointD(3.0,3.0), PointD(4.0,3.0), PointD(3.0,4.0)))
        val rect = RectangleD(2.0, 2.0, 2.0, 2.0)
        Assertions.assertThat(polygon2.intersects(polygon1)).isTrue()
        Assertions.assertThat(polygon3.intersects(polygon1)).isTrue()
        Assertions.assertThat(polygon4.intersects(polygon1)).isTrue()
        Assertions.assertThat(polygon5.intersects(polygon1)).isFalse()
        Assertions.assertThat(polygon4.intersects(polygon3)).isTrue()
        Assertions.assertThat(polygon1.intersects(rect)).isTrue()
    }

    @Test
    fun `moving and scaling preserves name`() {
        val polygon = PolygonD(listOf(PointD(1.0,1.0), PointD(4.0,1.0), PointD(1.0,5.0)), "my-name")
        val moved = polygon.plus(VectorD.NORTH).minus(VectorD.EAST)
        Assertions.assertThat(moved.name).isEqualTo("my-name")
    }

    @Test
    fun `name can be removed`() {
        val polygon = PolygonD(listOf(PointD(1.0,1.0), PointD(4.0,1.0), PointD(1.0,5.0)), "my-name")
        Assertions.assertThat(polygon.toAnonymous().name).isEqualTo("")
    }

    @Test
    fun `name can be set`() {
        val polygon = PolygonD(listOf(PointD(1.0,1.0), PointD(4.0,1.0), PointD(1.0,5.0)), "my-name")
        Assertions.assertThat(polygon.named("foo").name).isEqualTo("foo")
    }

    @Test
    fun `test PolygonD isWellFormed`() {
        val polygon1 = PolygonD(listOf(PointD(1.0,1.0), PointD(4.0,1.0), PointD(1.0,5.0)))
        val polygon2 = PolygonD(listOf(PointD(1.0,1.0), PointD(4.0,1.0), PointD(4.0,4.0), PointD(1.0,4.0)))
        val polygon3 = PolygonD(listOf(PointD(1.0,1.0), PointD(4.0,4.0), PointD(4.0,1.0), PointD(1.0,4.0)))
        Assertions.assertThat(polygon1.isWellFormed()).isTrue()
        Assertions.assertThat(polygon2.isWellFormed()).isTrue()
        Assertions.assertThat(polygon3.isWellFormed()).isFalse()
    }

    @Test
    fun `test PolygonD isConcave`() {
        val polygon1 = PolygonD(listOf(PointD(1.0,1.0), PointD(4.0,1.0), PointD(1.0,5.0)))
        val polygon2 = PolygonD(listOf(PointD(1.0,1.0), PointD(4.0,1.0), PointD(4.0,4.0), PointD(1.0,4.0)))
        val polygon3 = PolygonD(listOf(PointD(1.0,1.0), PointD(4.0,1.0), PointD(2.0,2.0), PointD(1.0,4.0)))
        Assertions.assertThat(polygon1.isConcave()).isTrue()
        Assertions.assertThat(polygon2.isConcave()).isTrue()
        Assertions.assertThat(polygon3.isConcave()).isFalse()
    }
}