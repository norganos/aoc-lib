package de.linkel.aoc.utils.rangeset

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class IntRangeSetTest {
    @Test
    fun `2-10 without 0-5 is 6-10`() {
        val result = (2..10).toRangeSet() - (0..5)
        Assertions.assertThat(result.toList()).isEqualTo(listOf(6, 7, 8, 9, 10))
        Assertions.assertThat(result.isEmpty()).isFalse()
        Assertions.assertThat(result.isNotEmpty()).isTrue()
        Assertions.assertThat(result).hasSize(5)
        Assertions.assertThat(result.first).isEqualTo(6)
        Assertions.assertThat(result.last).isEqualTo(10)
    }

    @Test
    fun `2-10 without 5-6 is 2-4+7-10`() {
        val result = (2..10).toRangeSet() - (5..6)
        Assertions.assertThat(result.toList()).isEqualTo(listOf(2, 3, 4, 7, 8, 9, 10))
        Assertions.assertThat(result.isEmpty()).isFalse()
        Assertions.assertThat(result.isNotEmpty()).isTrue()
        Assertions.assertThat(result).hasSize(7)
        Assertions.assertThat(result.first).isEqualTo(2)
        Assertions.assertThat(result.last).isEqualTo(10)
    }

    @Test
    fun `2-5 without 3-3 is 2,4,5`() {
        val result = (2..5).toRangeSet() - (3..3)
        Assertions.assertThat(result.toList()).isEqualTo(listOf(2, 4, 5))
        Assertions.assertThat(result.isEmpty()).isFalse()
        Assertions.assertThat(result.isNotEmpty()).isTrue()
        Assertions.assertThat(result).hasSize(3)
        Assertions.assertThat(result.first).isEqualTo(2)
        Assertions.assertThat(result.last).isEqualTo(5)
    }

    @Test
    fun `2-5 without 2-5 is empty`() {
        val result = (2..5).toRangeSet() - (2..5)
        Assertions.assertThat(result.toList()).isEqualTo(emptyList<Int>())
        Assertions.assertThat(result).hasSize(0)
        Assertions.assertThat(result.isEmpty()).isTrue()
        Assertions.assertThat(result.isNotEmpty()).isFalse()
    }

    @Test
    fun `2-5 without 0-10 is empty`() {
        val result = (2..5).toRangeSet() - (0..10)
        Assertions.assertThat(result.toList()).isEqualTo(emptyList<Int>())
        Assertions.assertThat(result).hasSize(0)
        Assertions.assertThat(result.isEmpty()).isTrue()
        Assertions.assertThat(result.isNotEmpty()).isFalse()
    }

    @Test
    fun `2-5 without 3-2 is 2,3,4,5`() {
        val result = (2..5).toRangeSet() - IntRange.EMPTY
        Assertions.assertThat(result.toList()).isEqualTo(listOf(2, 3, 4, 5))
        Assertions.assertThat(result).hasSize(4)
        Assertions.assertThat(result.first).isEqualTo(2)
        Assertions.assertThat(result.last).isEqualTo(5)
    }

    @Test
    fun `4-10 without 5-6 plus 2-3 is 2-4+7-10`() {
        val result = (4..10).toRangeSet() - (5..6) + (2..3)
        Assertions.assertThat(result.toList()).isEqualTo(listOf(2, 3, 4, 7, 8, 9, 10))
        Assertions.assertThat(result).hasSize(7)
        Assertions.assertThat(result.first).isEqualTo(2)
        Assertions.assertThat(result.last).isEqualTo(10)
    }

    @Test
    fun `4-10 without 5-6 plus 2-6 is 2-10`() {
        val result = (4..10).toRangeSet() - (5..6) + (2..6)
        Assertions.assertThat(result.toList()).isEqualTo(listOf(2, 3, 4, 5, 6, 7, 8, 9, 10))
        Assertions.assertThat(result).hasSize(9)
        Assertions.assertThat(result.first).isEqualTo(2)
        Assertions.assertThat(result.last).isEqualTo(10)
    }

    @Test
    fun `4-10 without 5-6 plus 2-6 is 2-10 in rangeSets`() {
        val result = (4..10).toRangeSet() - (5..6).toRangeSet() + (2..6).toRangeSet()
        Assertions.assertThat(result.toList()).isEqualTo(listOf(2, 3, 4, 5, 6, 7, 8, 9, 10))
        Assertions.assertThat(result).hasSize(9)
        Assertions.assertThat(result.first).isEqualTo(2)
        Assertions.assertThat(result.last).isEqualTo(10)
    }

    @Test
    fun `3-4 plus 4-5 equals 3-5`() {
        Assertions.assertThat((3..4).toRangeSet() + (4..5)).isEqualTo((3..5).toRangeSet())
    }

    @Test
    fun `equality works`() {
        Assertions.assertThat((3..4).toRangeSet()).isEqualTo((3..4).toRangeSet())
        Assertions.assertThat((3..4).toRangeSet().hashCode()).isEqualTo((3..4).toRangeSet().hashCode())
    }

    @Test
    fun `3-4 intersect 4-5 equals 4-4`() {
        Assertions.assertThat((3..4).toRangeSet().intersect(4..5)).isEqualTo((4..4).toRangeSet())
        Assertions.assertThat((3..4).toRangeSet().intersect((4..5).toRangeSet())).isEqualTo((4..4).toRangeSet())
    }

    @Test
    fun `3-4 plus 5-6 is merged into 1 sequence`() {
        Assertions.assertThat(
            ((3..4).toRangeSet() + (5..6)).segments
        ).hasSize(1)
    }
}
