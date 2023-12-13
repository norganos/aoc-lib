package de.linkel.aoc.utils.iterables

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class LongRangeIntersectTest {
    @Test
    fun `1 to 3 and 4 to 6 do not intersect`() {
        Assertions.assertThat(
            (1L..3L).intersects(4L..6L)
        ).isFalse()
    }

    @Test
    fun `intersection of 1 to 3 and 4 to 6 is empty`() {
        Assertions.assertThat(
            (1L..3L).intersect(4L..6L)
        ).isEmpty()
    }

    @Test
    fun `10 to 30 and 4 to 6 do not intersect`() {
        Assertions.assertThat(
            (10L..30L).intersects(4L..6L)
        ).isFalse()
    }

    @Test
    fun `1 to 3 and 2 to 4 intersect`() {
        Assertions.assertThat(
            (1L..3L).intersects(2L..4L)
        ).isTrue()
    }

    @Test
    fun `intersection of 1 to 3 and 2 to 4 is 2,3`() {
        Assertions.assertThat(
            (1L..3L).intersect(2L..4L)
        ).isNotEmpty()
        Assertions.assertThat(
            (1L..3L).intersect(2L..4L)
        ).isEqualTo(2L..3L)
    }

    @Test
    fun `1 to 3 and 3 to 4 intersect`() {
        Assertions.assertThat(
            (1L..3L).intersects(3L..4L)
        ).isTrue()
    }

    @Test
    fun `-1 to 1 and 0 to 4 intersect`() {
        Assertions.assertThat(
            (-1L..1L).intersects(0L..4L)
        ).isTrue()
    }

    @Test
    fun `-3 to -1 and -2 to 4 intersect`() {
        Assertions.assertThat(
            (-3L..-1L).intersects(-2L..4L)
        ).isTrue()
    }

    @Test
    fun `1 to 5 and 5 to 10 intersect`() {
        Assertions.assertThat(
            (1L..5L).intersects(5L..10L)
        ).isTrue()
    }

    @Test
    fun `1 to 5 and 4 to 5 intersect`() {
        Assertions.assertThat(
            (1L..5L).intersects(4L..5L)
        ).isTrue()
    }

    @Test
    fun `1 to 5 and 3 to 4 intersect`() {
        Assertions.assertThat(
            (1..5).intersects(3..4)
        ).isTrue()
    }

    @Test
    fun `1 to 5 and 5 to 5 intersect`() {
        Assertions.assertThat(
            (1..5).intersects(5..5)
        ).isTrue()
    }
}
