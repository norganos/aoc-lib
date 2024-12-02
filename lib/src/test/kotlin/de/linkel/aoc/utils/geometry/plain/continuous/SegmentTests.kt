package de.linkel.aoc.utils.geometry.plain.continuous

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class SegmentTests {

    @Test
    fun `test SegmentD constructor`() {
        val segment = SegmentD(PointD(2.0,3.0), PointD(4.0,5.0))
        Assertions.assertThat(segment.start.x).isEqualTo(2.0)
        Assertions.assertThat(segment.start.y).isEqualTo(3.0)
        Assertions.assertThat(segment.end.x).isEqualTo(4.0)
        Assertions.assertThat(segment.end.y).isEqualTo(5.0)
    }

    @Test
    fun `test SegmentD equals method`() {
        val segment1 = SegmentD(PointD(2.0,3.0), PointD(4.0,5.0))
        val segment2 = SegmentD(PointD(2.0,3.0), PointD(4.0,5.0))
        val segment2b = SegmentD(PointD(4.0,5.0), PointD(2.0,3.0))
        val segment3 = SegmentD(PointD(3.0,3.0), PointD(4.0,5.0))
        val segment4 = SegmentD(PointD(2.0,3.0), PointD(4.0,4.0))
        Assertions.assertThat(segment1).isEqualTo(segment2)
        Assertions.assertThat(segment1).isEqualTo(segment2b)
        Assertions.assertThat(segment1).isNotEqualTo(segment3)
        Assertions.assertThat(segment1).isNotEqualTo(segment4)
    }

    @Test
    fun `test SegmentD distance calculation`() {
        val segment = SegmentD(PointD(0.0,0.0), PointD(3.0,4.0))
        Assertions.assertThat(segment.length).isEqualTo(5.0)
    }

    @Test
    fun `test SegmentD isVertical and isHorizontal calculation`() {
        Assertions.assertThat(SegmentD(PointD(1.0,1.0), VectorD(3.0,4.0)).isVertical).isFalse()
        Assertions.assertThat(SegmentD(PointD(1.0,1.0), VectorD(0.0,4.0)).isVertical).isTrue()
        Assertions.assertThat(SegmentD(PointD(1.0,1.0), VectorD(3.0,4.0)).isHorizontal).isFalse()
        Assertions.assertThat(SegmentD(PointD(1.0,1.0), VectorD(3.0,0.0)).isHorizontal).isTrue()
    }

    @Test
    fun `test SegmentD plus method`() {
        val segment1 = SegmentD(PointD(2.0,3.0), PointD(4.0,5.0))
        Assertions.assertThat(segment1 + VectorD.NORTH).isEqualTo(SegmentD(PointD(2.0,2.0), PointD(4.0,4.0)))
        Assertions.assertThat(segment1 + VectorD.SOUTH).isEqualTo(SegmentD(PointD(2.0,4.0), PointD(4.0,6.0)))
        Assertions.assertThat(segment1 + VectorD.WEST).isEqualTo(SegmentD(PointD(1.0,3.0), PointD(3.0,5.0)))
        Assertions.assertThat(segment1 + VectorD.EAST).isEqualTo(SegmentD(PointD(3.0,3.0), PointD(5.0,5.0)))
        Assertions.assertThat(segment1 + VectorD.NORTH_WEST).isEqualTo(SegmentD(PointD(1.0,2.0), PointD(3.0,4.0)))
        Assertions.assertThat(segment1 + VectorD.NORTH_EAST).isEqualTo(SegmentD(PointD(3.0,2.0), PointD(5.0,4.0)))
        Assertions.assertThat(segment1 + VectorD.SOUTH_WEST).isEqualTo(SegmentD(PointD(1.0,4.0), PointD(3.0,6.0)))
        Assertions.assertThat(segment1 + VectorD.SOUTH_EAST).isEqualTo(SegmentD(PointD(3.0,4.0), PointD(5.0,6.0)))
    }

    @Test
    fun `test SegmentD minus method`() {
        val segment1 = SegmentD(PointD(2.0,3.0), PointD(4.0,5.0))
        Assertions.assertThat(segment1 - VectorD.SOUTH).isEqualTo(SegmentD(PointD(2.0,2.0), PointD(4.0,4.0)))
        Assertions.assertThat(segment1 - VectorD.NORTH).isEqualTo(SegmentD(PointD(2.0,4.0), PointD(4.0,6.0)))
        Assertions.assertThat(segment1 - VectorD.EAST).isEqualTo(SegmentD(PointD(1.0,3.0), PointD(3.0,5.0)))
        Assertions.assertThat(segment1 - VectorD.WEST).isEqualTo(SegmentD(PointD(3.0,3.0), PointD(5.0,5.0)))
        Assertions.assertThat(segment1 - VectorD.SOUTH_EAST).isEqualTo(SegmentD(PointD(1.0,2.0), PointD(3.0,4.0)))
        Assertions.assertThat(segment1 - VectorD.SOUTH_WEST).isEqualTo(SegmentD(PointD(3.0,2.0), PointD(5.0,4.0)))
        Assertions.assertThat(segment1 - VectorD.NORTH_EAST).isEqualTo(SegmentD(PointD(1.0,4.0), PointD(3.0,6.0)))
        Assertions.assertThat(segment1 - VectorD.NORTH_WEST).isEqualTo(SegmentD(PointD(3.0,4.0), PointD(5.0,6.0)))
    }

    @Test
    fun `test SegmentD times method`() {
        val segment1 = SegmentD(PointD(2.0,3.0), PointD(4.0,5.0))
        val doubled = segment1 * 2.0
        Assertions.assertThat(doubled.start).isEqualTo(PointD(2.0,3.0))
        Assertions.assertThat(doubled.end).isEqualTo(PointD(6.0,7.0))
    }

    @Test
    fun `test SegmentD div method`() {
        val segment1 = SegmentD(PointD(2.0,3.0), PointD(6.0,7.0))
        val doubled = segment1 / 2.0
        Assertions.assertThat(doubled.start).isEqualTo(PointD(2.0,3.0))
        Assertions.assertThat(doubled.end).isEqualTo(PointD(4.0,5.0))
    }

    @Test
    fun `test SegmentD intersects method`() {
        val segment1 = SegmentD(PointD(0.0,1.0), PointD(4.0,1.0))
        val segment2 = SegmentD(PointD(0.0,0.0), PointD(5.0,5.0))
        val segment3 = SegmentD(PointD(1.0,0.0), PointD(4.0,-1.0))
        Assertions.assertThat(segment1.intersects(segment2)).isTrue()
        Assertions.assertThat(segment1.intersects(segment3)).isFalse()
    }

    @Test
    fun `test SegmentD intersect method`() {
        val segment1 = SegmentD(PointD(0.0,0.0), PointD(1.0,1.0))
        val segment2 = SegmentD(PointD(0.0,1.0), PointD(1.0,0.0))
        Assertions.assertThat(segment1.intersects(segment2)).isTrue()
        Assertions.assertThat(segment1.intersect(segment2)).isEqualTo(PointD(0.5,0.5))
        val segment3 = SegmentD(PointD(0.0,0.0), PointD(2.0,2.0))
        val segment4 = SegmentD(PointD(0.0,2.0), PointD(2.0,0.0))
        Assertions.assertThat(segment3.intersect(segment4)).isEqualTo(PointD(1.0,1.0))
        Assertions.assertThat(segment3.intersects(segment3)).isTrue()
        val segment5 = SegmentD(PointD(1.0,1.0), PointD(5.0,5.0))
        Assertions.assertThat(segment3.intersects(segment5)).isTrue()
        Assertions.assertThat(segment3.intersects(segment5)).isTrue()
    }

    @Test
    fun `test SegmentD contains method`() {
        val segment1 = SegmentD(PointD(0.0,0.0), PointD(2.0,2.0))
        Assertions.assertThat(PointD(0.0,0.0) in segment1).isTrue()
        Assertions.assertThat(PointD(1.0,1.0) in segment1).isTrue()
    }

    @Test
    fun `moving and scaling preserves name`() {
        val seg = SegmentD(PointD(0.0,0.0), PointD(2.0,2.0), "my-name")
        val moved = seg.plus(VectorD.NORTH).minus(VectorD.EAST).times(4.0).div(2.0)
        Assertions.assertThat(moved.name).isEqualTo("my-name")
    }

    @Test
    fun `name can be removed`() {
        val seg = SegmentD(PointD(0.0,0.0), PointD(2.0,2.0), "my-name")
        Assertions.assertThat(seg.toAnonymous().name).isEqualTo("")
    }

    @Test
    fun `name can be set`() {
        val seg = SegmentD(PointD(0.0,0.0), PointD(2.0,2.0), "my-name")
        Assertions.assertThat(seg.named("foo").name).isEqualTo("foo")
    }
}