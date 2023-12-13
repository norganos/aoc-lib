package de.linkel.aoc.utils.iterables

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class IntRangeIntersectTest {
    @Test
    fun `1 to 3 and 4 to 6 do not intersect`() {
        Assertions.assertThat(
            (1..3).intersects(4..6)
        ).isFalse()
    }

    @Test
    fun `intersection of 1 to 3 and 4 to 6 is empty`() {
        Assertions.assertThat(
            (1..3).intersect(4..6)
        ).isEmpty()
    }

    @Test
    fun `10 to 30 and 4 to 6 do not intersect`() {
        Assertions.assertThat(
            (10..30).intersects(4..6)
        ).isFalse()
    }

    @Test
    fun `1 to 3 and 2 to 4 intersect`() {
        Assertions.assertThat(
            (1..3).intersects(2..4)
        ).isTrue()
    }

    @Test
    fun `intersection of 1 to 3 and 2 to 4 is 2,3`() {
        Assertions.assertThat(
            (1..3).intersect(2..4)
        ).isNotEmpty()
        Assertions.assertThat(
            (1..3).intersect(2..4)
        ).isEqualTo(2..3)
    }

    @Test
    fun `1 to 3 and 3 to 4 intersect`() {
        Assertions.assertThat(
            (1..3).intersects(3..4)
        ).isTrue()
    }

    @Test
    fun `-1 to 1 and 0 to 4 intersect`() {
        Assertions.assertThat(
            (-1..1).intersects(0..4)
        ).isTrue()
    }

    @Test
    fun `-3 to -1 and -2 to 4 intersect`() {
        Assertions.assertThat(
            (-3..-1).intersects(-2..4)
        ).isTrue()
    }

    @Test
    fun `1 to 5 and 5 to 10 intersect`() {
        Assertions.assertThat(
            (1..5).intersects(5..10)
        ).isTrue()
    }

    @Test
    fun `1 to 5 and 4 to 5 intersect`() {
        Assertions.assertThat(
            (1..5).intersects(4..5)
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
