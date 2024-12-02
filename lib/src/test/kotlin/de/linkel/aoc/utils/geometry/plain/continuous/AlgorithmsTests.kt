package de.linkel.aoc.utils.geometry.plain.continuous

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class AlgorithmsTests {

    @Test
    fun `simpler schnitt von 2 strecken`() {
        val segments = listOf(
            SegmentD(PointD(1.0, 4.0), PointD(6.0, 2.0)),
            SegmentD(PointD(2.0, 2.0), PointD(6.0, 7.0)),
        )
        Assertions.assertThat(Algorithms.anySegmentsIntersect(segments)).isTrue()
        Assertions.assertThat(Algorithms.segmentIntersections(segments))
            .containsExactly(
                segments.toSet()
            )
    }

    @Test
    fun `simpler schnitt von einer strecke mit einer nach links gerichteten strecke`() {
        val segments = listOf(
            SegmentD(PointD(6.0, 2.0), PointD(1.0, 4.0)),
            SegmentD(PointD(2.0, 3.0), PointD(6.0, 7.0)),
        )
        Assertions.assertThat(Algorithms.anySegmentsIntersect(segments)).isTrue()
        Assertions.assertThat(Algorithms.segmentIntersections(segments))
            .containsExactly(
                segments.toSet()
            )
    }

    @Test
    fun `zwei strecken die sich nicht schneiden`() {
        val segments = listOf(
            SegmentD(PointD(1.0, 3.0), PointD(6.0, 2.0)),
            SegmentD(PointD(2.0, 3.0), PointD(6.0, 7.0)),
        )
        Assertions.assertThat(Algorithms.anySegmentsIntersect(segments)).isFalse()
        Assertions.assertThat(Algorithms.segmentIntersections(segments)).isEmpty()
    }

    @Test
    fun `drei windschiefe strecken`() {
        val segments = listOf(
            SegmentD(PointD(1.0, 3.0), PointD(6.0, 2.0)),
            SegmentD(PointD(2.0, 3.0), PointD(6.0, 7.0)),
            SegmentD(PointD(3.0, 1.0), PointD(4.0, 1.0)),
        )
        Assertions.assertThat(Algorithms.anySegmentsIntersect(segments)).isFalse()
        Assertions.assertThat(Algorithms.segmentIntersections(segments)).isEmpty()
    }

    @Test
    fun `drei windschiefe strecken und eine senkrechte die alle drei schneidet`() {
        val segments = listOf(
            SegmentD(PointD(1.0, 3.0), PointD(6.0, 2.0)),
            SegmentD(PointD(2.0, 3.0), PointD(6.0, 7.0)),
            SegmentD(PointD(3.0, 1.0), PointD(4.0, 1.0)),
            SegmentD(PointD(3.5, 1.0), PointD(3.5, 6.0)),
        )
        Assertions.assertThat(Algorithms.anySegmentsIntersect(segments)).isTrue()
        Assertions.assertThat(Algorithms.segmentIntersections(segments))
            .containsOnly(
                setOf(segments[0], segments[3]),
                setOf(segments[1], segments[3]),
                setOf(segments[2], segments[3]),
            )
    }

    @Test
    fun `test sweep line ende-y-ermittlung`() {
        val segments = listOf(
            SegmentD(PointD(1.0, 6.0), PointD(4.0, 6.0)),
            SegmentD(PointD(2.0, 2.0), PointD(10.0, 10.0)),
            SegmentD(PointD(7.0, 6.0), PointD(9.0, 6.0)),
            SegmentD(PointD(3.0, 8.0), PointD(8.0, 4.0)),
        )
        Assertions.assertThat(Algorithms.anySegmentsIntersect(segments)).isTrue()
        Assertions.assertThat(Algorithms.segmentIntersections(segments))
            .containsExactly(
                setOf(segments[1], segments[3])
            )
    }

    @Test
    fun `test sweep line ende-y-ermittlung 2`() {
        val segments = listOf(
            SegmentD(PointD(2.0, 0.0), PointD(10.0, 6.0)),
            SegmentD(PointD(2.0, 5.0), PointD(11.0, 2.0)),
            SegmentD(PointD(0.0, 3.0), PointD(4.0, 3.0)),
            SegmentD(PointD(8.0, 4.0), PointD(12.0, 4.0)),
        )
        Assertions.assertThat(Algorithms.anySegmentsIntersect(segments)).isTrue()
        Assertions.assertThat(Algorithms.segmentIntersections(segments))
            .containsExactly(
                setOf(segments[0], segments[1])
            )
    }

    @Test
    fun `test selber startpunkt`() {
        val segments = listOf(
            SegmentD(PointD(6.0, 2.0), PointD(1.0, 4.0)),
            SegmentD(PointD(6.0, 2.0), PointD(4.0, 2.0)),
        )
        Assertions.assertThat(Algorithms.anySegmentsIntersect(segments)).isTrue()
        Assertions.assertThat(Algorithms.segmentIntersections(segments))
            .containsExactly(
                segments.toSet()
            )
    }
}