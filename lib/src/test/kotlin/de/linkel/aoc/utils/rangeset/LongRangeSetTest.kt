package de.linkel.aoc.utils.rangeset

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class LongRangeSetTest {
    @Test
    fun `2-10 without 0-5 is 6-10`() {
        val result = (2L..10L).toRangeSet() - (0L..5L)
        Assertions.assertThat(result.toList()).isEqualTo(listOf(6L, 7L, 8L, 9L, 10L))
        Assertions.assertThat(result.isEmpty()).isFalse()
        Assertions.assertThat(result.isNotEmpty()).isTrue()
        Assertions.assertThat(result).hasSize(5)
        Assertions.assertThat(result.first).isEqualTo(6L)
        Assertions.assertThat(result.last).isEqualTo(10L)
    }

    @Test
    fun `2-10 without 5-6 is 2-4+7-10`() {
        val result = (2L..10L).toRangeSet() - (5L..6L)
        Assertions.assertThat(result.toList()).isEqualTo(listOf(2L, 3L, 4L, 7L, 8L, 9L, 10L))
        Assertions.assertThat(result.isEmpty()).isFalse()
        Assertions.assertThat(result.isNotEmpty()).isTrue()
        Assertions.assertThat(result).hasSize(7)
        Assertions.assertThat(result.first).isEqualTo(2L)
        Assertions.assertThat(result.last).isEqualTo(10L)
    }

    @Test
    fun `2-5 without 3-3 is 2,4,5`() {
        val result = (2L..5L).toRangeSet() - (3L..3L)
        Assertions.assertThat(result.toList()).isEqualTo(listOf(2L, 4L, 5L))
        Assertions.assertThat(result.isEmpty()).isFalse()
        Assertions.assertThat(result.isNotEmpty()).isTrue()
        Assertions.assertThat(result).hasSize(3)
        Assertions.assertThat(result.first).isEqualTo(2L)
        Assertions.assertThat(result.last).isEqualTo(5L)
    }

    @Test
    fun `2-5 without 2-5 is empty`() {
        val result = (2L..5L).toRangeSet() - (2L..5L)
        Assertions.assertThat(result.toList()).isEqualTo(emptyList<Int>())
        Assertions.assertThat(result).hasSize(0)
        Assertions.assertThat(result.isEmpty()).isTrue()
        Assertions.assertThat(result.isNotEmpty()).isFalse()
    }

    @Test
    fun `2-5 without 0-10 is empty`() {
        val result = (2L..5L).toRangeSet() - (0L..10L)
        Assertions.assertThat(result.toList()).isEqualTo(emptyList<Int>())
        Assertions.assertThat(result).hasSize(0)
        Assertions.assertThat(result.isEmpty()).isTrue()
        Assertions.assertThat(result.isNotEmpty()).isFalse()
    }

    @Test
    fun `2-5 without 3-2 is 2,3,4,5`() {
        val result = (2L..5L).toRangeSet() - LongRange.EMPTY
        Assertions.assertThat(result.toList()).isEqualTo(listOf(2L, 3L, 4L, 5L))
        Assertions.assertThat(result).hasSize(4)
        Assertions.assertThat(result.first).isEqualTo(2)
        Assertions.assertThat(result.last).isEqualTo(5)
    }

    @Test
    fun `4-10 without 5-6 plus 2-3 is 2-4+7-10`() {
        val result = (4L..10L).toRangeSet() - (5L..6L) + (2L..3L)
        Assertions.assertThat(result.toList()).isEqualTo(listOf(2L, 3L, 4L, 7L, 8L, 9L, 10L))
        Assertions.assertThat(result).hasSize(7)
        Assertions.assertThat(result.first).isEqualTo(2L)
        Assertions.assertThat(result.last).isEqualTo(10L)
    }

    @Test
    fun `4-10 without 5-6 plus 2-6 is 2-10`() {
        val result = (4L..10L).toRangeSet() - (5L..6L) + (2L..6L)
        Assertions.assertThat(result.toList()).isEqualTo(listOf(2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L))
        Assertions.assertThat(result).hasSize(9)
        Assertions.assertThat(result.first).isEqualTo(2L)
        Assertions.assertThat(result.last).isEqualTo(10L)
    }

    @Test
    fun `4-10 without 5-6 plus 2-6 is 2-10 in rangeSets`() {
        val result = (4L..10L).toRangeSet() - (5L..6L).toRangeSet() + (2L..6L).toRangeSet()
        Assertions.assertThat(result.toList()).isEqualTo(listOf(2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L))
        Assertions.assertThat(result).hasSize(9)
        Assertions.assertThat(result.first).isEqualTo(2L)
        Assertions.assertThat(result.last).isEqualTo(10L)
    }

    @Test
    fun `3-4 plus 4-5 equals 3-5`() {
        Assertions.assertThat((3L..4L).toRangeSet() + (4L..5L)).isEqualTo((3L..5L).toRangeSet())
    }

    @Test
    fun `equality works`() {
        Assertions.assertThat((3L..4L).toRangeSet()).isEqualTo((3L..4L).toRangeSet())
        Assertions.assertThat((3L..4L).toRangeSet().hashCode()).isEqualTo((3L..4L).toRangeSet().hashCode())
    }

    @Test
    fun `3-4 intersect 4-5 equals 4-4`() {
        Assertions.assertThat((3L..4L).toRangeSet().intersect(4L..5L)).isEqualTo((4L..4L).toRangeSet())
        Assertions.assertThat((3L..4L).toRangeSet().intersect((4L..5L).toRangeSet())).isEqualTo((4L..4L).toRangeSet())
    }

    @Test
    fun `3-4 plus 5-6 is merged into 1 sequence`() {
        Assertions.assertThat(
            ((3L..4L).toRangeSet() + (5L..6L)).segments
        ).hasSize(1)
    }
}
