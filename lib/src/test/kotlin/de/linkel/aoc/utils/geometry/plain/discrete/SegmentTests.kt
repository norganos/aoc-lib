package de.linkel.aoc.utils.geometry.plain.discrete

import de.linkel.aoc.utils.geometry.plain.continuous.PointD
import de.linkel.aoc.utils.geometry.plain.continuous.SegmentD
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class SegmentTests {

    @Test
    fun `test Segment constructor`() {
        val segment = Segment(Point(2, 3), Point(4, 5))
        Assertions.assertThat(segment.start.x).isEqualTo(2)
        Assertions.assertThat(segment.start.y).isEqualTo(3)
        Assertions.assertThat(segment.end.x).isEqualTo(4)
        Assertions.assertThat(segment.end.y).isEqualTo(5)
    }

    @Test
    fun `test Segment equals method`() {
        val segment1 = Segment(Point(2, 3), Point(4, 5))
        val segment2 = Segment(Point(2, 3), Point(4, 5))
        val segment2b = Segment(Point(4, 5), Point(2, 3))
        val segment3 = Segment(Point(3, 3), Point(4, 5))
        val segment4 = Segment(Point(2, 3), Point(4, 4))
        Assertions.assertThat(segment1).isEqualTo(segment2)
        Assertions.assertThat(segment1).isEqualTo(segment2b)
        Assertions.assertThat(segment1).isNotEqualTo(segment3)
        Assertions.assertThat(segment1).isNotEqualTo(segment4)
    }
    
    @Test
    fun `test Segment size`() {
        val segment1 = Segment(Point(0, 0), Point(3, 4))
        Assertions.assertThat(segment1.size).isEqualTo(2)
        val segment2 = Segment(Point(0, 0), Point(4, 4))
        Assertions.assertThat(segment2.size).isEqualTo(5)
        val segment3 = Segment(Point(0, 0), Point(4, 6))
        Assertions.assertThat(segment3.size).isEqualTo(3)
        val segment5 = Segment(Point(0, 0), Point(4, 0))
        Assertions.assertThat(segment5.size).isEqualTo(5)
    }

    @Test
    fun `test Segment distance calculation`() {
        val segment = Segment(Point(0, 0), Point(3, 4))
        Assertions.assertThat(segment.length).isEqualTo(5.0)
    }

    @Test
    fun `test Segment manhattanDistance calculation`() {
        val segment = Segment(Point(0, 0), Point(3, 4))
        Assertions.assertThat(segment.manhattanDistance).isEqualTo(7)
    }

    @Test
    fun `test SegmentD isVertical and isHorizontal calculation`() {
        Assertions.assertThat(Segment(Point(1,1), Vector(3,4)).isVertical).isFalse()
        Assertions.assertThat(Segment(Point(1,1), Vector(0,4)).isVertical).isTrue()
        Assertions.assertThat(Segment(Point(1,1), Vector(3,4)).isHorizontal).isFalse()
        Assertions.assertThat(Segment(Point(1,1), Vector(3,0)).isHorizontal).isTrue()
    }

    @Test
    fun `test Segment plus method`() {
        val segment1 = Segment(Point(2, 3), Point(4, 5))
        Assertions.assertThat(segment1 + Vector.NORTH).isEqualTo(Segment(Point(2, 2), Point(4, 4)))
        Assertions.assertThat(segment1 + Vector.SOUTH).isEqualTo(Segment(Point(2, 4), Point(4, 6)))
        Assertions.assertThat(segment1 + Vector.WEST).isEqualTo(Segment(Point(1, 3), Point(3, 5)))
        Assertions.assertThat(segment1 + Vector.EAST).isEqualTo(Segment(Point(3, 3), Point(5, 5)))
        Assertions.assertThat(segment1 + Vector.NORTH_WEST).isEqualTo(Segment(Point(1, 2), Point(3, 4)))
        Assertions.assertThat(segment1 + Vector.NORTH_EAST).isEqualTo(Segment(Point(3, 2), Point(5, 4)))
        Assertions.assertThat(segment1 + Vector.SOUTH_WEST).isEqualTo(Segment(Point(1, 4), Point(3, 6)))
        Assertions.assertThat(segment1 + Vector.SOUTH_EAST).isEqualTo(Segment(Point(3, 4), Point(5, 6)))
    }

    @Test
    fun `test Segment minus method`() {
        val segment1 = Segment(Point(2, 3), Point(4, 5))
        Assertions.assertThat(segment1 - Vector.SOUTH).isEqualTo(Segment(Point(2, 2), Point(4, 4)))
        Assertions.assertThat(segment1 - Vector.NORTH).isEqualTo(Segment(Point(2, 4), Point(4, 6)))
        Assertions.assertThat(segment1 - Vector.EAST).isEqualTo(Segment(Point(1, 3), Point(3, 5)))
        Assertions.assertThat(segment1 - Vector.WEST).isEqualTo(Segment(Point(3, 3), Point(5, 5)))
        Assertions.assertThat(segment1 - Vector.SOUTH_EAST).isEqualTo(Segment(Point(1, 2), Point(3, 4)))
        Assertions.assertThat(segment1 - Vector.SOUTH_WEST).isEqualTo(Segment(Point(3, 2), Point(5, 4)))
        Assertions.assertThat(segment1 - Vector.NORTH_EAST).isEqualTo(Segment(Point(1, 4), Point(3, 6)))
        Assertions.assertThat(segment1 - Vector.NORTH_WEST).isEqualTo(Segment(Point(3, 4), Point(5, 6)))
    }

    @Test
    fun `test Segment times method`() {
        val segment1 = Segment(Point(2, 3), Point(4, 5))
        val doubled = segment1 * 2
        Assertions.assertThat(doubled.start).isEqualTo(Point(2, 3))
        Assertions.assertThat(doubled.end).isEqualTo(Point(6, 7))
    }

    @Test
    fun `test Segment div method`() {
        val segment1 = Segment(Point(2, 3), Point(6, 7))
        val doubled = segment1 / 2
        Assertions.assertThat(doubled.start).isEqualTo(Point(2, 3))
        Assertions.assertThat(doubled.end).isEqualTo(Point(4, 5))
    }

    @Test
    fun `test Segment iterator method`() {
        val segment1 = Segment(Point(2, 3), Point(6, 7))
        Assertions.assertThat(segment1.toList()).containsExactly(Point(2, 3), Point(3, 4), Point(4, 5), Point(5, 6), Point(6, 7))
        val segment2 = Segment(Point(2, 3), Point(6, 3))
        Assertions.assertThat(segment2.toList()).containsExactly(Point(2, 3), Point(3, 3), Point(4, 3), Point(5, 3), Point(6, 3))
        val segment3 = Segment(Point(2, 3), Point(2, 6))
        Assertions.assertThat(segment3.toList()).containsExactly(Point(2, 3), Point(2, 4), Point(2, 5), Point(2, 6))
    }

    @Test
    fun `test Segment intersects method`() {
        val segment1 = Segment(Point(0, 1), Point(4, 1))
        val segment2 = Segment(Point(0, 0), Point(5, 5))
        val segment3 = Segment(Point(1, 0), Point(4, -1))
        Assertions.assertThat(segment1.intersects(segment2)).isTrue()
        Assertions.assertThat(segment1.intersects(segment3)).isFalse()
    }

    @Test
    fun `test Segment intersect method`() {
        val segment1 = Segment(Point(0, 0), Point(1, 1))
        val segment2 = Segment(Point(0, 1), Point(1, 0))
        Assertions.assertThat(segment1.intersects(segment2)).isTrue()
        Assertions.assertThat(segment1.intersect(segment2)).isNull()
        val segment3 = Segment(Point(0, 0), Point(2, 2))
        val segment4 = Segment(Point(0, 2), Point(2, 0))
        Assertions.assertThat(segment3.intersect(segment4)).isEqualTo(Point(1, 1))
        Assertions.assertThat(segment3.intersects(segment3)).isTrue()
        val segment5 = Segment(Point(1, 1), Point(5, 5))
        Assertions.assertThat(segment3.intersects(segment5)).isTrue()
        Assertions.assertThat(segment3.intersects(segment5)).isTrue()
    }

    @Test
    fun `test Segment contains method`() {
        val segment1 = Segment(Point(0, 0), Point(2, 2))
        Assertions.assertThat(Point(0, 0) in segment1).isTrue()
        Assertions.assertThat(Point(1, 1) in segment1).isTrue()
    }

    @Test
    fun `test Segment toContinuous method`() {
        Assertions.assertThat(Segment(Point(2, 3), Point(4, 5)).toContinuous()).isEqualTo(SegmentD(PointD(2.0, 3.0), PointD(4.0, 5.0)))
    }

    @Test
    fun `moving and scaling preserves name`() {
        val seg = Segment(Point(0, 0), Point(2, 2), "my-name")
        val moved = seg.plus(Vector.NORTH).minus(Vector.EAST).times(4).div(2)
        Assertions.assertThat(moved.name).isEqualTo("my-name")
    }

    @Test
    fun `name can be removed`() {
        val seg = Segment(Point(0, 0), Point(2, 2), "my-name")
        Assertions.assertThat(seg.toAnonymous().name).isEqualTo("")
    }

    @Test
    fun `name can be set`() {
        val seg = Segment(Point(0, 0), Point(2, 2), "my-name")
        Assertions.assertThat(seg.named("foo").name).isEqualTo("foo")
    }
}